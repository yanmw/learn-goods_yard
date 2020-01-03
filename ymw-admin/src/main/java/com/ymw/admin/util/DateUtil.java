package com.ymw.admin.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

/**
 * Copyright (C), 2006-2010, ShanghaiLeiBao Co., Ltd. FileName: DateUtil.java
 * 日期操作工具类
 *
 * @author ycp
 * @Date 2018年4月25日
 * @version 1.00
 */
public class DateUtil {

	// 日志
	protected static Log logger = LogFactory.getLog(DateUtil.class);

	// 格式
	public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
	public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
	public static final String YYYYMMDD_HHMMSS = "yyyyMMdd-HHmmss";
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYYMMDD = "yyyyMMdd";
	public static final String YYYYMMDDS = "yyyy/MM/dd";
	public static final String MM_DD = "MM-dd";
	public static final String HH_MM_SS = "HH:mm:ss";
	public static final String YYYY_MM = "yyyy-MM";
	public static final int SUB_YEAR = 1;
	public static final int SUB_MONTH = 2;
	public static final int SUB_DAY = 5;
	public static final int SUB_HOUR = 10;
	public static final int SUB_MINUTE = 12;
	public static final int SUB_SECOND = 13;
	static final String[] a = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	/**
	 * 字符串转为时间
	 * 
	 * @author ycp
	 * @param string 要转换的字符串
	 * @param format 转换格式
	 * @return 日期
	 * 
	 *         修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static Date stringtoDate(String string, String format) {
		Date localDate = null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(format);
		localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			localSimpleDateFormat.setLenient(false);
			localDate = localSimpleDateFormat.parse(string);
		} catch (Exception localException) {
			localDate = null;
			logger.error(" stringtoDate Exception! ", localException);
		}
		return localDate;
	}

	public static Date StringtoDate(String string, String format) {
		Date localDate = null;
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(format);
		localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			localSimpleDateFormat.setLenient(false);
			localDate = localSimpleDateFormat.parse(string);
		} catch (Exception localException) {
			localDate = null;
		}
		return localDate;
	}

    /**
     * 比较两个时间差
     * @param date1 时间1
     * @param date2 时间2
     * @return 时间1 大于 时间2  返回true
     */
    public static boolean diffBoolDate(Date date1, Date date2) {
        return (date1.getTime() - date2.getTime()) > 0 ;
    }

    /**
     * 比较两个时间差
     * @param date1 时间1
     * @param date2 时间2
     * @return 时间1 - 时间2
     */
    public static long diffLongDate(Date date1, Date date2) {
        return date1.getTime() - date2.getTime();
    }

	
	
	/**
	 * 将字符串装换成日期类型
	 * 
	 * @param str 需要转换的字符串
	 * @return Date 转换后的日期
	 */
	public static Date convertToDate(String format, String str) {

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {

			return sdf.parse(str);
		} catch (ParseException pse) {

			return null;
		}
	}

