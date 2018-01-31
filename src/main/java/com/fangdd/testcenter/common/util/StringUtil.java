package com.fangdd.testcenter.common.util;

import java.io.UnsupportedEncodingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StringUtil {
	private static Logger logger = LoggerFactory.getLogger(StringUtil.class);

	/**
	 * 判断输入的字符串参数是否为空。
	 * 
	 * @param args
	 *            输入的字串
	 * 
	 * @return 输入的字符串为空则返回true,反之则返回false
	 */
	public static boolean validateNull(String args) {
		if (args == null || args.length() == 0) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断输入的字符串参数是否不为空。
	 * 
	 * @param args
	 *            输入的字串
	 * 
	 * @return 输入的字符串不为空则返回true,反之则返回false
	 */
	public static boolean validateNotNull(String args) {
		return !validateNull(args);
	}

	/**
	 * 对输入为空或者是"null"字符的字符串参数进行处理。
	 * 
	 * @param source
	 *            源字符串
	 * @param target
	 *            目标字符串
	 * 
	 * @return 输入的字符串为空或者是"null"字符则返回目标字符串，反之则返回源字符串
	 */
	public static String chanageNull(String source, String target) {
		if (source == null || source.length() == 0 || source.equalsIgnoreCase("null")) {
			return target;
		} else {
			return source;
		}
	}

	/**
	 * 根据号段phonePrefix与num生成11位手机号
	 * 
	 * @param phonePrefix
	 *            号段
	 * @param num
	 *            号码
	 * @return
	 */
	public static final String bulidPhoneNum(String phonePrefix, int num) {
		String phoneNum = String.format("%1$s%2$04d", new Object[] { phonePrefix, num });
		return phoneNum;
	}

	/**
	 * 过滤<, >,\n等特殊字符的方法。
	 * 
	 * @param input
	 *            需要过滤的字符
	 * @return 完成过滤以后的字符串
	 */
	public static final String filterStr(String str) {
		str = str.replaceAll(";", "");
		str = str.replaceAll("&", "&amp;");
		str = str.replaceAll("<", "&lt;");
		str = str.replaceAll(">", "&gt;");
		str = str.replaceAll("'", "");
		str = str.replaceAll("--", " ");
		str = str.replaceAll("/", "");
		str = str.replaceAll("%", "");
		str = str.replaceAll(" ", "&nbsp;");
		str = str.replaceAll("\"", "&quot;");
		str = str.replaceAll("\n", "<br>");
		str = str.replaceAll("\r", "<br>");
		return str;
	}

	/**
	 * 将字符串转成unicode
	 * 
	 * @param str
	 *            待转字符串
	 * @return unicode字符串
	 */
	public static String convert(String str) {
		str = (str == null ? "" : str);
		String tmp;
		StringBuffer sb = new StringBuffer(1000);
		char c;
		int i, j;
		sb.setLength(0);
		for (i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			sb.append("\\u");
			j = (c >>> 8); // 取出高8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);
			j = (c & 0xFF); // 取出低8位
			tmp = Integer.toHexString(j);
			if (tmp.length() == 1)
				sb.append("0");
			sb.append(tmp);

		}
		return (new String(sb));
	}

	/**
	 * 将unicode转成字符串
	 * 
	 * @param str
	 *            待转字符串
	 * @return 普通字符串
	 */
	public static String revert(String str) {
		str = (str == null ? "" : str);
		if (str.indexOf("\\u") == -1)// 如果不是unicode码则原样返回
			return str;

		StringBuffer sb = new StringBuffer(1000);

		for (int i = 0; i < str.length() - 6;) {
			String strTemp = str.substring(i, i + 6);
			String value = strTemp.substring(2);
			int c = 0;
			for (int j = 0; j < value.length(); j++) {
				char tempChar = value.charAt(j);
				int t = 0;
				switch (tempChar) {
				case 'a':
					t = 10;
					break;
				case 'b':
					t = 11;
					break;
				case 'c':
					t = 12;
					break;
				case 'd':
					t = 13;
					break;
				case 'e':
					t = 14;
					break;
				case 'f':
					t = 15;
					break;
				default:
					t = tempChar - 48;
					break;
				}

				c += t * ((int) Math.pow(16, (value.length() - j - 1)));
			}
			sb.append((char) c);
			i = i + 6;
		}
		return sb.toString();
	}

	public static String get_node_text(String respContent, String nodeName) {

		String dest_string = "<" + nodeName;
		int start = respContent.indexOf(dest_string);
		if (-1 == start)
			return "";
		start = respContent.indexOf('>', start + 1);
		if (-1 == start)
			return "";
		start += 1;

		dest_string = "</" + nodeName + ">";
		int end = respContent.indexOf(dest_string, start);
		if (-1 == end)
			return "";

		return respContent.substring(start, end);

	}

	public static byte[] toBytes(String string, String charset) {
		try {
			return string.getBytes(charset);
		} catch (UnsupportedEncodingException e) {
			logger.error("to bytes occur error, string={}, charset={}", string, charset);
		}
		return null;
	}

	public static String toString(byte[] bytes, String charset) {
		try {
			return new String(bytes, charset);
		} catch (UnsupportedEncodingException e) {
			logger.error("to string occur error, bytes={}, charset={}", bytes, charset);
		}
		return null;
	}

	public static String inTextArea(String str) {
		// 下面的代码将字符串以正确方式显示（包括回车，换行，空格）
		while (str.indexOf("<br>") != -1) {
			str = str.substring(0, str.indexOf("<br>")) + "\r\n" + str.substring(str.indexOf("<br>") + 4);
		}

		while (str.indexOf("&nbsp;") != -1) {
			str = str.substring(0, str.indexOf("&nbsp;")) + " " + str.substring(str.indexOf("&nbsp;") + 6);
		}
		while (str.indexOf("&lt;") != -1) {
			str = str.substring(0, str.indexOf("&lt;")) + "<" + str.substring(str.indexOf("&lt;") + 4);
		}
		while (str.indexOf("&gt;") != -1) {
			str = str.substring(0, str.indexOf("&gt;")) + "<" + str.substring(str.indexOf("&gt;") + 4);
		}
		return str;
	}

	public static void main(String[] args) {
		// System.out.println(bulidPhoneNum("1681234", 789));
		// System.out.println(convert("测试"));
		System.out.println(revert("\u767b\u5f55\u6210\u529f\uff0c\u6b63\u5728\u4e3a\u60a8\u8df3\u8f6c"));
	}
}
