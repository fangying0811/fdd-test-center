package com.fangdd.testcenter.common.util;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.log4j.PropertyConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fangdd.testcenter.web.util.WebConstants;

public class MailUtil {
	private Logger logger = LoggerFactory.getLogger(MailUtil.class);
	private MimeMessage mimeMsg; // MIME邮件对象
	private Session session; // 邮件会话对象
	private Properties props; // 系统属性
	private boolean needAuth = false; // smtp是否需要认证
	// smtp认证用户名和密码
	private String username;
	private String password;
	private Multipart mp; // Multipart对象,邮件内容,标题,附件等内容均添加到其中后再生成MimeMessage对象

	/**
	 * Constructor
	 * 
	 * @param smtp
	 *            邮件发送服务器
	 */
	public MailUtil(String smtp, int port) {
		setSmtpHost(smtp);
		setSmtpPort(port);
		createMimeMessage();
	}

	/**
	 * 设置邮件发送服务器
	 * 
	 * @param hostName
	 *            String
	 */
	public void setSmtpHost(String hostName) {
		logger.info("设置系统属性：mail.smtp.host = {}", new Object[] { hostName });
		if (props == null) {
			props = System.getProperties();// 获得系统属性对象
		}
		props.put("mail.smtp.host", hostName); // 设置SMTP主机
	}

	/**
	 * 设置邮件发送服务器
	 * 
	 * @param hostName
	 *            String
	 */
	public void setSmtpPort(int port) {

		logger.info("设置系统属性：mail.smtp.port = {}", new Object[] { port });
		if (props == null) {
			props = System.getProperties(); // 获得系统属性对象
		}
		props.put("mail.smtp.port", port); // 设置SMTP端口
	}

	/**
	 * 创建MIME邮件对象
	 * 
	 * @return
	 */
	public boolean createMimeMessage() {
		try {
			logger.info("准备获取邮件会话对象！");
			session = Session.getDefaultInstance(props, null); // 获得邮件会话对象
		} catch (Exception e) {
			logger.error("获取邮件会话对象时发生错误！", e);
			return false;
		}

		logger.info("准备创建MIME邮件对象！");
		try {
			mimeMsg = new MimeMessage(session); // 创建MIME邮件对象
			mp = new MimeMultipart();

			return true;
		} catch (Exception e) {
			logger.error("创建MIME邮件对象失败！", e);
			return false;
		}
	}

	/**
	 * 设置SMTP是否需要验证
	 * 
	 * @param need
	 */
	public void setNeedAuth(boolean need) {
		if (props == null)
			props = System.getProperties();
		if (need) {
			props.put("mail.smtp.auth", "true");
		} else {
			props.put("mail.smtp.auth", "false");
		}
	}

	/**
	 * 设置用户名和密码
	 * 
	 * @param name
	 * @param pass
	 */
	public void setNamePass(String name, String pass) {
		username = name;
		password = pass;
	}

	/**
	 * 设置邮件主题
	 * 
	 * @param mailSubject
	 * @return
	 */
	public boolean setSubject(String mailSubject) {
		try {
			mimeMsg.setSubject(mailSubject);
			return true;
		} catch (Exception e) {
			logger.error("设置邮件主题发生错误！", e);
			return false;
		}
	}

	/**
	 * 设置邮件正文
	 * 
	 * @param mailBody
	 *            String
	 */
	public boolean setBody(String mailBody) {
		try {
			BodyPart bp = new MimeBodyPart();
			bp.setContent("" + mailBody, "text/html;charset=" + WebConstants.CHARSET);
			mp.addBodyPart(bp);

			return true;
		} catch (Exception e) {
			logger.error("设置邮件正文时发生错误！", e);
			return false;
		}
	}

	/**
	 * 添加附件
	 * 
	 * @param filename
	 *            String
	 */
	public boolean addFileAffix(String filename) {
		try {
			BodyPart bp = new MimeBodyPart();
			FileDataSource fileds = new FileDataSource(filename);
			bp.setDataHandler(new DataHandler(fileds));
			bp.setFileName(fileds.getName());

			mp.addBodyPart(bp);

			return true;
		} catch (Exception e) {
			logger.error("增加邮件附件发生错误！", e);
			return false;
		}
	}

	/**
	 * 设置发信人
	 * 
	 * @param from
	 *            String
	 */
	public boolean setFrom(String from) {
		try {
			mimeMsg.setFrom(new InternetAddress(from)); // 设置发信人
			return true;
		} catch (Exception e) {
			logger.error("设置发信人发生错误！", e);
			return false;
		}
	}