	/**
	 * 字符串转为时间标明解析开始位
	 * 
	 * @author ycp
	 * @param string        要转换的字符串
	 * @param format        转换格式
	 * @param parsePosition 标明解析开始位
	 * @return 日期
	 * 
	 *         修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static Date stringtoDate(String string, String format, ParsePosition parsePosition) {
		Date localDate = null;
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			simpleDateFormat.setLenient(false);
			localDate = simpleDateFormat.parse(string, parsePosition);
		} catch (Exception localException) {
			localDate = null;
			logger.error(" stringtoDate Exception! ", localException);
		}
		return localDate;
	}

	/**
	 * 将日志类型转为字符串
	 * 
	 * @author ycp
	 * @param date   要转换的日志
	 * @param string 要转换的字符串
	 * @return 字符串
	 * 
	 *         修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static String dateToString(Date date, String string) {
		String str = "";
		SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(string);
		localSimpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
		try {
			str = localSimpleDateFormat.format(date);
		} catch (Exception localException) {
			logger.error(" dateToString Exception! ", localException);
		}
		return str;
	}

	/**
	 * 按照指定格式获取当前时间
	 * 
	 * @author ycp
	 * @param format 日期格式
	 * @return 指定格式的当前时间
	 * 
	 *         修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static String getCurrDate(String format) {
		return dateToString(new Date(), format);
	}

	/**
	 * 日期截取
	 * 
	 * @author ycp
	 * @param num
	 * @param string
	 * @param num1
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static String dateSub(int num, String string, int num1) {
		Date date = stringtoDate(string, "yyyy-MM-dd HH:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(num, num1);
		return dateToString(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 计算两个时间相差的long值
	 * 
	 * @author ycp
	 * @param string
	 * @param string1
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static long timeSub(String string, String string1) {
		long l1 = stringtoDate(string, "yyyy-MM-dd HH:mm:ss").getTime();
		long l2 = stringtoDate(string1, "yyyy-MM-dd HH:mm:ss").getTime();
		return (l2 - l1) / 1000L;
	}

	/**
	 * 获取一月多少天
	 * 
	 * @author ycp
	 * @param year
	 * @param month
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static int getDaysOfMonth(String year, String month) {
		int i = 0;
		if ((month.equals("1")) || (month.equals("3")) || (month.equals("5")) || (month.equals("7"))
				|| (month.equals("8")) || (month.equals("10")) || (month.equals("12")))
			i = 31;
		else if ((month.equals("4")) || (month.equals("6")) || (month.equals("9")) || (month.equals("11"))) {
			i = 30;
		} else if (((Integer.parseInt(year) % 4 == 0) && (Integer.parseInt(year) % 100 != 0))
				|| (Integer.parseInt(year) % 400 == 0))
			i = 29;
		else {
			i = 28;
		}

		return i;
	}

	/**
	 * 获得第几周多少天
	 * 
	 * @author ycp
	 * @param num
	 * @param num1
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static int getDaysOfMonth(int num, int num1) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(num, num1 - 1, 1);
		return calendar.getActualMaximum(5);
	}

	public static int getToday() {
		Calendar localCalendar = Calendar.getInstance();
		return localCalendar.get(5);
	}

	public static int getToMonth() {
		Calendar localCalendar = Calendar.getInstance();
		return localCalendar.get(2) + 1;
	}

	public static int getToYear() {
		Calendar localCalendar = Calendar.getInstance();
		return localCalendar.get(1);
	}

	public static int getDay(Date paramDate) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		return localCalendar.get(5);
	}

	public static int getYear(Date paramDate) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		return localCalendar.get(1);
	}

	public static int getMonth(Date paramDate) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(paramDate);
		return localCalendar.get(2) + 1;
	}

	/**
	 * 相差天数
	 * 
	 * @author ycp
	 * @param date1
	 * @param date2
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static long dayDiff(Date date1, Date date2) {
		return (date2.getTime() - date1.getTime()) / 86400000L;
	}

	/**
	 * 相差多少钱年
	 * 
	 * @author ycp
	 * @param paramString1
	 * @param paramString2
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static int yearDiff(String paramString1, String paramString2) {
		Date localDate1 = stringtoDate(paramString1, "yyyy-MM-dd");
		Date localDate2 = stringtoDate(paramString2, "yyyy-MM-dd");
		return getYear(localDate2) - getYear(localDate1);
	}

	public static int yearDiffCurr(String paramString) {
		Date localDate1 = new Date();
		Date localDate2 = stringtoDate(paramString, "yyyy-MM-dd");
		return getYear(localDate1) - getYear(localDate2);
	}

	public static long dayDiffCurr(String paramString) {
		Date localDate1 = stringtoDate(currDay(), "yyyy-MM-dd");
		Date localDate2 = stringtoDate(paramString, "yyyy-MM-dd");
		return (localDate1.getTime() - localDate2.getTime()) / 86400000L;
	}

	public static int getFirstWeekdayOfMonth(int paramInt1, int paramInt2) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setFirstDayOfWeek(7);
		localCalendar.set(paramInt1, paramInt2 - 1, 1);
		return localCalendar.get(7);
	}

	public static int getLastWeekdayOfMonth(int num, int num2) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setFirstDayOfWeek(7);
		localCalendar.set(num, num2 - 1, getDaysOfMonth(num, num2));
		return localCalendar.get(7);
	}

	/**
	 * 获取指定格式的当前时间
	 * 
	 * @author ycp
	 * @return 当前时间
	 * 
	 *         修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static String getCurrDateTime() {
		Calendar localCalendar = Calendar.getInstance();
		return dateToString(localCalendar.getTime(), "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 获取星座
	 * 
	 * @author ycp
	 * @param string
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static String getAstro(String string) {
		if (!isDate(string)) {
			string = "2000" + string;
		}
		if (!isDate(string)) {
			return "";
		}
		int i = Integer.parseInt(string.substring(string.indexOf("-") + 1, string.lastIndexOf("-")));
		int j = Integer.parseInt(string.substring(string.lastIndexOf("-") + 1));
		String str = "魔羯水瓶双鱼牡羊金牛双子巨蟹狮子处女天秤天蝎射手魔羯";
		int[] arrayOfInt = { 20, 19, 21, 21, 21, 22, 23, 23, 23, 23, 22, 22 };
		int k = i * 2 - (j < arrayOfInt[(i - 1)] ? 2 : 0);
		return str.substring(k, k + 2) + "座";
	}

	/**
	 * 判读字符串是否符合日期格式
	 * 
	 * @author ycp
	 * @param string
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static boolean isDate(String string) {
		StringBuffer stringBuffer = new StringBuffer("^((\\d{2}(([02468][048])|([13579][26]))-?((((0?");
		stringBuffer.append("[13578])|(1[02]))-?((0?[1-9])|([1-2][0-9])|(3[01])))");
		stringBuffer.append("|(((0?[469])|(11))-?((0?[1-9])|([1-2][0-9])|(30)))|");
		stringBuffer.append("(0?2-?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][12");
		stringBuffer.append("35679])|([13579][01345789]))-?((((0?[13578])|(1[02]))");
		stringBuffer.append("-?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))");
		stringBuffer.append("-?((0?[1-9])|([1-2][0-9])|(30)))|(0?2-?((0?[");
		stringBuffer.append("1-9])|(1[0-9])|(2[0-8]))))))");
		Pattern localPattern = Pattern.compile(stringBuffer.toString());
		return localPattern.matcher(string).matches();
	}

	/**
	 * 指定日期的下多少月
	 * 
	 * @author ycp
	 * @param date
	 * @param num
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static Date nextMonth(Date date, int num) {
		Calendar localCalendar = Calendar.getInstance();
		if (date != null) {
			localCalendar.setTime(date);
		}
		localCalendar.add(2, num);
		return localCalendar.getTime();
	}

	/**
	 * 指定日期下多少天
	 * 
	 * @author ycp
	 * @param date
	 * @param num
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static Date nextDay(Date date, int num) {
		Calendar localCalendar = Calendar.getInstance();
		if (date != null) {
			localCalendar.setTime(date);
		}
		localCalendar.add(6, num);
		return localCalendar.getTime();
	}

	/**
	 * 下一天
	 * 
	 * @author ycp
	 * @param num
	 * @param format
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static String nextDay(int num, String format) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.setTime(new Date());
		localCalendar.add(6, num);
		return dateToString(localCalendar.getTime(), format);
	}

	/**
	 * 下一周
	 * 
	 * @author ycp
	 * @param date
	 * @param num
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static Date nextWeek(Date date, int num) {
		Calendar localCalendar = Calendar.getInstance();
		if (date != null) {
			localCalendar.setTime(date);
		}
		localCalendar.add(4, num);
		return localCalendar.getTime();
	}

	/**
	 * 当前时间
	 * 
	 * @author ycp
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static String currDay() {
		return dateToString(new Date(), "yyyy-MM-dd");
	}

	/**
	 * 前多少天
	 * 
	 * @author ycp
	 * @param day
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static String befoDay(int day) {
		return befoDay(day, "yyyy-MM-dd");
	}

	/**
	 * 昨天
	 * 
	 * @author ycp
	 * @param paramInt
	 * @param paramString
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static String befoDay(int paramInt, String paramString) {
		return dateToString(nextDay(new Date(), -paramInt), paramString);
	}

	/**
	 * 明天
	 * 
	 * @author ycp
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static String afterDay() {
		return dateToString(nextDay(new Date(), 1), "yyyy-MM-dd");
	}

	/**
	 * 获取当前时间距1900年1月1日多少天
	 * 
	 * @author ycp
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static int getDayNum() {
		int i = 0;
		GregorianCalendar calendar = new GregorianCalendar();
		Date date = calendar.getTime();
		GregorianCalendar calendar2 = new GregorianCalendar(1900, 1, 1);
		Date localDate2 = calendar2.getTime();
		i = (int) ((date.getTime() - localDate2.getTime()) / 86400000L);
		return i;
	}

	/**
	 * 获取1900年1月1日后多少天的日期
	 * 
	 * @author ycp
	 * @param num
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static Date getDateByNum(int num) {
		GregorianCalendar calendar = new GregorianCalendar(1900, 1, 1);
		Date date = calendar.getTime();
		date = nextDay(date, num);
		return date;
	}

	/**
	 * 获取年月日格式
	 * 
	 * @author ycp
	 * @param string
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static String getYmdDateCN(String string) {
		if (string == null)
			return "";
		if (string.length() < 10)
			return "";
		StringBuffer buffer = new StringBuffer();
		buffer.append(string.substring(0, 4)).append(string.substring(5, 7)).append(string.substring(8, 10));
		return buffer.toString();
	}

	/**
	 * 获取某月的第一天
	 * 
	 * @author ycp
	 * @param string
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static String getFirstDayOfMonth(String string) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(5, 1);
		return dateToString(localCalendar.getTime(), string);
	}

	/**
	 * 获取某月的最后一天
	 * 
	 * @author ycp
	 * @param string
	 * @return
	 * 
	 * 		修改人：ycp 修改日期： 2018年4月25日 修改内容：
	 */
	public static String getLastDayOfMonth(String string) {
		Calendar localCalendar = Calendar.getInstance();
		localCalendar.set(5, 1);
		localCalendar.add(2, 1);
		localCalendar.add(5, -1);
		return dateToString(localCalendar.getTime(), string);
	}

