package com.frank.search.util;

import org.apache.commons.lang.StringUtils;

import java.sql.Timestamp;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * <p>
 * 公共方法类
 * </p>
 * <p>
 * 提供有关日期的实用方法集
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * </p>
 * <p>
 * Company: NineTowns
 * </p>
 * 
 * @author admin
 * @version 1.0
 *
 */

public class DateUtil {
	/**
	 * 时 分 HH:mm
	 */
	public static String FORMAT_PATTERN_SECOND = "HH:mm";

	/**
	 * 年月日时分秒 yyyy-MM-dd HH:mm:ss
	 */
	public static String FORMAT_PATTERN_ANNUAL = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 年月日 yyyy-MM-dd
	 */
	public static String FORMAT_PATTERN_DATE = "yyyy-MM-dd";

	/**
	 * 年月日时分 yyyy-MM-dd HH:mm
	 */
	public static String FORMAT_PATTERN_HOURMINUTE = "yyyy-MM-dd HH:mm";

	/**
	 * solr 时间yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
	 */
	public static String FORMAT_PATTERN_SOLR = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

	/**
	 * solr 时间HH:mm:ss.SSS
	 */
	public static String FORMAT_PATTERN_SOLR_TIME = "HH:mm:ss";

	/**
	 * solr 时间HH
	 */
	public static String FORMAT_PATTERN_SOLR_HOUR = "HH";

	/**
	 * solr 时间mm
	 */
	public static String FORMAT_PATTERN_SOLR_MINUTE = "mm";

	/**
	 * solr 时间ss
	 */
	public static String FORMAT_PATTERN_SOLR_SECOND = "ss";

	static SimpleDateFormat sdfShort = new SimpleDateFormat("yyyyMMdd");
	static SimpleDateFormat sdfLong = new SimpleDateFormat("yyyy-MM-dd");
	static SimpleDateFormat sdfLongCn = new SimpleDateFormat("yyyy年MM月dd日");
	static SimpleDateFormat sdfShortU = new SimpleDateFormat("MMM dd", Locale.ENGLISH);
	static SimpleDateFormat sdfLongU = new SimpleDateFormat("MMM dd,yyyy", Locale.ENGLISH);
	static SimpleDateFormat sdfLongTime = new SimpleDateFormat("yyyyMMddHHmmss");
	static SimpleDateFormat sdfLongTimePlus = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	static SimpleDateFormat sdfShortLongTimePlusCn = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
	static SimpleDateFormat sdfLongTimePlusMill = new SimpleDateFormat("yyyyMMddHHmmssSSSS");
	static SimpleDateFormat sdfMd = new SimpleDateFormat("MM月dd日");

	private static long DAY_IN_MILLISECOND = 0x5265c00L;

	public DateUtil() {
	}

	/**
	 * 格式化出时间
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static Date parseStringToDate(String date, String pattern) {
		try {
			if (StringUtils.isBlank(pattern)) {
				pattern = FORMAT_PATTERN_DATE;
			}
			if (StringUtils.isBlank(date)) {
				return Calendar.getInstance().getTime();
			}
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		return null;
	}

	/**
	 * 格式化出时间
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String parseStringToString(String date, String pattern) {
		try {
			if (StringUtils.isBlank(pattern)) {
				pattern = FORMAT_PATTERN_DATE;
			}

			if (StringUtils.isBlank(date)) {
				return null;
			}
			SimpleDateFormat sdf = new SimpleDateFormat(pattern);
			return sdf.format(sdf.parse(date));
		} catch (ParseException e) {
			e.printStackTrace(); // To change body of catch statement use File |
									// Settings | File Templates.
		}
		return null;
	}

	/**
	 * @author admin Descrption:ȡ�õ�ǰ����getgetg get Date format
	 *         Example：2008-05-15
	 * @return String
	 * @throws Exception
	 */
	public static String getDateLong(Date date) {
		String nowDate = "";
		try {
			if (date != null)
				nowDate = sdfLong.format(date);
			return nowDate;
		} catch (Exception e) {
			System.out.println("Error at getDate:" + e.getMessage());
			return "";
		}
	}

	/**
	 * @author admin Descrption:ȡ�õ�ǰ����getgetg get Date format
	 *         Example：2008年-05月-15日
	 * @return String
	 * @throws Exception
	 */
	public static String getDateLongCn(Date date) {
		String nowDate = "";
		try {
			if (date != null)
				nowDate = sdfLongCn.format(date);
			return nowDate;
		} catch (Exception e) {
			System.out.println("Error at getDate:" + e.getMessage());
			return "";
		}
	}

	/**
	 * @author admin Descrption:ȡ�õ�ǰ����getgetg get Date format Example：05月-15日
	 * @return String
	 * @throws Exception
	 */
	public static String getDateMD(Date date) {
		String nowDate = "";
		try {
			if (date != null)
				nowDate = sdfMd.format(date);
			return nowDate;
		} catch (Exception e) {
			System.out.println("Error at getDate:" + e.getMessage());
			return "";
		}
	}

	/**
	 * @author admin Descrption:ȡ�õ�ǰ����getgetg get Date format
	 *         Example：2008年-05月-15日 11:05
	 * @return String
	 * @throws Exception
	 */
	public static String getDateShortLongTimeCn(Date date) {
		String nowDate = "";
		try {
			if (date != null)
				nowDate = sdfShortLongTimePlusCn.format(date);
			return nowDate;
		} catch (Exception e) {
			System.out.println("Error at getDate:" + e.getMessage());
			return "";
		}
	}

	/**
	 * @author admin Descrption:ȡ�õ�ǰ����getgetg get Date format Example：Aug 28,
	 *         2007
	 * @return String
	 * @throws Exception
	 */
	public static String getDateUS(Date date) {
		String nowDate = "";
		try {
			if (date != null)
				nowDate = sdfLongU.format(date);
			return nowDate;
		} catch (Exception e) {
			System.out.println("Error at getDate:" + e.getMessage());
			return "";
		}
	}

	/**
	 * @author admin Descrption:ȡ�õ�ǰ����getgetg get Date format Example：Aug 28,
	 *         2007
	 * @return String
	 * @throws Exception
	 */
	public static String getDateUSShort(Date date) {
		String nowDate = "";
		try {
			if (date != null)
				nowDate = sdfShortU.format(date);
			return nowDate;
		} catch (Exception e) {
			System.out.println("Error at getDate:" + e.getMessage());
			return "";
		}
	}

	/**
	 * 简单转换日期类型到字符串类型，本地信息设为UK
	 *
	 * @param date
	 * @param format
	 * @return String
	 */
	public static String getFomartDate(Date date, String format) {
		try {
			return new SimpleDateFormat(format, Locale.UK).format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return (date == null) ? new Date().toString() : date.toString();
		}
	}