	/**
	 * 设置收信人
	 * 
	 * @param to
	 *            String
	 */
	public boolean setTo(String to) {
		if (to == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 设置抄送人
	 * 
	 * @param copyto
	 *            String
	 */
	public boolean setCopyTo(String copyto) {
		if (copyto == null)
			return false;
		try {
			mimeMsg.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(copyto));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 发送邮件
	 */
	public boolean sendOut() {
		try {
			mimeMsg.setContent(mp);
			mimeMsg.saveChanges();
			logger.info("正在发送邮件....");

			Session mailSession = Session.getInstance(props, null);
			Transport transport = mailSession.getTransport("smtp");
			logger.info("链接邮件服务器,mail.smtp.host={},mail.smtp.port={},username={},password={}", new Object[] {
					props.get("mail.smtp.host"), (int) props.get("mail.smtp.port"), username, password });
			transport.connect((String) props.get("mail.smtp.host"), (int) props.get("mail.smtp.port"), username,
					password);
			if (mimeMsg.getRecipients(Message.RecipientType.TO) != null) {
				transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.TO));
			}
			if (mimeMsg.getRecipients(Message.RecipientType.CC) != null) {
				transport.sendMessage(mimeMsg, mimeMsg.getRecipients(Message.RecipientType.CC));
			}
			// transport.send(mimeMsg);
			logger.info("发送邮件成功！");
			transport.close();

			return true;
		} catch (Exception e) {
			logger.error("邮件发送失败！", e);
			return false;
		}
	}

	/**
	 * 调用sendOut方法完成邮件发送
	 * 
	 * @param smtp
	 * @param port
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean send(String smtp, int port, String from, String to, String subject, String content,
			String username, String password) {
		MailUtil theMail = new MailUtil(smtp, port);
		theMail.setNeedAuth(true); // 需要验证
		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOut())
			return false;
		return true;
	}

	/**
	 * 调用sendOut方法完成邮件发送,带抄送
	 * 
	 * @param smtp
	 * @param port
	 * @param from
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @return boolean
	 */
	public static boolean sendAndCc(String smtp, int port, String from, String to, String copyto, String subject,
			String content, String username, String password) {
		MailUtil theMail = new MailUtil(smtp, port);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setCopyTo(copyto))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOut())
			return false;
		return true;
	}

	/**
	 * 调用sendOut方法完成邮件发送,带附件
	 * 
	 * @param smtp
	 * @param port
	 * @param from
	 * @param to
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @param filename
	 *            附件路径
	 * @return
	 */
	public static boolean send(String smtp, int port, String from, String to, String subject, String content,
			String username, String password, String filename) {
		MailUtil theMail = new MailUtil(smtp, port);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.addFileAffix(filename))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOut())
			return false;
		return true;
	}

	/**
	 * 调用sendOut方法完成邮件发送,带附件和抄送
	 * 
	 * @param smtp
	 * @param port
	 * @param from
	 * @param to
	 * @param copyto
	 * @param subject
	 * @param content
	 * @param username
	 * @param password
	 * @param filename
	 * @return
	 */
	public static boolean sendAndCc(String smtp, int port, String from, String to, String copyto, String subject,
			String content, String username, String password, String filename) {
		MailUtil theMail = new MailUtil(smtp, port);
		theMail.setNeedAuth(true); // 需要验证

		if (!theMail.setSubject(subject))
			return false;
		if (!theMail.setBody(content))
			return false;
		if (!theMail.addFileAffix(filename))
			return false;
		if (!theMail.setTo(to))
			return false;
		if (!theMail.setCopyTo(copyto))
			return false;
		if (!theMail.setFrom(from))
			return false;
		theMail.setNamePass(username, password);

		if (!theMail.sendOut())
			return false;
		return true;
	}

	public static void main(String[] args) {
		PropertyConfigurator.configure(System.getProperty("user.dir") + "/src/conf/log4j.properties");
		String smtp = "smtp.exmail.qq.com";
		int port = 25;
		String from = "hexin@fangdd.com";
		String password = "Fdd@1985";
		String to = "hexin@fangdd.com";
		String subject = "周四提交周报提醒";
		String content = "亲，又到周四了，记得赶在下班前提交周报哦！";
		MailUtil.send(smtp, port, from, to, subject, content, from, password);
	}
}