	/**
	 * 获取当前日期的前多少天的日期 TODO 修改方法名....
	 * 
	 * @return
	 */
	public static Date getDatelaozhang0000(int number) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - number);

		return calendar.getTime();
	}

	/**
	 * 当前时间往前推多少个小时的时间 TODO 修改方法名....
	 * 
	 * @return
	 */
	public static Date getDatelaozhang0001(int number) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - number);
		return calendar.getTime();
	}

	/**
	 * 某个时间往前推i个小时的时间 TODO
	 * 
	 * @return
	 */
	public static Date getAfterTime(Date time, int number, int timeType) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(time);
		calendar.add(timeType, number);
		return calendar.getTime();
	}

	/**
	 * @Title: getDutyDays
	 * @Description: TODO 两个日期之间周日有多少个
	 * @param: @param startDate
	 * @param: @param endDate
	 * @param: @return
	 * @return: int
	 * @date:2019年4月1日 下午2:12:29
	 */
	@SuppressWarnings("deprecation")
	public static int getDutyDays0(Date startDate, Date endDate) {
		int result = 0;
		while (startDate.compareTo(endDate) <= 0) {
			if (startDate.getDay() == 0)
				result++;
			startDate.setDate(startDate.getDate() + 1);
		}
		return result;

	}

	/**
	 * @Title: getDutyDays
	 * @Description: TODO 两个日期之间周六日有多少个
	 * @param: @param startDate
	 * @param: @param endDate
	 * @param: @return
	 * @return: int
	 * @date:2019年4月1日 下午2:12:29
	 */
	@SuppressWarnings("deprecation")
	public static int getDutyDays01(Date startDate, Date endDate) {
		int result = 0;
		while (startDate.compareTo(endDate) <= 0) {
			if (startDate.getDay() == 6 || startDate.getDay() == 0)
				result++;
			startDate.setDate(startDate.getDate() + 1);
		}
		return result;

	}

	/**
	 * 
	 * 根据开始日期 ，需要的工作日天数 ，计算工作截止日期，并返回截止日期
	 * 
	 * @param startDate 开始日期
	 * @param workDay   工作日天数(周一到周五)
	 * @author
	 * @time
	 */

	public static Date getWorkDayDouble(Date startDate, int workDay) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);
		for (int i = 0; i < workDay; i++) {
			c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
			if (Calendar.SATURDAY == c1.get(Calendar.DAY_OF_WEEK) || Calendar.SUNDAY == c1.get(Calendar.DAY_OF_WEEK)) {
				workDay = workDay + 1;
				c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
				continue;
			}
		}
		return c1.getTime();
	}

	/*
	 * 判断当前日期是否是周六日
	 */
	public static Date getbeginTime(Date startDate) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);
