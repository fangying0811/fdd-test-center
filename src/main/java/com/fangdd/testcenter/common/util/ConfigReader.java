package com.fangdd.testcenter.common.util;

import com.fangdd.qa.framework.utils.common.DateTimeUtil;
import com.fangdd.qa.framework.utils.common.PropUtil;

public class ConfigReader {
	private static PropUtil propUtil = new PropUtil("config/server.properties");
	/**
	 * 暂停时间,单位:毫秒
	 */
	public static long SLEEPTIME_MILLIS = 250;
	/**
	 * 暂停时间,单位:秒
	 */
	public static long SLEEPTIME = 1;
	//public static String smtp = propUtil.get("mail.smtp");
	//public static int smtpPort = Integer.parseInt(propUtil.get("mail.smtp.port"));
	//public static String from = propUtil.get("mail.from");
	//public static String password = propUtil.get("mail.password");
	//public static String RedmineServiceUrl = propUtil.get("RedmineServiceUrl");
	//public static String RedmineAPIKey = propUtil.get("RedmineAPIKey");
//	public static String SonarServiceUrl = propUtil.get("SonarServiceUrl");
//	public static String SonarUserName = propUtil.get("SonarUserName");
//	public static String SonarPassword = propUtil.get("SonarPassword");
//	public static String TqpeHookUrl = propUtil.get("TQPE.Hook.Url");
	public static String DATE_FORMAT = "yyyy-MM-dd";
	public static String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
	// 上周
	public static String lastWeek = DateTimeUtil.lastSunday(DateTimeUtil.getTimestamp(DATE_FORMAT), DATE_FORMAT);
	// 本周
	public static String nowWeek = DateTimeUtil.nowFirday(DateTimeUtil.getTimestamp(DATE_FORMAT), DATE_FORMAT);
	public static String lastMonday = DateTimeUtil
			.convertDate(DateTimeUtil.lastMonday(DateTimeUtil.getTimestamp(DATE_FORMAT), DATE_FORMAT), true);
	public static String lastSunday = DateTimeUtil
			.convertDate(DateTimeUtil.lastSunday(DateTimeUtil.getTimestamp(DATE_FORMAT), DATE_FORMAT), false);
	public static String nowMonday = DateTimeUtil
			.convertDate(DateTimeUtil.nowMonday(DateTimeUtil.getTimestamp(DATE_FORMAT), DATE_FORMAT), true);
	public static String nowSunday = DateTimeUtil
			.convertDate(DateTimeUtil.nowSunday(DateTimeUtil.getTimestamp(DATE_FORMAT), DATE_FORMAT), false);
	// 不做统计的sonar项目，多个项目逗号分隔
	public static String EXCLUDE_SONAR_PAOJECTID = propUtil.get("EXCLUDE_SONAR_PAOJECTID");
	public static String STATISTIC_LABLES_DATE_FORMAT = "yyyy-MM";
	public static String[] STATISTIC_LABLES = new String[] {
			DateTimeUtil.getMonthsByFormatter(-5, STATISTIC_LABLES_DATE_FORMAT),
			DateTimeUtil.getMonthsByFormatter(-4, STATISTIC_LABLES_DATE_FORMAT),
			DateTimeUtil.getMonthsByFormatter(-3, STATISTIC_LABLES_DATE_FORMAT),
			DateTimeUtil.getMonthsByFormatter(-2, STATISTIC_LABLES_DATE_FORMAT),
			DateTimeUtil.getMonthsByFormatter(-1, STATISTIC_LABLES_DATE_FORMAT),
			DateTimeUtil.getTimestamp(STATISTIC_LABLES_DATE_FORMAT) };
	public static String SINAR_STATISTIC_LABLES_DATE_FORMAT = "yyyy-MM-dd";
	public static String[] SONAR_STATISTIC_LABLES = new String[] {
			DateTimeUtil.getDaysByFormatter(-42, nowWeek, SINAR_STATISTIC_LABLES_DATE_FORMAT),
			DateTimeUtil.getDaysByFormatter(-35, nowWeek, SINAR_STATISTIC_LABLES_DATE_FORMAT),
			DateTimeUtil.getDaysByFormatter(-28, nowWeek, SINAR_STATISTIC_LABLES_DATE_FORMAT),
			DateTimeUtil.getDaysByFormatter(-21, nowWeek, SINAR_STATISTIC_LABLES_DATE_FORMAT),
			DateTimeUtil.getDaysByFormatter(-14, nowWeek, SINAR_STATISTIC_LABLES_DATE_FORMAT),
			DateTimeUtil.getDaysByFormatter(-7, nowWeek, SINAR_STATISTIC_LABLES_DATE_FORMAT) };
	public static String[] COLORS = new String[] { "#0000ff", "#FF0000", "#CC00FF", "#33CC00", "#FF00FF", "#FF6600",
			"#9933CC" };

	public static int getSteps(int max) {
		if (max >= 0 && max <= 10) {
			return 2;
		} else if (max > 10 && max <= 100) {
			return 10;
		} else if (max > 100 && max <= 200) {
			return 20;
		} else if (max > 200 && max <= 300) {
			return 25;
		} else if (max > 300 && max <= 400) {
			return 30;
		} else if (max > 400 && max <= 500) {
			return 35;
		} else if (max > 500 && max <= 600) {
			return 40;
		} else if (max > 600 && max <= 700) {
			return 45;
		} else if (max > 700 && max <= 800) {
			return 50;
		} else if (max > 800 && max <= 900) {
			return 55;
		} else if (max > 900 && max <= 1000) {
			return 60;
		} else if (max > 1000) {
			return 60;
		} else {
			return 1;
		}
	}

	// 统计工作量的周报类型
	// 类型：1，版本测试 2，其他 3，活动测试 4，临时需求测试
	public static String TYPE_LIST_STR = "1,3,4";

	public static void main(String[] args) {

	//	System.out.println(ConfigReader.password);
	}
}