	/**
	 * Descrption:取得当前日期时间,格式为:YYYYMMDDHHMISS
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String getNowLongTime() throws Exception {
		String nowTime = "";
		try {
			java.sql.Date date = null;
			date = new java.sql.Date(new Date().getTime());
			nowTime = sdfLongTime.format(date);
			return nowTime;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Descrption:取得当前日期,格式为:YYYYMMDD
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String getNowShortDate() throws Exception {
		String nowDate = "";
		try {
			java.sql.Date date = null;
			date = new java.sql.Date(new Date().getTime());
			nowDate = sdfShort.format(date);
			return nowDate;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Descrption:取得当前日期,格式为:YYYY-MM-DD
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String getNowFormateDate() throws Exception {
		String nowDate = "";
		try {
			java.sql.Date date = null;
			date = new java.sql.Date(new Date().getTime());
			nowDate = sdfLong.format(date);
			return nowDate;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Descrption:取得当前日期,格式为:yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String getNowPlusTime() throws Exception {
		String nowDate = "";
		try {
			java.sql.Date date = null;
			date = new java.sql.Date(new Date().getTime());
			nowDate = sdfLongTimePlus.format(date);
			return nowDate;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Descrption:取得当前日期,格式为:yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String getPlusTime(Date date) throws Exception {
		if (date == null)
			return null;
		try {
			String nowDate = sdfLongTimePlus.format(date);
			return nowDate;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Descrption:取得当前日期,格式为:yyyy-MM-dd HH:mm:ss
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String getPlusTime2(Date date) {

		if (date == null)
			return null;
		try {
			String nowDate = sdfLongTimePlus.format(date);
			return nowDate;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * Descrption:取得当前日期到毫秒极,格式为:yyyyMMddHHmmssSSSS
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String getNowPlusTimeMill() throws Exception {
		String nowDate = "";
		try {
			java.sql.Date date = null;
			date = new java.sql.Date(new Date().getTime());
			nowDate = sdfLongTimePlusMill.format(date);
			return nowDate;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到当前年份值:1900
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String getNowYear() throws Exception {
		String nowYear = "";
		try {
			String strTemp = getNowLongTime();
			nowYear = strTemp.substring(0, 4);
			return nowYear;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到当前月份值:12
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String getNowMonth() throws Exception {
		String nowMonth = "";
		try {
			String strTemp = getNowLongTime();
			nowMonth = strTemp.substring(4, 6);
			return nowMonth;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到当前日期值:30
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String getNowDay() throws Exception {
		String nowDay = "";
		try {
			String strTemp = getNowLongTime();
			nowDay = strTemp.substring(6, 8);
			return nowDay;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到当前小时值:23
	 * 
	 * @return String
	 * @throws Exception
	 */
	public static String getNowHour() throws Exception {
		String nowHour = "";
		try {
			String strTemp = getNowPlusTimeMill();
			nowHour = strTemp.substring(8, 10);
			return nowHour;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 根据秒数返回时分秒
	 * 
	 * @param _second
	 *            秒数
	 * @return String
	 * @throws Exception
	 */
	public static String getTimeBySecond(String _second) throws Exception {
		String returnTime = "";
		long longHour = 0;
		long longMinu = 0;
		long longSec = 0;
		try {
			longSec = Long.parseLong(_second);
			if (longSec == 0) {
				returnTime = "0时0分0秒";
				return returnTime;
			}
			longHour = longSec / 3600; // 取得小时数
			longSec = longSec % 3600; // 取得余下的秒
			longMinu = longSec / 60; // 取得分数
			longSec = longSec % 60; // 取得余下的秒
			returnTime = longHour + "时" + longMinu + "分" + longSec + "秒";
			return returnTime;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * pablo 根据毫秒数返回时分秒毫秒
	 * 
	 * @param ms_second
	 *            秒数
	 * @return String
	 * @throws Exception
	 */
	public static String getTimeBySecond(long ms_second) throws Exception {
		String returnTime = "";
		long longHour = 0;
		long longMinu = 0;
		long longSec = 0;
		long longMs = ms_second;
		try {
			if (longMs == 0) {
				returnTime = "0时0分0秒0毫秒";
				return returnTime;
			}
			longHour = longMs / 3600000; // 取得小时数
			longMs = longMs % 3600000; // 取得余下的毫秒
			longMinu = longMs / 60000; // 取得分数
			longMs = longMs % 60000; // 取得余下的毫秒
			longSec = longMs / 1000; // 取得余下的秒
			longMs = longMs % 1000; // 取得余下的毫秒
			returnTime = longHour + "时" + longMinu + "分" + longSec + "秒" + longMs + "毫秒";
			return returnTime;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 得到日期中的年份
	 *
	 * @param date
	 *            日期
	 * @return yyyy格式的年份
	 */
	public static int convertDateToYear(Date date) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy", new DateFormatSymbols());
		return Integer.parseInt(df.format(date));
	}

	/**
	 * 得到日期中年月组成的字符串
	 *
	 * @param d
	 *            日期
	 * @return yyyyMM格式的年月字符串
	 */
	public static String convertDateToYearMonth(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMM", new DateFormatSymbols());
		return df.format(d);
	}

	/**
	 * 得到日期中年月日组成的字符串
	 *
	 * @param d
	 *            日期
	 * @return yyyyMMdd格式的年月日字符串
	 */
	public static String convertDateToYearMonthDay(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd", new DateFormatSymbols());
		return df.format(d);
	}

	/**
	 * 得到日期中的月份
	 *
	 * @param d
	 *            日期
	 * @return yyyy格式的年份
	 */
	public static String convertDateToMonth(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("MM", new DateFormatSymbols());
		return df.format(d);
	}

	/**
	 * 得到日期中的日
	 *
	 * @param d
	 *            日期
	 * @return yyyy格式的年份
	 */
	public static String convertDateToDay(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("dd", new DateFormatSymbols());
		return df.format(d);
	}

	/**
	 * 得到日期中的小时
	 *
	 * @param d
	 *            日期
	 * @return HH格式的小时
	 */
	public static String convertDateToHour(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("HH", new DateFormatSymbols());
		return df.format(d);
	}

	/**
	 * 得到日期中的分钟
	 *
	 * @param d
	 *            日期
	 * @return mm格式的分钟
	 */
	public static String convertDateToMinute(Date d) {
		SimpleDateFormat df = new SimpleDateFormat("mm", new DateFormatSymbols());
		return df.format(d);
	}

	/**
	 * 获取当前日期为日期型
	 *
	 * @return 当前日期，java.util.Date类型
	 */
	public static Date getCurrentDate() {
		Calendar cal = Calendar.getInstance();

		// String currentDate = null;
		Date d = cal.getTime();

		return d;
	}

	/**
	 * 获取当前年月的字符串
	 *
	 * @return 当前年月，yyyyMM格式
	 */
	public static String getCurrentYearMonth() {
		Calendar cal = Calendar.getInstance();
		String currentYear = (new Integer(cal.get(Calendar.YEAR))).toString();
		String currentMonth = null;
		if (cal.get(Calendar.MONTH) < 9)
			currentMonth = "0" + (new Integer(cal.get(Calendar.MONTH) + 1)).toString();
		else
			currentMonth = (new Integer(cal.get(Calendar.MONTH) + 1)).toString();
		return (currentYear + currentMonth);
	}

	/**
	 * 获取当前年为整型
	 *
	 * @return 获取当前日期中的年，int型
	 */
	public static int getCurrentYear() {
		Calendar cal = Calendar.getInstance();
		int currentYear = cal.get(Calendar.YEAR);
		return currentYear;
	}

	/**
	 * 新增的格式化时间类,将时间进行标准化格式,适用于将前台传入的日期格式化为实际可行的日期
	 * 如将20050600格式化为20050601,或将20050631格式化为20050630
	 * 
	 * @param _dateTime
	 *            传入的原时间串
	 * @param _format
	 *            格式符,YYYYMMDDHH24MISS,YYYYMMDDHH12MISS
	 * @return String
	 * @throws Exception
	 */
	public static String formatDateTime(String _dateTime, String _format) throws Exception {
		String returnValue = "";
		String formatString = _format.toUpperCase();
		String strYear = "";
		String strMonth = "";
		String strDay = "";
		String strHour = "";
		String strMinu = "";
		String strSec = "";
		int hourType = 12; // 12小时制,24小时制
		int yearType = 1; // 1为平年,2为闰年
		try {
			if (formatString.indexOf("YYYY") >= 0) {
				int tempBeginPlace = formatString.indexOf("YYYY");
				int temEndPlace = tempBeginPlace + 4;
				strYear = _dateTime.substring(tempBeginPlace, temEndPlace);
			}
			if (formatString.indexOf("MM") >= 0) {
				int tempBeginPlace = formatString.indexOf("MM");
				int temEndPlace = tempBeginPlace + 2;
				strMonth = _dateTime.substring(tempBeginPlace, temEndPlace);
			}
			if (formatString.indexOf("DD") >= 0) {
				int tempBeginPlace = formatString.indexOf("DD");
				int temEndPlace = tempBeginPlace + 2;
				strDay = _dateTime.substring(tempBeginPlace, temEndPlace);
			}
			if (formatString.indexOf("HH24") >= 0) {
				int tempBeginPlace = formatString.indexOf("HH24");
				int temEndPlace = tempBeginPlace + 2;
				strHour = _dateTime.substring(tempBeginPlace, temEndPlace);
				formatString = formatString.replaceAll("24", "");
				// 为了保持位数一致,去除24
				hourType = 24;
			} else if (formatString.indexOf("HH12") >= 0) {
				int tempBeginPlace = formatString.indexOf("HH12");
				int temEndPlace = tempBeginPlace + 2;
				strHour = _dateTime.substring(tempBeginPlace, temEndPlace);
				formatString = formatString.replaceAll("12", "");
				// 为了保持位数一致,去除12
				hourType = 12;
			} else if (formatString.indexOf("HH") >= 0) {
				int tempBeginPlace = formatString.indexOf("HH");
				int temEndPlace = tempBeginPlace + 2;
				strHour = _dateTime.substring(tempBeginPlace, temEndPlace);
				hourType = 12; // 如果未指定小时制,则默认为12小时制;
			}
			if (formatString.indexOf("MI") >= 0) {
				int tempBeginPlace = formatString.indexOf("MI");
				int temEndPlace = tempBeginPlace + 2;
				strMinu = _dateTime.substring(tempBeginPlace, temEndPlace);
			}
			if (formatString.indexOf("SS") >= 0) {
				int tempBeginPlace = formatString.indexOf("SS");
				int temEndPlace = tempBeginPlace + 2;
				strSec = _dateTime.substring(tempBeginPlace, temEndPlace);
			}

			// 判断是否是闰年
			if (!strYear.equals("")) {
				int intYear = Integer.parseInt(strYear);
				// 能被4整除，但不能被100整除② 能被4整除，且能被400
				if (intYear % 4 == 0) {
					if (intYear % 100 != 0) {
						yearType = 2;
					}
				}
				if (intYear % 4 == 0) {
					if (intYear % 400 == 0) {
						yearType = 2;
					}
				}
			}
			// 格式化月
			if (!strMonth.equals("")) {
				int intMonth = Integer.parseInt(strMonth);
				if (intMonth == 0) {
					strMonth = "01";
					intMonth = 1;
				}
				if (intMonth > 12) {
					strMonth = "12";
					intMonth = 12;
				}
			}

			// 格式化日
			if (!strDay.equals("")) {
				int intDay = Integer.parseInt(strDay);
				if (intDay == 0) {
					strDay = "01";
					intDay = 1;
				}
				if (intDay > 31) {
					strDay = "31";
					intDay = 31;
				}
				if ((strMonth.equals("01")) || (strMonth.equals("03")) || (strMonth.equals("05"))
						|| (strMonth.equals("07")) || (strMonth.equals("08")) || (strMonth.equals("10"))
						|| (strMonth.equals("12"))) {
					if (intDay > 31) {
						strDay = "31";
						intDay = 31;
					}
				}
				if ((strMonth.equals("02")) || (strMonth.equals("04")) || (strMonth.equals("06"))
						|| (strMonth.equals("09")) || (strMonth.equals("11"))) {
					if (intDay > 30) {
						strDay = "30";
						intDay = 30;
					}
					if (strMonth.equals("02")) { // 对2月的特别处理
						if (yearType == 2) {
							if (intDay > 29) {
								strDay = "29";
								intDay = 29;
							}
						} else {
							if (intDay > 28) {
								strDay = "28";
								intDay = 28;
							}
						}
					}
				}

				// 格式化小时
				if (!strHour.equals("")) {
					int intHour = Integer.parseInt(strHour);
					if (intHour > 24) {
						strHour = "24";
						intHour = 24;
					}
					if (hourType == 12) {
						if (intHour == 0) {
							intHour = 1;
							strHour = "01";
						}
						if (intHour > 12) {
							intHour = intHour - 12;
							strHour = "0" + intHour;
						}
					} else {
						if (intHour > 23) {
							intHour = 23;
							strHour = "23";
						}
					}
				}
				// 格式化分
				if (!strMinu.equals("")) {
					int intMinu = Integer.parseInt(strMinu);
					if (intMinu > 59) {
						strMinu = "59";
						intMinu = 59;
					}
				}
				// 格式化秒
				if (!strSec.equals("")) {
					int intSec = Integer.parseInt(strSec);
					if (intSec > 59) {
						strSec = "59";
						intSec = 59;
					}
				}
			}
			returnValue = strYear + strMonth + strDay + strHour + strMinu + strSec;
			return returnValue;
		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * 将指定格式的字符串转换为日期型
	 *
	 * @param strDate
	 *            - 日期
	 * @param oracleFormat
	 *            --oracle型日期格式
	 * @return 转换得到的日期
	 */
	public static Date stringToDate(String strDate, String oracleFormat) {
		if (strDate == null)
			return null;
		Hashtable h = new Hashtable();
		String javaFormat = new String();
		String s = oracleFormat.toLowerCase();
		if (s.indexOf("yyyy") != -1)
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		else if (s.indexOf("yy") != -1)
			h.put(new Integer(s.indexOf("yy")), "yy");
		if (s.indexOf("mm") != -1)
			h.put(new Integer(s.indexOf("mm")), "MM");

		if (s.indexOf("dd") != -1)
			h.put(new Integer(s.indexOf("dd")), "dd");
		if (s.indexOf("hh24") != -1)
			h.put(new Integer(s.indexOf("hh24")), "HH");
		if (s.indexOf("mi") != -1)
			h.put(new Integer(s.indexOf("mi")), "mm");
		if (s.indexOf("ss") != -1)
			h.put(new Integer(s.indexOf("ss")), "ss");

		int intStart = 0;
		while (s.indexOf("-", intStart) != -1) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf("/", intStart) != -1) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(" ", intStart) != -1) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(":", intStart) != -1) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
			intStart++;
		}

		if (s.indexOf("年") != -1)
			h.put(new Integer(s.indexOf("年")), "年");
		if (s.indexOf("月") != -1)
			h.put(new Integer(s.indexOf("月")), "月");
		if (s.indexOf("日") != -1)
			h.put(new Integer(s.indexOf("日")), "日");
		if (s.indexOf("时") != -1)
			h.put(new Integer(s.indexOf("时")), "时");
		if (s.indexOf("分") != -1)
			h.put(new Integer(s.indexOf("分")), "分");
		if (s.indexOf("秒") != -1)
			h.put(new Integer(s.indexOf("秒")), "秒");

		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n)
					n = i;
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));

			javaFormat = temp + javaFormat;
		}
		SimpleDateFormat df = new SimpleDateFormat(javaFormat);