//		if (Calendar.SATURDAY == c1.get(Calendar.DAY_OF_WEEK) || Calendar.SUNDAY == c1.get(Calendar.DAY_OF_WEEK)) {
//			// 将时分秒,毫秒域清零
//			c1.set(Calendar.HOUR_OF_DAY, 23);
//			c1.set(Calendar.MINUTE, 59);
//			c1.set(Calendar.SECOND, 59);
//		}
		while (Calendar.SATURDAY == c1.get(Calendar.DAY_OF_WEEK) || Calendar.SUNDAY == c1.get(Calendar.DAY_OF_WEEK)) {
			c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
		}
		return c1.getTime();
	}

	/**
	 * 
	 * 根据开始日期 ，需要的工作日天数 ，计算工作截止日期，并返回截止日期
	 * 
	 * @param startDate 开始日期
	 * @param workDay   工作日天数(周日到周五)
	 * @author
	 * @time
	 */

	public static Date getWorkDay6(Date startDate, int workDay) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);
		for (int i = 0; i < workDay; i++) {
			c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
			if (Calendar.SATURDAY == c1.get(Calendar.SATURDAY)) {
				workDay = workDay + 1;
				c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
				continue;
			}
		}
		return c1.getTime();
	}

	/**
	 * 结算节假日和周日的时间
	 * 
	 * @param startDate
	 * @param workDay
	 * @return
	 * @throws Exception
	 */
	public static Date getHolidays01(Date startDate, int workDay) throws Exception {

		// 得到节假日列表
		Calendar c2 = Calendar.getInstance();
		c2.setTime(startDate);
		List<String> holidays = getDateHolidays();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar c1 = Calendar.getInstance();
		
		startDate.setHours(0);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		c1.setTime(startDate);
		
		for (int i = 0; i < workDay; i++) {
			c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
			Date time = c1.getTime();
			String timeStr = sdf.format(time);
			
			if (Calendar.SUNDAY == c1.get(Calendar.DAY_OF_WEEK)) {
				//workDay = workDay + 1;
				c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
				continue;
			}
			
			if (holidays.contains(timeStr)) {
				workDay = workDay + 1;
				c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
				continue;
			}
		}
		
		c1.set(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY));
		c1.set(Calendar.MINUTE, c2.get(Calendar.MINUTE));
		c1.set(Calendar.SECOND, c2.get(Calendar.SECOND));
		return c1.getTime();
	}
	
	public static Date getHolidays02(Date startDate, int workDay) throws Exception {

		// 得到节假日列表
		Calendar c2 = Calendar.getInstance();
		c2.setTime(startDate);
		List<String> holidays = getDateHolidays();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar c1 = Calendar.getInstance();
		
		startDate.setHours(0);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		c1.setTime(startDate);
		
		for (int i = 0; i < workDay; i++) {
			c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
			Date time = c1.getTime();
			String timeStr = sdf.format(time);
			if (Calendar.SATURDAY == c1.get(Calendar.DAY_OF_WEEK) || Calendar.SUNDAY == c1.get(Calendar.DAY_OF_WEEK)) {
				workDay = workDay + 1;
				c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
				continue;
			}
			
			if (holidays.contains(timeStr)) {
				workDay = workDay + 1;
				c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
				if (Calendar.SATURDAY == c1.get(Calendar.DAY_OF_WEEK) || Calendar.SUNDAY == c1.get(Calendar.DAY_OF_WEEK)) {
					c1.set(Calendar.DATE, c1.get(Calendar.DATE) - 1);
				}
				continue;
			}
		}
		
		c1.set(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY));
		c1.set(Calendar.MINUTE, c2.get(Calendar.MINUTE));
		c1.set(Calendar.SECOND, c2.get(Calendar.SECOND));
		return c1.getTime();
	}
	
	
	public static Date getHolidays03(Date startDate, int workDay) throws Exception {

		// 得到节假日列表
		Calendar c2 = Calendar.getInstance();
		c2.setTime(startDate);
		List<String> holidays = getDateHolidays();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Calendar c1 = Calendar.getInstance();
		startDate.setHours(0);
		startDate.setMinutes(0);
		startDate.setSeconds(0);
		c1.setTime(startDate);
		
		for (int i = 0; i < workDay; i++) {
			c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
			// 周日和节假日
			Date time = c1.getTime();
			String timeStr = sdf.format(time);
			if (holidays.contains(timeStr)) {
				 workDay = workDay + 1;
				c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
				continue;
			}
		}
		c1.set(Calendar.HOUR_OF_DAY, c2.get(Calendar.HOUR_OF_DAY));
		c1.set(Calendar.MINUTE, c2.get(Calendar.MINUTE));
		c1.set(Calendar.SECOND, c2.get(Calendar.SECOND));
		return c1.getTime();
	}
	
	

	public static List<String> getDateHolidays() throws ParseException {
		// 2019-2020年的节假日
		List<String> dateStr = Arrays.asList("2019-01-01 00:00:00", "2019-02-04 00:00:00", "2019-02-05 00:00:00",
				"2019-02-06 00:00:00", "2019-02-07 00:00:00", "2019-02-08 00:00:00", "2019-02-10 00:00:00",
				"2019-04-05 00:00:00", "2019-04-06 00:00:00", "2019-04-07 00:00:00", "2019-05-01 00:00:00",
				"2019-05-02 00:00:00", "2019-05-03 00:00:00", "2019-05-04 00:00:00", "2019-06-07 00:00:00",
				"2019-06-08 00:00:00", "2019-06-09 00:00:00", "2019-09-13 00:00:00", "2019-09-14 00:00:00",
				"2019-09-15 00:00:00", "2019-10-01 00:00:00", "2019-10-02 00:00:00", "2019-10-03 00:00:00",
				"2019-10-04 00:00:00", "2019-10-05 00:00:00", "2019-10-06 00:00:00", "2019-10-07 00:00:00",
				"2020-01-01 00:00:00", "2020-01-24 00:00:00", "2020-01-25 00:00:00", "2020-01-26 00:00:00",
				"2020-01-27 00:00:00", "2020-01-28 00:00:00", "2020-01-29 00:00:00", "2020-01-30 00:00:00",
				"2020-04-04 00:00:00", "2020-04-05 00:00:00", "2020-04-06 00:00:00", "2020-05-01 00:00:00",
				"2020-05-02 00:00:00", "2020-05-03 00:00:00", "2020-06-25 00:00:00", "2020-06-26 00:00:00",
				"2020-06-27 00:00:00", "2020-10-01 00:00:00", "2020-10-02 00:00:00", "2020-10-03 00:00:00",
				"2020-10-04 00:00:00", "2020-10-05 00:00:00", "2020-10-06 00:00:00", "2020-10-07 00:00:00",
				"2020-10-08 00:00:00");
		return dateStr;
		
	}

	/**
	 * 
	 * 根据开始日期 ，需要的工作日天数 ，计算工作截止日期，并返回截止日期
	 * 
	 * @param startDate 开始日期
	 * @param workDay   工作日天数(周日到周五)
	 * @author
	 * @time
	 */

	public static Date getWorkDay7(Date startDate, int workDay) {
		Calendar c1 = Calendar.getInstance();
		c1.setTime(startDate);
		for (int i = 0; i < workDay; i++) {
			c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
			if (Calendar.SUNDAY == c1.get(Calendar.DAY_OF_WEEK)) {
				//workDay = workDay + 1;
				c1.set(Calendar.DATE, c1.get(Calendar.DATE) + 1);
				continue;
			}
		}
		return c1.getTime();
	}

	public static void main(String[] args) {
		int dutyDays0 = getDutyDays01(stringtoDate("2019-3-30", YYYY_MM_DD), stringtoDate("2019-4-7", YYYY_MM_DD));

	}

}
