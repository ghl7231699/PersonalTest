package com.example.mylibrary;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * 时间工具
 */
public class TimeUtil {
	
	/**
     * yyyy-MM-dd
     */
	public static final String DATE = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
	public static final String TIMESTAMP = "yyyy-MM-dd HH:mm:ss";

    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String MONTH_TIMESTAMP = "MM-dd HH:mm:ss";
    /**
     * 根据指定的格式格式化当前系统时间
     *
     * @param format 要格式化成的格式
     * @return 格式化成功后的时间字符串
     */
	public static String formatCurrentTime(String format) {
		Date date = new Date(System.currentTimeMillis());
		SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.SIMPLIFIED_CHINESE);
		return dateFormat.format(date);
	}

    /**
     * 格式化时间
     *
     * @param time 要格式化的时间对应的毫秒数
     * @param format 要格式化成的格式
     * @return 格式化成功后的时间字符串
     */
	public static String formatTime(long time, String format) {
		return new SimpleDateFormat(format).format(new Date(time));
	}

    /**
     * 将字符串解析成时间
     *
     * @param dateStr 时间字符串
     * @param format 预期的时间格式
     * @return 时间解析成功对应的毫秒数，如果解析失败则返回 -1。
     */
	public static long parseTime(String dateStr, String format) {
		try {
			return new SimpleDateFormat(format).parse(dateStr).getTime();
		} catch (ParseException e) {
			
		}
		return -1;
	}

    /**
     * 基于日期，计算两个时间之间间隔多少天，比如今天晚上 23:55 和明天凌晨 00:05 之间间隔 1 天，
     * 但明天凌晨 00:05 和明天夜间 23:55 之间间隔 0 天。
     */
	public static int getDaysBetween(long date1, long date2) {
		Calendar c1 = Calendar.getInstance();
		c1.setTimeInMillis(date1);
		c1.set(Calendar.HOUR_OF_DAY, 0);
		c1.set(Calendar.MINUTE, 0);
		c1.set(Calendar.SECOND, 0);
		c1.set(Calendar.MILLISECOND, 0);
		
		Calendar c2 = Calendar.getInstance();
		c2.setTimeInMillis(date2);
		c2.set(Calendar.HOUR_OF_DAY, 0);
		c2.set(Calendar.MINUTE, 0);
		c2.set(Calendar.SECOND, 0);
		c2.set(Calendar.MILLISECOND, 0);
		
		return (int) ((c2.getTimeInMillis() - c1.getTimeInMillis()) / (24 * 3600 * 1000));
	}
	
	/**
	 * 获取时间戳，格式：yyyy-MM-dd HH:mm:ss
	 */
	public static String getTimeStamp() {
        return formatCurrentTime(TIMESTAMP);
	}

    /**
     * 根据距离当前的时间差格式化时间，如：1分钟前/x分钟前/x小时前/昨天 HH:mm/前天 HH:mm/MM-dd HH:mm 等。
     *
     * @param time 要格式化的时间
     * @return 格式化后的时间字符串
     */
	public static String getFriendlyTime(long time) {
		long now = System.currentTimeMillis();
		long elapse = now - time;
		if (elapse < 60 * 1000) {
			return "1分钟前";
		} else if (elapse < 3600 * 1000) {
			return (elapse / 1000 / 60) + "分钟前";
		} else if (elapse < 24 * 3600 * 1000) {
			return (elapse / 1000 / 3600) + "小时前";
		} else if (elapse < 2 * 24 * 3600 * 1000) {
			return "昨天 " + formatTime(time, "HH:mm");
		} else if (elapse < 3 * 24 * 3600 * 1000) {
			return "前天 " + formatTime(time, "HH:mm");
		} else {
			return formatTime(time, "MM-dd HH:mm");
		}
	}


	/**
	 * 将时间戳转为代表"距现在多久之前"的字符串
	 *
	 * @param timeStr
	 *            时间戳
	 * @return
	 */
	public static String getStandardDate(String timeStr) {
		if ("".equals(timeStr)) {
			return formatCurrentTime(MONTH_TIMESTAMP);
		}
		StringBuffer sb = new StringBuffer();

		long t = Long.parseLong(timeStr);
		long time = System.currentTimeMillis() - t;
		long mill = (long) Math.ceil(time / 1000.0f);// 秒前

		long minute = (long) Math.ceil(time / 60 / 1000.0f);// 分钟前

		long hour = (long) Math.ceil(time / 60 / 60 / 1000.0f);// 小时

		long day = (long) Math.ceil(time / 24 / 60 / 60 / 1000.0f);// 天前

		if (day - 1 > 0) {
			sb.append(day + "天");
		} else if (hour - 1 > 0) {
			if (hour >= 24) {
				sb.append("1天");
			} else {
				sb.append(hour + "小时");
			}
		} else if (minute - 1 > 0) {
			if (minute == 60) {
				sb.append("1小时");
			} else {
				sb.append(minute + "分钟");
			}
		} else if (mill - 1 > 0) {
			if (mill == 60) {
				sb.append("1分钟");
			} else {
				sb.append(mill + "秒");
			}
		} else {
			sb.append("刚刚");
		}
		if (!sb.toString().equals("刚刚")) {
			sb.append("前");
		}
		return sb.toString();
	}

}