		Date myDate = new Date();
		try {
			myDate = df.parse(strDate);
		} catch (Exception e) {
			// e.printStackTrace();
			return null;
		}

		return myDate;
	}

	/**
	 * 获取输入格式的日期字符串，字符串遵循Oracle格式
	 *
	 * @param d
	 *            - 日期
	 * @param format
	 *            - 指定日期格式，格式的写法为Oracle格式
	 * @return 按指定的日期格式转换后的日期字符串
	 */
	public static String dateToString(Date d, String format) {
		if (d == null)
			return "";
		Hashtable h = new Hashtable();
		String javaFormat = new String();
		String s = format.toLowerCase();
		if (s.indexOf("yyyy") != -1)
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		else if (s.indexOf("yy") != -1)
			h.put(new Integer(s.indexOf("yy")), "yy");
		if (s.indexOf("mm") != -1)
			h.put(new Integer(s.indexOf("mm")), "MM");

		if (s.indexOf("dd") != -1)
			h.put(new Integer(s.indexOf("dd")), "dd");
		if (s.indexOf("hh24") != -1)
			h.put(new Integer(s.indexOf("hh24")), "HH");
		if (s.indexOf("mi") != -1)
			h.put(new Integer(s.indexOf("mi")), "mm");
		if (s.indexOf("ss") != -1)
			h.put(new Integer(s.indexOf("ss")), "ss");

		int intStart = 0;
		while (s.indexOf("-", intStart) != -1) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf("/", intStart) != -1) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(" ", intStart) != -1) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(":", intStart) != -1) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
			intStart++;
		}

		if (s.indexOf("年") != -1)
			h.put(new Integer(s.indexOf("年")), "年");
		if (s.indexOf("月") != -1)
			h.put(new Integer(s.indexOf("月")), "月");
		if (s.indexOf("日") != -1)
			h.put(new Integer(s.indexOf("日")), "日");
		if (s.indexOf("时") != -1)
			h.put(new Integer(s.indexOf("时")), "时");
		if (s.indexOf("分") != -1)
			h.put(new Integer(s.indexOf("分")), "分");
		if (s.indexOf("秒") != -1)
			h.put(new Integer(s.indexOf("秒")), "秒");

		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n)
					n = i;
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));

			javaFormat = temp + javaFormat;
		}
		SimpleDateFormat df = new SimpleDateFormat(javaFormat, new DateFormatSymbols());

		return df.format(d);
	}

	/**
	 * 获取输入格式的日期字符串，字符串遵循Oracle格式
	 *
	 * @param d
	 *            - 日期
	 * @param format
	 *            - 指定日期格式，格式的写法为Oracle格式
	 * @return 按指定的日期格式转换后的日期字符串
	 */
	public static String getDate(Date d, String format) {
		if (d == null)
			return "";
		Hashtable h = new Hashtable();
		String javaFormat = new String();
		String s = format.toLowerCase();
		if (s.indexOf("yyyy") != -1)
			h.put(new Integer(s.indexOf("yyyy")), "yyyy");
		else if (s.indexOf("yy") != -1)
			h.put(new Integer(s.indexOf("yy")), "yy");
		if (s.indexOf("mm") != -1)
			h.put(new Integer(s.indexOf("mm")), "MM");

		if (s.indexOf("dd") != -1)
			h.put(new Integer(s.indexOf("dd")), "dd");
		if (s.indexOf("hh24") != -1)
			h.put(new Integer(s.indexOf("hh24")), "HH");
		if (s.indexOf("mi") != -1)
			h.put(new Integer(s.indexOf("mi")), "mm");
		if (s.indexOf("ss") != -1)
			h.put(new Integer(s.indexOf("ss")), "ss");

		int intStart = 0;
		while (s.indexOf("-", intStart) != -1) {
			intStart = s.indexOf("-", intStart);
			h.put(new Integer(intStart), "-");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf("/", intStart) != -1) {
			intStart = s.indexOf("/", intStart);
			h.put(new Integer(intStart), "/");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(" ", intStart) != -1) {
			intStart = s.indexOf(" ", intStart);
			h.put(new Integer(intStart), " ");
			intStart++;
		}

		intStart = 0;
		while (s.indexOf(":", intStart) != -1) {
			intStart = s.indexOf(":", intStart);
			h.put(new Integer(intStart), ":");
			intStart++;
		}

		if (s.indexOf("年") != -1)
			h.put(new Integer(s.indexOf("年")), "年");
		if (s.indexOf("月") != -1)
			h.put(new Integer(s.indexOf("月")), "月");
		if (s.indexOf("日") != -1)
			h.put(new Integer(s.indexOf("日")), "日");
		if (s.indexOf("时") != -1)
			h.put(new Integer(s.indexOf("时")), "时");
		if (s.indexOf("分") != -1)
			h.put(new Integer(s.indexOf("分")), "分");
		if (s.indexOf("秒") != -1)
			h.put(new Integer(s.indexOf("秒")), "秒");

		int i = 0;
		while (h.size() != 0) {
			Enumeration e = h.keys();
			int n = 0;
			while (e.hasMoreElements()) {
				i = ((Integer) e.nextElement()).intValue();
				if (i >= n)
					n = i;
			}
			String temp = (String) h.get(new Integer(n));
			h.remove(new Integer(n));

			javaFormat = temp + javaFormat;
		}
		SimpleDateFormat df = new SimpleDateFormat(javaFormat, new DateFormatSymbols());

		return df.format(d);
	}

	/**
	 * 根据身份证号码获取年龄
	 *
	 * @param id
	 *            身份证号
	 * @throws Exception
	 *             身份证号错误时发生
	 * @return int - 年龄
	 */
	public static int getAge(String id) throws Exception {
		int age = -1;
		int length = id.length();
		String birthday = "";
		if (length == 15) {
			birthday = id.substring(6, 8);
			birthday = "19" + birthday;
		} else if (length == 18) {
			birthday = id.substring(6, 10);
		} else {
			throw new Exception("错误的身份证号");
		}
		int currentYear = Calendar.getInstance().get(Calendar.YEAR);
		age = currentYear - (new Integer(birthday)).intValue();
		return age;
	}

	/**
	 * 根据年龄获取出生年份
	 *
	 * @param age
	 *            int 年龄
	 * @return Date - 出生年份
	 */
	public static java.sql.Date getDateByAge(int age) {
		Calendar calendar = Calendar.getInstance(Locale.CHINESE);
		long current = calendar.getTimeInMillis();
		calendar.set(calendar.get(Calendar.YEAR) - age, calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
		return new java.sql.Date((calendar.getTimeInMillis()));
	}

	/**
	 * 比较两个日期(年月型，格式为YYYYMM)之间相差月份
	 *
	 * @param dealMonth
	 *            - 开始年月
	 * @param alterMonth
	 *            - 结束年月
	 * @return alterMonth-dealMonth相差的月数
	 */
	public static int calBetweenTwoMonth(String dealMonth, String alterMonth) {
		int length = 0;
		if ((dealMonth.length() != 6) || (alterMonth.length() != 6)) {
			// 比较年月字符串的长度不正确
			length = -1;

		} else {
			int dealInt = Integer.parseInt(dealMonth);
			int alterInt = Integer.parseInt(alterMonth);
			if (dealInt < alterInt) {
				// 第一个年月变量应大于或等于第二个年月变量
				length = -2;
			} else {
				int dealYearInt = Integer.parseInt(dealMonth.substring(0, 4));
				int dealMonthInt = Integer.parseInt(dealMonth.substring(4, 6));
				int alterYearInt = Integer.parseInt(alterMonth.substring(0, 4));
				int alterMonthInt = Integer.parseInt(alterMonth.substring(4, 6));
				length = (dealYearInt - alterYearInt) * 12 + (dealMonthInt - alterMonthInt);
			}
		}

		return length;
	}

	/**
	 * 得到两个日期之间相差的天数
	 *
	 * @param newDate
	 *            大的日期
	 * @param oldDate
	 *            小的日期
	 * @return newDate-oldDate相差的天数
	 */
	public static int daysBetweenDates(Date newDate, Date oldDate) {
		int days = 0;
		Calendar calo = Calendar.getInstance();
		Calendar caln = Calendar.getInstance();
		calo.setTime(oldDate);
		caln.setTime(newDate);
		int oday = calo.get(Calendar.DAY_OF_YEAR);
		int nyear = caln.get(Calendar.YEAR);
		int oyear = calo.get(Calendar.YEAR);
		while (nyear > oyear) {
			calo.set(Calendar.MONTH, 11);
			calo.set(Calendar.DATE, 31);
			days = days + calo.get(Calendar.DAY_OF_YEAR);
			oyear = oyear + 1;
			calo.set(Calendar.YEAR, oyear);
		}
		int nday = caln.get(Calendar.DAY_OF_YEAR);
		days = days + nday - oday;

		return days;
	}

	/**
	 * 取得与原日期相差一定天数的日期，返回Date型日期
	 *
	 * @param date
	 *            原日期
	 * @param intBetween
	 *            相差的天数
	 * @return date加上intBetween天后的日期
	 */
	public static Date getDateBetween(Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.DATE, intBetween);
		return calo.getTime();
	}

	/**
	 * 按指定格式取得与原日期相差一定天数的日期，返回String型日期
	 *
	 * @param date
	 *            原日期
	 * @param intBetween
	 *            相差的日期
	 * @param strFromat
	 *            返回日期的格式
	 * @return date加上intBetween天后的日期
	 */
	public static String getDateBetween_String(Date date, int intBetween, String strFromat) {
		Date dateOld = getDateBetween(date, intBetween);
		return getDate(dateOld, strFromat);
	}

	/**
	 * 得到将年月型字符串增加1月后的日期字符串
	 *
	 * @param yearMonth
	 *            yyyyMM格式
	 * @return yearMonth增加一个月后的日期，yyyyMM格式
	 */
	public static String increaseYearMonth(String yearMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		month = month + 1;
		if (month <= 12 && month >= 10)
			return yearMonth.substring(0, 4) + (new Integer(month)).toString();
		else if (month < 10)
			return yearMonth.substring(0, 4) + "0" + (new Integer(month)).toString();
		else
			// if(month>12)
			return (new Integer(year + 1)).toString() + "0" + (new Integer(month - 12)).toString();

	}

	/**
	 * 得到将年月型字符串增加指定月数后的日期字符串
	 *
	 * @param yearMonth
	 *            yyyyMM格式日期
	 * @param addMonth
	 *            增加指定月数
	 * @return yearMonth 增加addMonth个月后的日期，yyyyMM格式
	 */
	public static String increaseYearMonth(String yearMonth, int addMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		month = month + addMonth;
		year = year + month / 12;
		month = month % 12;
		if (month <= 12 && month >= 10)
			return year + (new Integer(month)).toString();
		else
			return year + "0" + (new Integer(month)).toString();

	}

	/**
	 * 得到将年月型字符串减去1月后的日期字符串
	 *
	 * @param yearMonth
	 *            - yyyyMM格式
	 * @return - yearMonth减少一个月的日期，yyyyMM格式
	 */
	public static String descreaseYearMonth(String yearMonth) {
		int year = (new Integer(yearMonth.substring(0, 4))).intValue();
		int month = (new Integer(yearMonth.substring(4, 6))).intValue();
		month = month - 1;
		if (month >= 10)
			return yearMonth.substring(0, 4) + (new Integer(month)).toString();
		else if (month > 0 && month < 10)
			return yearMonth.substring(0, 4) + "0" + (new Integer(month)).toString();
		else
			// if(month>12)
			return (new Integer(year - 1)).toString() + (new Integer(month + 12)).toString();

	}

	/**
	 * 比较两个年月型日期的大小，日期格式为yyyyMM 两个字串6位，前4代表年，后2代表月， <br>
	 * IF 第一个代表的时间 > 第二个代表的时间，返回真，ELSE 返回假 <br>
	 *
	 * @param s1
	 *            日期1
	 * @param s2
	 *            日期2
	 * @return boolean 如果s1大于等于s2则返回真，否则返回假
	 */
	public static boolean yearMonthGreatEqual(String s1, String s2) {
		String temp1 = s1.substring(0, 4);
		String temp2 = s2.substring(0, 4);
		String temp3 = s1.substring(4, 6);
		String temp4 = s2.substring(4, 6);

		if (Integer.parseInt(temp1) > Integer.parseInt(temp2))
			return true;
		else if (Integer.parseInt(temp1) == Integer.parseInt(temp2)) {
			if (Integer.parseInt(temp3) >= Integer.parseInt(temp4))
				return true;
			else
				return false;
		} else
			return false;
	}

	/**
	 * 比较两个年月型日期的大小，日期格式为yyyyMM 两个字串6位，前4代表年，后2代表月， <br>
	 * IF 第一个代表的时间 > 第二个代表的时间，返回真，ELSE 返回假 <br>
	 *
	 * @param s1
	 *            日期1
	 * @param s2
	 *            日期2
	 * @return boolean 如果s1大于s2则返回真，否则返回假
	 */
	public static boolean yearMonthGreater(String s1, String s2) {
		String temp1 = s1.substring(0, 4);
		String temp2 = s2.substring(0, 4);
		String temp3 = s1.substring(4, 6);
		String temp4 = s2.substring(4, 6);

		if (Integer.parseInt(temp1) > Integer.parseInt(temp2))
			return true;
		else if (Integer.parseInt(temp1) == Integer.parseInt(temp2)) {
			if (Integer.parseInt(temp3) > Integer.parseInt(temp4))
				return true;
			else
				return false;
		} else
			return false;
	}

	/**
	 * 将日期型数据转换成Oracle要求的标准格式的字符串
	 * 
	 * @param date
	 *            日期
	 * @return 格式化后的字符串
	 */
	public static String getOracleFormatDateStr(Date date) {
		return getDate(date, "YYYY-MM-DD HH24:MI:SS");
	}

	/**
	 * 字串6位，前4代表年，后2代表月， 返回给定日期中的月份中的最后一天 param term(YYYYMMDD)
	 *
	 * @param term
	 *            - 年月，格式为yyyyMM
	 * @return String 指定年月中该月份的天数
	 */
	public static String getLastDay(String term) {

		int getYear = Integer.parseInt(term.substring(0, 4));
		int getMonth = Integer.parseInt(term.substring(4, 6));

		String getLastDay = "";

		if (getMonth == 2) {
			if (getYear % 4 == 0 && getYear % 100 != 0 || getYear % 400 == 0) {
				getLastDay = "29";
			} else {
				getLastDay = "28";
			}
		} else if (getMonth == 4 || getMonth == 6 || getMonth == 9 || getMonth == 11) {
			getLastDay = "30";
		} else {
			getLastDay = "31";
		}
		return String.valueOf(getYear) + "年" + String.valueOf(getMonth) + "月" + getLastDay + "日";
	}

	/**
	 * 返回两个年月(例如：200206)之间相差的月数，年月格式为yyyyMM
	 *
	 * @param strDateBegin
	 *            - String
	 * @param strDateEnd
	 *            String
	 * @return String strDateEnd-strDateBegin相差的月数
	 */
	public static String getMonthBetween(String strDateBegin, String strDateEnd) {
		try {
			int intMonthBegin;
			int intMonthEnd;
			String strOut;
			if (strDateBegin.equals("") || strDateEnd.equals("") || strDateBegin.length() != 6
					|| strDateEnd.length() != 6)
				strOut = "";
			else {
				intMonthBegin = Integer.parseInt(strDateBegin.substring(0, 4)) * 12
						+ Integer.parseInt(strDateBegin.substring(4, 6));
				intMonthEnd = Integer.parseInt(strDateEnd.substring(0, 4)) * 12
						+ Integer.parseInt(strDateEnd.substring(4, 6));
				strOut = String.valueOf(intMonthBegin - intMonthEnd);
			}
			return strOut;
		} catch (Exception e) {
			return "0";
		}
	}

	/**
	 * 将yyyyMMDD格式的日期转换为yyyy-MM-DD格式的日期 返回带'-'的日期(例如：20020612 转换为 2002-06-12)
	 *
	 * @param strDate
	 *            String yyyyMMDD格式的日期
	 * @return String - yyyy-MM-DD格式的日期
	 */
	public static String getStrHaveAcross(String strDate) {
		try {
			return strDate.substring(0, 4) + "-" + strDate.substring(4, 6) + "-" + strDate.substring(6, 8);
		} catch (Exception e) {
			return strDate;
		}
	}

	/**
	 * 取得当前日期的下一个月的第一天 add by yaojp 2002-10-08
	 *
	 * @return 当前日期的下个月的第一天，格式为yyyyMMDD
	 */
	public static String getFirstDayOfNextMonth() {
		try {
			return increaseYearMonth(getNowShortDate().substring(0, 6)) + "01";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 取得当前日期的下一个月的第一天 add by zhouning 2006-09-13
	 *
	 * @return 当前日期的下个月的第一天，格式为yyyyMMDD
	 */
	public static String getFirstDayOfThisMonth() {
		try {
			return getNowShortDate().substring(0, 6) + "01";
		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 将yyyyMM各式转换成yyyy年MM月格式
	 * 
	 * @param yearMonth
	 *            年月类型的字符串
	 * @return String
	 */
	public static String getYearAndMonth(String yearMonth) {
		if (null == yearMonth)
			return "";
		String ym = yearMonth.trim();
		if (6 != ym.length())
			return ym;
		String year = ym.substring(0, 4);
		String month = ym.substring(4);
		return new StringBuffer(year).append("年").append(month).append("月").toString();
	}

	/**
	 * 将输入的Integer类型的月数转化成"X年X月"格式的字符串
	 * 
	 * @param month
	 *            Integer
	 * @return String
	 */
	public static String month2YearMonth(String month) {
		String yearMonth = "";
		int smonth = 0;
		int year = 0;
		int rmonth = 0;

		if ((null == month) || ("0".equals(month)) || "".equals(month.trim())) {
			return "0月";
		}

		smonth = Integer.parseInt(month);
		year = smonth / 12;
		rmonth = smonth % 12;

		if (year > 0) {
			yearMonth = year + "年";
		}
		if (rmonth > 0) {
			yearMonth += rmonth + "个月";
		}

		return yearMonth;
	}

	/**
	 * 将yyyyMM各式转换成yyyy年MM月格式
	 * 
	 * @param month
	 *            月
	 * @return 返回年月型格式的日期
	 */
	public static String getYearMonthByMonth(String month) {
		if (null == month)
			return null;
		String ym = month.trim();
		if (6 != ym.length())
			return ym;
		String year = ym.substring(0, 4);
		String month1 = ym.substring(4);
		return new StringBuffer(year).append("年").append(month).append("月").toString();
	}

	/**
	 * 得到将date增加指定月数后的date
	 *
	 * @param date
	 *            日期
	 * @param intBetween
	 *            增加的月份
	 * @return date 加上intBetween月数后的日期
	 */
	public static Date increaseMonth(Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.MONTH, intBetween);
		return calo.getTime();
	}

	/**
	 * 得到将date增加指定天数后的date
	 *
	 * @param date
	 *            日期
	 * @param intBetween
	 *            增加的天数
	 * @return date 加上intBetween天数后的日期
	 */
	public static Date increaseDay(Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.DATE, intBetween);
		return calo.getTime();
	}

	/**
	 * 得到将date增加指定年数后的date
	 * 
	 * @param date
	 *            日期
	 * @param intBetween
	 *            增加的年数
	 * @return date加上intBetween年数后的日期
	 */
	public static Date increaseYear(Date date, int intBetween) {
		Calendar calo = Calendar.getInstance();
		calo.setTime(date);
		calo.add(Calendar.YEAR, intBetween);
		return calo.getTime();
	}

	/**
	 * 比较两个时间先后
	 * 
	 * @param str1
	 *            传入的字符串
	 * @param str2
	 *            传入的字符串
	 * @return int negative integer, zero, or a positive integer as str1 is less
	 *         than, equal to, or greater than str2
	 */
	public static int compareDate(String str1, String str2) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date1 = null, date2 = null;
		try {
			date1 = formatter.parse(str1);
			date2 = formatter.parse(str2);
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date1.compareTo(date2);
	}

	public static int compareDate(String str1, Date date2) {
		Date date1 = getDateByString(str1);
		return date1.compareTo(date2);
	}

	public static int compareDate(String format, String str1, Date date2) {

		Date date1 = null;
		try {
			date1 = fromStringToDate(format, str1);
		} catch (ParseException e) {
			// TODO 自动生成 catch 块
			e.printStackTrace();
		}
		return date1.compareTo(date2);
	}

	/**
	 * 根据传入的日期字符串转换成相应的日期对象，如果字符串为空或不符合日期格 式，则返回当前时间。
	 * 
	 * @param strDate
	 *            String 日期字符串
	 * @return java.sql.Timestamp 日期对象
	 */
	public static Timestamp getDateByString(String strDate) {
		if (strDate.trim().equals("")) {
			return new Timestamp(System.currentTimeMillis());
		}
		try {
			strDate = getFormattedDate(strDate, "yyyy-MM-dd HH:mm:ss") + ".000000000";
			return Timestamp.valueOf(strDate);
		} catch (Exception ex) {
			return new Timestamp(System.currentTimeMillis());
		}
	}

	public static Timestamp getNextMonyDate(String strDate) {
		try {
			int iYear = StringUtil.getStrToInt(getFormattedDate(strDate, "yyyy"));
			int iMonth = StringUtil.getStrToInt(getFormattedDate(strDate, "MM"));
			if (iMonth == 12) {
				iYear = iYear + 1;
			} else {
				iMonth = iMonth + 1;
			}
			String strMonth = Integer.toString(iMonth);
			if (strMonth.length() == 1) {
				strMonth = "0" + strMonth;
			}
			strDate = Integer.toString(iYear) + "/" + strMonth + "/01";
			return getDateByString(strDate);
		} catch (Exception ex) {
			return getDateByString(strDate);
		}
	}

	// /**
	// * 根据参数名称，从request对象中取出该参数，并把该参数转换成GB2312编码的字符集。
	// * @param request 请求对象
	// * @param strParamName 参数名称
	// * @return java.sql.Date 转换后的参数值
	// * */
	// public static java.sql.Timestamp getDateFromReqParam(HttpServletRequest
	// request, String strParamName)
	// {
	// String strStr =
	// StringUtil.getNotNullStr(request.getParameter(strParamName));
	// return getDateByString(strStr);
	// }
	/**
	 * 得到当前日期，格式yyyy-MM-dd。
	 * 
	 * @return String 格式化的日期字符串
	 */
	public static String getCurrDate() {
		return getFormattedDate(getDateByString(""));
	}

	/**
	 * 得到当前日期，格式yyyy-MM-dd。
	 * 
	 * @return String 格式化的日期字符串
	 */
	public static String getToday() {
		Date cDate = new Date();
		SimpleDateFormat cSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return cSimpleDateFormat.format(cDate);
	}

	/**
	 * 得到当前日期，格式yyyy-MM-dd。
	 * 
	 * @return String 格式化的日期字符串
	 */
	public static String getYesterday() {
		Date cDate = new Date();
		cDate.setTime(cDate.getTime() - 24 * 3600 * 1000);
		SimpleDateFormat cSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return cSimpleDateFormat.format(cDate);
	}

	/**
	 * 得到当前日期，格式yyyy-MM-dd。
	 * 
	 * @return String 格式化的日期字符串
	 */
	public static String getTomorrow() {
		Date cDate = new Date();
		cDate.setTime(cDate.getTime() + 24 * 3600 * 1000);
		SimpleDateFormat cSimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return cSimpleDateFormat.format(cDate);
	}

	/**
	 * 返回默认的功能生效的时间，1900/01/01。
	 * 
	 * @return String 默认的实效时间字符串
	 */
	public static String getDefaultValidDate() {
		return "1900-01-01";
	}

	/**
	 * 返回默认的功能失效的时间，2099/12/31。
	 * 
	 * @return String 默认的实效时间字符串
	 */
	public static String getDefaultExpireDate() {
		return "2099-12-31";
	}

	/**
	 * 得到当前日期时间,格式为yyyy-MM-dd hh:mm:ss.
	 * 
	 * @return String
	 */
	public static String getCurrDateTime() {
		Timestamp date = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return formatter.format(date);
	}

	public static String getCurrDateTime(Date date, String fotmat) {
		SimpleDateFormat format = new SimpleDateFormat(fotmat);
		return format.format(date);
	}

	/**
	 * 得到指定的日期，如一年三个月零九天后(以yyyy/MM/dd格式显示)参数为("yyyy/MM/dd",1,3,9)
	 * 
	 * @param strFormat
	 *            strFormat
	 * @param iYear
	 *            iYear
	 * @param iMonth
	 *            iMonth
	 * @param iDate
	 *            iDate
	 * @return String
	 */
	public static String getSpecDate(String strFormat, int iYear, int iMonth, int iDate) {
		Calendar rightNow = Calendar.getInstance();
		rightNow.set(Calendar.YEAR, rightNow.get(Calendar.YEAR) + iYear);
		rightNow.set(Calendar.MONTH, rightNow.get(Calendar.MONTH) + iMonth);
		rightNow.set(Calendar.DATE, rightNow.get(Calendar.DATE) + iDate);
		SimpleDateFormat df = new SimpleDateFormat(strFormat);
		return df.format(rightNow.getTime());
	}

	/**
	 * 对输入的日期字符串进行默认的格式yyyy-MM-dd HH:mm:ss转换。
	 * 
	 * @param strDate
	 *            String 需要进行格式化的日期字符串
	 * @return String 经过格式化后的字符串
	 */
	public static String getDefaultFormattedDate(String strDate) {
		return getFormattedDate(strDate, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 对输入的日期进行默认的格式yyyy-MM-dd HH:mm:ss转换。
	 * 
	 * @param dtDate
	 *            需要进行格式化的日期
	 * @return String 经过格式化后的字符串
	 */
	public static String getDefaultFormattedDate(Timestamp dtDate) {
		return getFormattedDate(dtDate, "yyyy-MM-dd HH:mm:ss");
	}

	public static Timestamp getNullBirthDay() {
		return new Timestamp(0);
	}

	/**
	 * 对输入的日期字符串按照默认的格式yyyy-MM-dd转换.
	 * 
	 * @param strDate
	 *            String 需要进行格式化的日期字符串
	 * @return String 经过格式化后的字符串
	 */
	public static String getFormattedDate(String strDate) {
		return getFormattedDate(strDate, "yyyy-MM-dd");
	}

	/**
	 * 对输入的日期字符串进行格式化,如果输入的是0000/00/00 00:00:00则返回空串.
	 * 
	 * @param strDate
	 *            String 需要进行格式化的日期字符串
	 * @param strFormatTo
	 *            String 要转换的日期格式
	 * @return String 经过格式化后的字符串
	 */
	public static String getFormattedDate(String strDate, String strFormatTo) {
		if (strDate == null || strDate.trim().equals("")) {
			return "";
		}
		strDate = strDate.replace('/', '-');
		strFormatTo = strFormatTo.replace('/', '-');
		if (strDate.equals("0000-00-00 00:00:00") || strDate.equals("1800-01-01 00:00:00")) {
			return "";
		}
		String formatStr = strFormatTo; // "yyyyMMdd";
		if (strDate == null || strDate.trim().equals("")) {
			return "";
		}
		switch (strDate.trim().length()) {
		case 6:
			if (strDate.substring(0, 1).equals("0")) {
				formatStr = "yyMMdd";
			} else {
				formatStr = "yyyyMM";
			}
			break;
		case 8:
			formatStr = "yyyyMMdd";
			break;
		case 10:
			if (strDate.indexOf("-") == -1) {
				formatStr = "yyyy/MM/dd";
			} else {
				formatStr = "yyyy-MM-dd";
			}
			break;
		case 11:
			if (strDate.getBytes().length == 14) {
				formatStr = "yyyy年MM月dd日";
			} else {
				return "";
			}
		case 14:
			formatStr = "yyyyMMddHHmmss";
			break;
		case 19:
			if (strDate.indexOf("-") == -1) {
				formatStr = "yyyy/MM/dd HH:mm:ss";
			} else {
				formatStr = "yyyy-MM-dd HH:mm:ss";
			}
			break;
		case 21:
			if (strDate.indexOf("-") == -1) {
				formatStr = "yyyy/MM/dd HH:mm:ss.S";
			} else {
				formatStr = "yyyy-MM-dd HH:mm:ss.S";
			}
			break;
		default:
			return strDate.trim();
		}
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(formatStr);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(formatter.parse(strDate));
			formatter = new SimpleDateFormat(strFormatTo);
			return formatter.format(calendar.getTime());
		} catch (Exception e) {
			// Common.printLog("转换日期字符串格式时出错;" + e.getMessage());
			return "";
		}
	}

	/**
	 * 对输入的日期按照默认的格式yyyy-MM-dd转换.
	 * 
	 * @param dtDate
	 *            需要进行格式化的日期字符串
	 * @return String 经过格式化后的字符串
	 */
	public static String getFormattedDate(Timestamp dtDate) {
		return getFormattedDate(dtDate, "yyyy-MM-dd");
	}

	/**
	 * 对输入的日期进行格式化, 如果输入的日期是null则返回空串.
	 * 
	 * @param dtDate
	 *            java.sql.Timestamp 需要进行格式化的日期字符串
	 * @param strFormatTo
	 *            String 要转换的日期格式
	 * @return String 经过格式化后的字符串
	 */
	public static String getFormattedDate(Timestamp dtDate, String strFormatTo) {
		if (dtDate == null) {
			return "";
		}
		if (dtDate.equals(new Timestamp(0))) {
			return "";
		}
		strFormatTo = strFormatTo.replace('/', '-');
		try {
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy");
			if (Integer.parseInt(formatter.format(dtDate)) < 1900) {
				return "";
			} else {
				formatter = new SimpleDateFormat(strFormatTo);
				return formatter.format(dtDate);
			}
		} catch (Exception e) {
			// Common.printLog("转换日期字符串格式时出错;" + e.getMessage());
			return "";
		}
	}

	/**
	 * 把秒数转换成hh:mm:ss格式
	 * 
	 * @param lSecond
	 *            long
	 * @return String
	 */
	public static String getTimeFormat(long lSecond) {
		String szTime = new String();

		if (lSecond <= 0) {
			szTime = "00" + ":" + "00" + ":" + "00";
		} else {
			long hour = lSecond / 3600;
			long minute = (lSecond - hour * 3600) / 60;
			long second = (lSecond - hour * 3600 - minute * 60);

			if (hour <= 0) {
				szTime = "00";
			} else if (hour < 10) {
				szTime = "0" + String.valueOf(hour);
			} else {
				szTime = String.valueOf(hour);
			}
			szTime = szTime + ":";

			if (minute <= 0) {
				szTime = szTime + "00";
			} else if (minute < 10) {
				szTime = szTime + "0" + String.valueOf(minute);
			} else {
				szTime = szTime + String.valueOf(minute);
			}
			szTime = szTime + ":";

			if (second <= 0) {
				szTime = szTime + "00";
			} else if (second < 10) {
				szTime = szTime + "0" + String.valueOf(second);
			} else {
				szTime = szTime + String.valueOf(second);
			}
		}

		return szTime;
	}

	public static String getFormattedDateUtil(Date dtDate, String strFormatTo) {
		if (dtDate == null) {
			return "";
		}
		strFormatTo = strFormatTo.replace('/', '-');
		try {
			SimpleDateFormat formatter = new SimpleDateFormat(strFormatTo);
			return formatter.format(dtDate);
		} catch (Exception e) {
			// Common.printLog("转换日期字符串格式时出错;" + e.getMessage());
			return "";
		}
	}

	/**
	 * 得出两个日期之间的间隔天数
	 * 
	 * @param strFromDate
	 *            格式为yyyyMMdd
	 * @param strToDate
	 *            格式为yyyyMMdd
	 * @return int
	 */
	public static int getBetweenDays(String strFromDate, String strToDate) {
		try {
			Calendar clFrom = new GregorianCalendar();
			int iYear = Integer.parseInt(strFromDate.substring(0, 4));
			int iMonth = Integer.parseInt(strFromDate.substring(4, 6));
			int iDay = Integer.parseInt(strFromDate.substring(6, 8));
			clFrom.set(iYear, iMonth, iDay, 0, 0, 0);
			Calendar clTo = new GregorianCalendar();
			iYear = Integer.parseInt(strToDate.substring(0, 4));
			iMonth = Integer.parseInt(strToDate.substring(4, 6));
			iDay = Integer.parseInt(strToDate.substring(6, 8));
			clTo.set(iYear, iMonth, iDay, 0, 0, 0);
			long llTmp = clTo.getTime().getTime() - clFrom.getTime().getTime();
			return new Long(llTmp / 1000 / 3600 / 24).intValue();
		} catch (Exception e) {
			return Integer.MIN_VALUE;
		}
	}

	// 原DateUtil方法
	private static DateUtil instance = null;

	private static final Locale local = Locale.ENGLISH;

	public static synchronized DateUtil getInstance() {
		if (instance == null) {
			instance = new DateUtil();
		}
		return instance;
	}

	public static final long millisInDay = 86400000;

	// some static date formats
	private static SimpleDateFormat[] mDateFormats = loadDateFormats();

	private static final SimpleDateFormat mFormat8chars = new SimpleDateFormat("yyyyMMdd");

	private static final SimpleDateFormat mFormatIso8601Day = new SimpleDateFormat("yyyy-MM-dd");

	private static final SimpleDateFormat mFormatIso8601 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");

	// http://www.w3.org/Protocols/rfc822/Overview.html#z28
	private static final SimpleDateFormat mFormatRfc822 = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z");

	private static final SimpleDateFormat mFormatTradeEasy = new SimpleDateFormat("MM/dd/yyyy HH:mm");

	private static final SimpleDateFormat mFormatTradeEasyMMddyyyy = new SimpleDateFormat("MM/dd/yyyy");

	// add by huyanzhi
	private static final SimpleDateFormat mFormatTradeEasyProduct = new SimpleDateFormat("dd/MM/yyyy");
	// end

	private static final SimpleDateFormat mFormatExpire = new SimpleDateFormat("MMMM dd, yyyy", local);

	public static final SimpleDateFormat DEFAULT_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static SimpleDateFormat[] loadDateFormats() {
		SimpleDateFormat[] temp = {
				// new SimpleDateFormat("MM/dd/yyyy hh:mm:ss.SSS a"),
				new SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy"),
				// standard Date.toString() results
				new SimpleDateFormat("M/d/yy hh:mm:ss"), new SimpleDateFormat("M/d/yyyy hh:mm:ss"),
				new SimpleDateFormat("M/d/yy hh:mm a"), new SimpleDateFormat("M/d/yyyy hh:mm a"),
				new SimpleDateFormat("M/d/yy HH:mm"), new SimpleDateFormat("M/d/yyyy HH:mm"),
				new SimpleDateFormat("dd.MM.yyyy HH:mm:ss"), new SimpleDateFormat("yy-MM-dd HH:mm:ss.SSS"),
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"),
				// standard Timestamp.toString() results
				new SimpleDateFormat("M-d-yy HH:mm"), new SimpleDateFormat("M-d-yyyy HH:mm"),
				new SimpleDateFormat("MM/dd/yyyy HH:mm:ss.SSS"), new SimpleDateFormat("M/d/yy"),
				new SimpleDateFormat("M/d/yyyy"), new SimpleDateFormat("M-d-yy"), new SimpleDateFormat("M-d-yyyy"),
				new SimpleDateFormat("MMMM d, yyyyy"), new SimpleDateFormat("MMM d, yyyyy") };

		return temp;
	}

	// -----------------------------------------------------------------------
	/**
	 * Gets the array of SimpleDateFormats that DateUtil knows about.
	 **/
	private static SimpleDateFormat[] getFormats() {
		return mDateFormats;
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns a Date set to the last possible millisecond of the day, just
	 * before midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getEndOfDay(Date day) {
		return getEndOfDay(day, Calendar.getInstance());
	}

	public static Date getEndOfDay(Date day, Calendar cal) {
		if (day == null)
			day = new Date();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, cal.getMaximum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMaximum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMaximum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMaximum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns a Date set to the first possible millisecond of the day, just
	 * after midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getStartOfDay(Date day) {
		return getStartOfDay(day, Calendar.getInstance());
	}

	/**
	 * Returns a Date set to the first possible millisecond of the day, just
	 * after midnight. If a null day is passed in, a new Date is created.
	 * midnight (00m 00h 00s)
	 */
	public static Date getStartOfDay(Date day, Calendar cal) {
		if (day == null)
			day = new Date();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, cal.getMinimum(Calendar.HOUR_OF_DAY));
		cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	/**
	 * Returns a Date set just to Noon, to the closest possible millisecond of
	 * the day. If a null day is passed in, a new Date is created. nnoon (00m
	 * 12h 00s)
	 */
	public static Date getNoonOfDay(Date day, Calendar cal) {
		if (day == null)
			day = new Date();
		cal.setTime(day);
		cal.set(Calendar.HOUR_OF_DAY, 12);
		cal.set(Calendar.MINUTE, cal.getMinimum(Calendar.MINUTE));
		cal.set(Calendar.SECOND, cal.getMinimum(Calendar.SECOND));
		cal.set(Calendar.MILLISECOND, cal.getMinimum(Calendar.MILLISECOND));
		return cal.getTime();
	}

	/**
	 * 根据传入的日期字符串转换成相应的日期对象，如果字符串为空或不符合日期格 式，则返回当前时间。
	 * 
	 * @param strDate
	 *            String 日期字符串
	 * @return java.util.Date 日期对象
	 */
	public static Date getDateFromString(String strDate) {
		if (StringUtils.isEmpty(strDate)) {
			return new Date(System.currentTimeMillis());
		}
		try {
			return sdfLongTimePlus.parse(strDate);
		} catch (Exception ex) {
			return new Timestamp(System.currentTimeMillis());
		}
	}

	// -----------------------------------------------------------------------
	public static Date parseFromFormats(String aValue) {
		if (StringUtil.isEmpty(aValue))
			return null;

		// get DateUtil's formats
		SimpleDateFormat formats[] = DateUtil.getFormats();
		if (formats == null)
			return null;

		// iterate over the array and parse
		Date myDate = null;
		for (int i = 0; i < formats.length; i++) {
			try {
				myDate = DateUtil.parse(aValue, formats[i]);
				// if (myDate instanceof Date)
				return myDate;
			} catch (Exception e) {
				// do nothing because we want to try the next
				// format if current one fails
			}
		}
		// haven't returned so couldn't parse
		return null;
	}

	// -----------------------------------------------------------------------
	public static Timestamp parseTimestampFromFormats(String aValue) {
		if (StringUtil.isEmpty(aValue))
			return null;

		// call the regular Date formatter
		Date myDate = DateUtil.parseFromFormats(aValue);
		if (myDate != null)
			return new Timestamp(myDate.getTime());
		return null;
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns a java.sql.Timestamp equal to the current time
	 **/
	public static Timestamp getdate() {
		return new Timestamp(new Date().getTime());
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns a string the represents the passed-in date parsed according to
	 * the passed-in format. Returns an empty string if the date or the format
	 * is null.
	 **/
	public static String format(Date aDate, SimpleDateFormat aFormat) {
		if (aDate == null || aFormat == null) {
			return "";
		}
		synchronized (aFormat) {
			return aFormat.format(aDate);
		}
	}

	// -----------------------------------------------------------------------
	/**
	 * Tries to take the passed-in String and format it as a date string in the
	 * the passed-in format.
	 **/
	public static String formatDateString(String aString, SimpleDateFormat aFormat) {
		if (StringUtil.isEmpty(aString) || aFormat == null)
			return "";
		try {
			Timestamp aDate = parseTimestampFromFormats(aString);
			if (aDate != null) {
				return DateUtil.format(aDate, aFormat);
			}
		} catch (Exception e) {
			// Could not parse aString.
		}
		return "";
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns a Date using the passed-in string and format. Returns null if the
	 * string is null or empty or if the format is null. The string must match
	 * the format.
	 **/
	public static Date parse(String aValue, SimpleDateFormat aFormat) throws ParseException {
		if (StringUtil.isEmpty(aValue) || aFormat == null) {
			return null;
		}

		return aFormat.parse(aValue);
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns true if endDate is after startDate or if startDate equals endDate
	 * or if they are the same date. Returns false if either value is null.
	 **/
	public static boolean isValidDateRange(Date startDate, Date endDate) {
		return isValidDateRange(startDate, endDate, true);
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns true if endDate is after startDate or if startDate equals
	 * endDate. Returns false if either value is null. If equalOK, returns true
	 * if the dates are equal.
	 **/
	public static boolean isValidDateRange(Date startDate, Date endDate, boolean equalOK) {
		// false if either value is null
		if (startDate == null || endDate == null) {
			return false;
		}

		if (equalOK) {
			// true if they are equal
			if (startDate.equals(endDate)) {
				return true;
			}
		}

		// true if endDate after startDate
		if (endDate.after(startDate)) {
			return true;
		}

		return false;
	}

	// -----------------------------------------------------------------------
	// returns full timestamp format
	public static SimpleDateFormat defaultTimestampFormat() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
	}

	// -----------------------------------------------------------------------
	// convenience method returns minimal date format
	public static SimpleDateFormat get8charDateFormat() {
		return DateUtil.mFormat8chars;
	}

	// -----------------------------------------------------------------------
	// convenience method returns minimal date format
	public static SimpleDateFormat defaultDateFormat() {
		return DateUtil.friendlyDateFormat(true);
	}

	// -----------------------------------------------------------------------
	// convenience method
	public static String defaultTimestamp(Date date) {
		return DateUtil.format(date, DateUtil.defaultTimestampFormat());
	}

	// -----------------------------------------------------------------------
	// convenience method
	public static String defaultDate(Date date) {
		return DateUtil.format(date, DateUtil.defaultDateFormat());
	}

	// -----------------------------------------------------------------------
	// convenience method returns long friendly timestamp format
	public static SimpleDateFormat friendlyTimestampFormat() {
		return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
	}

	// -----------------------------------------------------------------------
	// convenience method returns long friendly formatted timestamp
	public static String friendlyTimestamp(Date date) {
		return DateUtil.format(date, DateUtil.friendlyTimestampFormat());
	}

	// -----------------------------------------------------------------------
	// convenience method returns long friendly formatted timestamp
	public static String format8chars(Date date) {
		return DateUtil.format(date, mFormat8chars);
	}

	// -----------------------------------------------------------------------
	// convenience method returns long friendly formatted timestamp
	public static String formatIso8601Day(Date date) {
		return DateUtil.format(date, mFormatIso8601Day);
	}

	public static String formatIso8601Day(Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp.getTime());
		return DateUtil.format(calendar.getTime(), mFormatIso8601Day);
	}

	public static String formatTradeEasy(Date date) {
		return DateUtil.format(date, mFormatTradeEasy);
	}

	// add by huyanzhi
	public static String formatTradeEasyProduct(Date date) {
		return DateUtil.format(date, mFormatTradeEasyProduct);
	}
	//

	public static String formatFormatTradeEasyMMddyyyy(Date date) {
		return DateUtil.format(date, mFormatTradeEasyMMddyyyy);
	}

	public static String formatTradeEasy(Timestamp timestamp) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timestamp.getTime());
		return DateUtil.format(calendar.getTime(), mFormatTradeEasy);
	}

	// -----------------------------------------------------------------------
	public static String formatRfc822(Date date) {
		return DateUtil.format(date, mFormatRfc822);
	}

	public static String formatExpire(Date date) {
		return DateUtil.format(date, mFormatExpire);
	}

	// -----------------------------------------------------------------------
	// This is a hack, but it seems to work
	public static String formatIso8601(Date date) {
		if (date == null)
			return "";

		// Add a colon 2 chars before the end of the string
		// to make it a valid ISO-8601 date.

		String str = DateUtil.format(date, mFormatIso8601);
		StringBuffer sb = new StringBuffer();
		sb.append(str.substring(0, str.length() - 2));
		sb.append(":");
		sb.append(str.substring(str.length() - 2));
		return sb.toString();
	}

	// -----------------------------------------------------------------------
	// convenience method returns minimal date format
	public static SimpleDateFormat minimalDateFormat() {
		return DateUtil.friendlyDateFormat(true);
	}

	// -----------------------------------------------------------------------
	// convenience method using minimal date format
	public static String minimalDate(Date date) {
		return DateUtil.format(date, DateUtil.minimalDateFormat());
	}

	// -----------------------------------------------------------------------
	// convenience method that returns friendly data format
	// using full month, day, year digits.
	public static SimpleDateFormat fullDateFormat() {
		return DateUtil.friendlyDateFormat(false);
	}

	// -----------------------------------------------------------------------
	public static String fullDate(Date date) {
		return DateUtil.format(date, DateUtil.fullDateFormat());
	}

	// -----------------------------------------------------------------------
	/**
	 * Returns a "friendly" date format.
	 * 
	 * @param minimalFormat
	 *            Should the date format allow single digits.
	 **/
	public static SimpleDateFormat friendlyDateFormat(boolean minimalFormat) {
		if (minimalFormat) {
			return new SimpleDateFormat("d.M.yy");
		}

		return new SimpleDateFormat("dd.MM.yyyy");
	}

	// -----------------------------------------------------------------------
	/**
	 * Format the date using the "friendly" date format.
	 */
	public static String friendlyDate(Date date, boolean minimalFormat) {
		return DateUtil.format(date, DateUtil.friendlyDateFormat(minimalFormat));
	}

	// -----------------------------------------------------------------------
	// convenience method
	public static String friendlyDate(Date date) {
		return DateUtil.format(date, DateUtil.friendlyDateFormat(true));
	}

	public static Date parseFormatIso8601Date(String date) throws Exception {
		Date returnDate = null;
		try {
			returnDate = mFormatIso8601Day.parse(date);
		} catch (Exception e) {
			throw e;
		}
		return returnDate;
	}

	// add by huyanzhi
	public static String addDate(String date, String type, int into) throws Exception {
		String Sdate = "";
		try {
			GregorianCalendar grc = new GregorianCalendar();
			grc.setTime(new Date(date));
			if (type.equals("D")) {
				grc.add(GregorianCalendar.DATE, into);
			} else if (type.equals("M")) {
				grc.add(GregorianCalendar.MONTH, into);
			} else if (type.equals("Y")) {
				grc.add(GregorianCalendar.YEAR, into);
			}
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Sdate = new String(formatter.format(grc.getTime()));
		} catch (Exception e) {
			throw e;
		}
		return Sdate;
	}

	public static String addDate(String date, String into) throws Exception {
		String Sdate = "";
		try {
			date = date.replaceAll("-", "/");
			date = date.substring(0, date.length() - 2);
			GregorianCalendar grc = new GregorianCalendar();
			grc.setTime(new Date(date));
			grc.add(GregorianCalendar.DATE, Integer.parseInt(into));
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			Sdate = new String(formatter.format(grc.getTime()));
		} catch (Exception e) {
			throw e;
		}
		return Sdate;
	}

	public static String formatDate(Date date, String pattern) {
		if (pattern == null || pattern.equals("") || pattern.equals("null")) {
			pattern = "yyyy-MM-dd";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}
	//

	public static String addValidateDate(String date, String into) throws Exception {
		String Sdate = "";
		try {
			date = date.replaceAll("-", "/");
			date = date.substring(0, date.length() - 2);
			GregorianCalendar grc = new GregorianCalendar();
			grc.setTime(new Date(date));
			grc.add(GregorianCalendar.DATE, Integer.parseInt(into));
			Sdate = new String(mFormatExpire.format(grc.getTime()));
		} catch (Exception e) {
			throw e;
		}
		return Sdate;
	}

	public static String addDayToStringDate(String formate, String strDate, String days) {
		String stringDate = null;
		try {
			Date date = fromStringToDate(formate, strDate);
			long now = date.getTime() + (long) Integer.parseInt(days) * DAY_IN_MILLISECOND;

			stringDate = getFomartDate(new Date(now), formate);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		return stringDate;
	}

	public static Date addDayToStringDate2(String formate, String strDate, String days) {
		Date date = null;
		try {
			date = fromStringToDate(formate, strDate);
			long now = date.getTime() + (long) Integer.parseInt(days) * DAY_IN_MILLISECOND;

			date = new Date(now);

		} catch (ParseException e) {

			e.printStackTrace();
		}

		return date;
	}

	public static Date dateDayAdd(Date date, int days) {
		long now = date.getTime() + (long) days * DAY_IN_MILLISECOND;
		return new Date(now);
	}

	/**
	 *
	 * 字符串形式转化为Date类型 String类型按照format格式转为Date类型
	 **/
	public static Date fromStringToDate(String format, String dateTime) throws ParseException {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		date = sdf.parse(dateTime);
		return date;
	}

	/**
	 *
	 * 字符串形式转化为Date类型 String类型按照format格式转为Date类型
	 **/
	public static Date fromStringToDate(Date date) throws ParseException {
		return sdfLongTimePlus.parse(sdfLongTimePlus.format(date));
	}

	/**
	 * <p>
	 * Description: 修改时间为指定时间当天的23:59:59.000
	 * </p>
	 * 
	 * @param date
	 *            需要修改的时间
	 * @return 修改后的时间
	 */
	public static Date fillTime(Date date) {
		Date result = removeTime(date);
		result.setTime(result.getTime() + 24 * 60 * 60 * 1000 - 1000); // 加一天
		return result;
	}

	/**
	 * <p>
	 * Description: 去掉日期时间中的时间部分
	 * </p>
	 * 如: 2013-11-11 18:56:33 ---> 2013-11-11 00:00:00
	 * 
	 * @param date
	 *            需要修改的时间
	 * @return 修改后的时间
	 */
	public static Date removeTime(Date date) {
		Date result = null;
		try {
			SimpleDateFormat df = new SimpleDateFormat(FORMAT_PATTERN_DATE);
			String dateStr = df.format(date);
			result = df.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		try {
			System.out.println(DateUtil.toDayToStr("yyyy年MM月dd日"));

			// System.out.println("我的年龄：" + DateUtil.getAge("210202720508171"));
			// System.out.println("我的年龄：" +
			// DateUtil.getAge("210202197205081712"));
			// int x = 31;
			// java.sql.Date date = DateUtil.getDateByAge(x);
			// System.out.println("31岁人的出生日期：" + date.toString());
			// System.out.println(
			// "========getYearMonth:" + DateUtil.getYearAndMonth("200406"));
			// System.out.println("====" + DateUtil.month2YearMonth("15"));
			// System.out.println(
			// "========increaseMonth: "
			// + DateUtil.increaseMonth(DateUtil.getCurrentDate(), 2));

			// Date data = new Date();
			// System.out.println(data);
			// System.out.println(
			// "测试==" + DateUtil.dateToString(data, "YYYY-MM-DD HH24:MI:SS"));

			// Date newdate = new Date();
			// System.out.println(DateUtil.parseFromFormats(sdata));
			// String sdata = "2005-01-01";
			// System.out.println(
			// DateUtil.stringToDate(sdata, "YYYY-MM-DD"));
			// Date nowDate1 = new Date();
			// Date nowDate2 = new Date();
			// System.out.println("nowDate1" + nowDate1);
			// System.out.println("nowDate2" + nowDate2);
			// System.out.println(DateUtil.daysBetweenDates(nowDate1,nowDate2));
			// System.out.println(
			// "加上50天==" + DateUtil.getSpecDate("yyyy/MM/dd", 0, 0, 50));
			// System.out.println(
			// DateUtil.getDefaultFormattedDate(
			// DateUtil.getSpecDate("yyyy/MM/dd", 0, 0, 50)));
			// System.out.println(
			// "add 50 days" + DateUtil.increaseDay(new Date(), 365));
			System.out.println(getDateShortLongTimeCn(new Date()));
			System.out.println(null + "1");
			System.out.println(convertDateToDay(new Date()));
			Date nows = new Date();
			System.out.println("============" + getDateLongCn(nows));
			System.out.println("============0000000000000000000000000000000");
			System.out
					.println("============stringToDate=" + stringToDate("2009-11-18 19:14:31", "yyyy-MM-dd h24:mi:ss"));

			System.out.println("============getDateFromString=" + getDateFromString("2009-11-18 19:14:31"));

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * 
	 * <br>
	 * <b>功能：</b>返回时分秒:HHmmss<br>
	 * <b>作者：</b>admin<br>
	 * <b>日期：</b> Aug 26, 2011 <br>
	 * 
	 * @param date
	 * @return
	 */
	public static Integer getTimeFormatIntger(Date date) {
		if (date == null) {
			return 0;
		}
		String strTemp = DateUtil.getFomartDate(date, "yyyyMMddHHmmss");
		String nowTime = strTemp.substring(8, 14);
		return Integer.valueOf(nowTime);
	}

	public static String getNowDayStr(Date date) {
		if (date == null) {
			return "";
		}

		Calendar c = Calendar.getInstance();
		int i = c.get(Calendar.DAY_OF_WEEK);
		System.out.println(i);
		// String strTemp = DateUtil.getFomartDate(date, "yyyyMMddHHmmss");
		// String nowTime = strTemp.substring(8,14);
		return "";
	}

	/**
	 * @param format
	 *            日期格式
	 * @return String
	 * @author admin
	 * @return String
	 */
	public static String toDayToStr(String format) {
		try {
			Date now = new Date();
			return DateToStr(now, format) + " " + getWeekOfDate(now);
		} catch (Exception e) {
			System.out.println("Date 转 String 类型失败: " + e);
			return null;
		}
	}

	/**
	 * @param date
	 * @param format
	 *            日期格式
	 * @return String
	 * @author admin
	 * @return String
	 */
	public static String DateToStr(Date date, String format) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			return sdf.format(date);
		} catch (Exception e) {
			System.out.println("Date 转 String 类型失败: " + e);
			return null;
		}
	}

	/**
	 * @author admin
	 * @return DATE 型加具体的天数
	 * 
	 * @param date,
	 *            int days
	 */
	public static Date dateAddDays(Date date, int days) {
		long now = date.getTime() + (long) days * DAY_IN_MILLISECOND;
		return new Date(now);
	}

	/**
	 * @return 将DATE 转换成字符性日期格式
	 * @author admin
	 * @param date,String
	 *            fFormatStr eg:yyyy-MM-dd HH:mm:ss
	 */
	public static String dateTypeToString(Date date, String fFormatStr) {
		// yyyy-MM-dd HH:mm:ss
		SimpleDateFormat dateformat = new SimpleDateFormat(fFormatStr);
		String strDate = dateformat.format(date);
		return strDate;
	}

	/**
	 * @param fFormatStr
	 * @author admin
	 * @获取当前的系统时间，并按照固定的格式初始话
	 */
	public static String getStringOfNowDate(String fFormatStr) {
		String nowDateString = dateTypeToString(new Date(), fFormatStr);
		return nowDateString;
	}

	/**
	 * @ author zhangyong @ 获取当月的第一天，2009-05-01
	 */
	public static String getStringOfFirstDayInMonth() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String temp = sdf.format(date);
		String firstDayInMoth = "";
		firstDayInMoth = temp + "-01";

		return firstDayInMoth;

	}

	/**
	 * 获取当前日期是星期几<br>
	 * 
	 * @param dt
	 * @return 当前日期是星期几
	 */
	public static String getWeekOfDate(Date dt) {
		String[] weekDays = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };
		Calendar cal = Calendar.getInstance();
		cal.setTime(dt);

		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0)
			w = 0;

		return weekDays[w];
	}

	/**
	 * 
	 * @Title: getNewReceiveDate
	 * @Description: 订单收货日期特殊处理
	 * @param receiveDate
	 * @return
	 */
	public static String getNewReceiveDate(String receiveDate) {
		/*
		 * 
		 * 之前的订单收货日期 由于不是标准的日期格式 如 2015-12-1,对后面相关的逻辑处理造成影响，这里先做特殊处理，以后做调整 TODO
		 * 9 7
		 */
		if (!StringUtils.isBlank(receiveDate)) {
			char[] datechars = receiveDate.toCharArray();
			char[] datechars_new = new char[10];
			int index_month = -1;
			int index_day = -1;
			for (int i = 0, j = 0; i < datechars.length; i++) {
				if (datechars[i] == '-' && index_month == -1) {
					index_month = i;
					if (datechars[i + 2] == '-') {
						datechars_new[j++] = '-';
						datechars_new[j++] = '0';
						continue;
					}
				} else if (datechars[i] == '-' && index_day == -1) {
					index_day = i;
					if (i + 2 == datechars.length) {
						datechars_new[j++] = '-';
						datechars_new[j++] = '0';
						continue;
					}
				}
				datechars_new[j++] = datechars[i];
			}
			return new String(datechars_new);
		}
		return receiveDate;
	}
}
