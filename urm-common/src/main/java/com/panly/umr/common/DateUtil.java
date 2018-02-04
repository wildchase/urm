package com.panly.umr.common;


import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 日期工具类
 * </p>
 * 
 * @project core
 * @class DateUtil.java
 * @author a@panly.me
 * @date 2017年6月1日下午12:43:21
 */
public final class DateUtil extends DateUtils {

	private static Logger logger = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 显示日期 年月日
	 */
	public static String DATE_PATTERN = "yyyy-MM-dd";

	/**
	 * 显示具体到秒的时间
	 */
	public static String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 14位时间格式
	 */
	public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

	/**
	 * 8位年月日时间格式
	 */
	public static final String YYYYMMDD = "yyyyMMdd";

	/**
	 * 格式化当前日期为yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static String getCurrDateTime() {
		return formatDate(new Date(), TIME_PATTERN);
	}

	/**
	 * 格式化当前日期为yyyy-MM-dd
	 * 
	 * @return
	 */
	public static String getCurrDate() {
		return formatDate(new Date(), DATE_PATTERN);
	}

	/**
	 * 转换 date 成对应格式的string
	 * 
	 * @param date
	 * @param pattern
	 *            格式
	 * @return
	 */
	public static String formatDate(Date date, String pattern) {
		if (date == null) {
			return null;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(date);
		}
	}

	/**
	 * 获取当前时间指定小时以后的日期
	 * 
	 * @param currentDate
	 *            当前时间
	 * @param hour
	 *            小时，
	 * @return
	 */
	public static Date getCurrentDateAfterDateByHour(Date currentDate, int hour) {
		Calendar c = Calendar.getInstance();
		c.setTime(currentDate);
		c.set(Calendar.HOUR, hour);
		return c.getTime();
	}

	/**
	 * 按指定的格式sFormat将日期dDate转化为字符串
	 */
	public static String date2String(Date date, String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 将字符串按指定格式转换为java.util.Date类型
	 */
	public static Date string2Date(String str, String format) {
		Date result = null;
		try {
			DateFormat mFormat = new SimpleDateFormat(format);
			result = mFormat.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将给定时间移动相对月份.
	 *
	 * @param beginDate
	 *            起始日期
	 * @param amount
	 *            数量
	 * @return 结果日期
	 */
	public static Date moveMonth(Date beginDate, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);

		cal.add(Calendar.MONTH, amount);

		return cal.getTime();
	}

	/**
	 * 将给定时间移动相对天数.
	 *
	 * @param beginDate
	 *            起始日期
	 * @param amount
	 *            数量
	 * @return 结果日期
	 */
	public static Date moveDay(Date beginDate, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(beginDate);

		cal.add(Calendar.DATE, amount);

		return cal.getTime();
	}

	/**
	 * 将给定字符串时间移动相对月份.
	 *
	 * @param year
	 *            年字符串
	 * @param month
	 *            月
	 * @param amount
	 *            数量
	 * @return int[ 年, 月 ]
	 */
	public static int[] moveMonth(String year, String month, int amount) {
		Date d = moveMonth(parseDate(year + "/" + month, "yyyy/MM"), amount);

		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		return new int[] { cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) };
	}

	/**
	 * 解析日期.
	 *
	 * @param input
	 *            输入字符串
	 * @param pattern
	 *            类型
	 * @return Date 对象
	 */
	public static Date parseDate(String input, String pattern) {
		if (StringUtils.isEmpty(input)) {
			return null;
		}

		SimpleDateFormat df = new SimpleDateFormat(pattern);

		try {
			return df.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 格式化日期到日时分秒时间格式的显示. d日 HH:mm:ss
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToDHMSString(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("d日 HH:mm:ss");
		return dateFormat.format(date);

	}

	/**
	 * 格式化日期到时分秒时间格式的显示.
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToHMSString(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		return dateFormat.format(date);

	}

	/**
	 * 将时分秒时间格式的字符串转换为日期.
	 */
	public static Date parseHMSStringToDate(String input) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
		try {
			return dateFormat.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 格式化日期到 Mysql 数据库日期格式字符串的显示.
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToMysqlString(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(date);

	}

	/**
	 * 将 Mysql 数据库日期格式字符串转换为日期.
	 */
	public static Date parseStringToMysqlDate(String input) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return dateFormat.parse(input);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 返回时间字符串, 可读形式的, M月d日 HH:mm 格式. 2004-09-22, 
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToMMddHHmm(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("M月d日 HH:mm");
		return dateFormat.format(date);
	}

	/**
	 * 返回时间字符串, 可读形式的, yy年M月d日HH:mm 格式. 2004-10-04, 
	 *
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToyyMMddHHmm(Date date) {
		if (date == null) {
			return "";
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yy年M月d日HH:mm");
		return dateFormat.format(date);
	}

	/**
	 * 返回时间字符串, 可读形式的, yy年M月d日HH:mm 格式. 2004-10-04, 
	 * @return - String 格式化后的时间
	 */
	public static String formatDateToyyyyMMddHHmm2(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");

		return dateFormat.format(date);
	}

	/**
	 * 将日期转换为中文表示方式的字符串(格式为 yyyy年MM月dd日 HH:mm:ss).
	 */
	public static String dateToChineseString(Date date) {
		if (date == null) {
			return "";
		}

		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");

		return dateFormat.format(date);
	}

	/**
	 * 将日期转换为 14 位的字符串(格式为yyyyMMddHHmmss).
	 */
	public static String dateTo14String(Date date) {
		if (date == null) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		return dateFormat.format(date);
	}

	/**
	 * 将 14 位的字符串(格式为yyyyMMddHHmmss)转换为日期.
	 */
	public static Date string14ToDate(String input) {
		if (StringUtils.isEmpty(input)) {
			return null;
		}
		if (input.length() != 14) {
			return null;
		}
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		try {
			return dateFormat.parse(input);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	/**
	 * 判断map里面有没有指定的日期数据，有返回，没有返回null
	 */
	public static Date toDateFromMap(String string, Map<String, Object> map) {
		Date date = null;
		if (map.containsKey(string)) {
			date = new Date();
		}
		return date;
	}

	/**
	 * 将Sting类型格式化成Timestamp格式
	 *
	 * @param stringDate
	 *            string型的日期
	 */
	public static Timestamp fmtDate2Time(String stringDate) {
		if (stringDate != null) {
			DateFormat format = new SimpleDateFormat(TIME_PATTERN);
			try {
				Timestamp ts = new Timestamp(format.parse(stringDate).getTime());
				return (ts);
			} catch (ParseException e) {
				throw new RuntimeException(e);
			}
		} else {
			return null;
		}
	}

	/**
	 * 判断时间是否在时间段内
	 *
	 * @param date
	 *            当前时间 yyyy-MM-dd HH:mm:ss
	 * @param strDateBegin
	 *            开始时间 00:00:00
	 * @param strDateEnd
	 *            结束时间 00:05:00
	 */
	public static boolean isInDate(Date date, String strDateBegin, String strDateEnd) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String strDate = sdf.format(date);
		// 截取当前时间时分秒
		int strDateH = Integer.parseInt(strDate.substring(11, 13));
		int strDateM = Integer.parseInt(strDate.substring(14, 16));
		int strDateS = Integer.parseInt(strDate.substring(17, 19));
		// 截取开始时间时分秒
		int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
		int strDateBeginM = Integer.parseInt(strDateBegin.substring(3, 5));
		int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
		// 截取结束时间时分秒
		int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
		int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
		int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
		if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {
			// 当前时间小时数在开始时间和结束时间小时数之间
			if (strDateH > strDateBeginH && strDateH < strDateEndH) {
				return true;
				// 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM >= strDateBeginM && strDateM <= strDateEndM) {
				return true;
				// 当前时间小时数等于开始时间小时数，分钟数等于开始时间分钟数，秒数在开始和结束之间
			} else if (strDateH == strDateBeginH && strDateM == strDateBeginM && strDateS >= strDateBeginS
					&& strDateS <= strDateEndS) {
				return true;
			}
			// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
			else if (strDateH >= strDateBeginH && strDateH == strDateEndH && strDateM <= strDateEndM) {
				return true;
				// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
			} else if (strDateH >= strDateBeginH && strDateH == strDateEndH && strDateM == strDateEndM
					&& strDateS <= strDateEndS) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 判断时间是否 在指定时间之前
	 */
	public static boolean isInEndDate(Date startDate, Date endDate) {
		Calendar start = Calendar.getInstance();
		Calendar end = Calendar.getInstance();
		start.setTime(startDate);
		end.setTime(endDate);
		int result = start.compareTo(end);
		if (result == 0)
			return false;
		// System.out.println("c1相等c2");
		else if (result < 0)
			return true;
		// System.out.println("c1小于c2");
		else
			return false;
		// System.out.println("c1大于c2");
	}

	/**
	 * 计算两时间之前的天数
	 */
	public static int countDays(Date startDate, Date endDate) {
		long start = endDate.getTime() - startDate.getTime();
		long m = start / (24 * 60 * 60 * 1000);
		return Integer.parseInt(String.valueOf(m));
	}

	/**
	 * 判断时间是否为当前时间 根据不同条件显示时分秒 年月日
	 */
	public static String getFomatFromDateToResultDate(String time) {
		String result = "";
		if (time == null || "".equals(time)) {
			return result;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar current = Calendar.getInstance();// 实例化一个日历对象
		// 当前年月日
		Integer currentyear = current.get(Calendar.YEAR);
		Integer currentmonth = current.get(Calendar.MONTH) + 1;
		Integer currentday = current.get(Calendar.DAY_OF_MONTH);// x,y,z分别代表当前年，月，日

		// 组装参数
		Date date;
		try {
			date = sdf.parse(time);
			Calendar from = Calendar.getInstance();// 实例化一个参数对象
			from.setTime(date);
			Integer fromYear = from.get(Calendar.YEAR);
			Integer fromMonth = from.get(Calendar.MONTH) + 1;
			Integer fromDay = from.get(Calendar.DAY_OF_MONTH);// x,y,z分别代表当前年，月，日

			// 判断条件
			Integer flagyear = currentyear - fromYear;
			Integer flagmonth = currentmonth - fromMonth;
			Integer flagday = currentday - fromDay;
			if (flagyear == 0) {// 当前年
				if (flagmonth == 0 && flagday == 0) {// 当天
					sdf = new SimpleDateFormat("HH:mm");
					result = sdf.format(date);// 返回 15:20
				} else { // 非当天
					sdf = new SimpleDateFormat("MM-dd HH:mm");
					result = sdf.format(date);// 返回 09-03 15:20
				}
			} else if (flagyear > 0) {// 小于当前年(过去)
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				result = sdf.format(date);// 返回 09-03 15:20
			} else if (flagyear < 0) {// 未来
				sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				result = sdf.format(date);// 原值返回
			}
		} catch (ParseException e) {
			result = "";
		}
		return result;
	}
}
