package com.example.liangge.rxjavatest.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by guhongliang on 2018/4/3.
 */

public class TimeUtils {

    /**
     * 根据当前日期获得是星期几
     *
     * @return
     */
    public static String getWeek(String time, int type) {
        String Week = "";

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH));

        int dayOfWeek = calendar.get((Calendar.DAY_OF_WEEK));

        try {
            calendar.setTime(format.parse(time));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (type) {
            case 1://本周开始日期
                calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek);
//                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                break;
            case 2://本周结束日期
                calendar.add(Calendar.DAY_OF_MONTH, -dayOfWeek + 7 + 1);
//                calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                break;
        }
        Week = format.format(calendar.getTime());


//        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
//            Week += "周天";
//        }
//        if (calendar.get(Calendar.DAY_OF_WEEK) == 2) {
//            Week += "周一";
//        }
//        if (calendar.get(Calendar.DAY_OF_WEEK) == 3) {
//            Week += "周二";
//        }
//        if (calendar.get(Calendar.DAY_OF_WEEK) == 4) {
//            Week += "周三";
//        }
//        if (calendar.get(Calendar.DAY_OF_WEEK) == 5) {
//            Week += "周四";
//        }
//        if (calendar.get(Calendar.DAY_OF_WEEK) == 6) {
//            Week += "周五";
//        }
//        if (calendar.get(Calendar.DAY_OF_WEEK) == 7) {
//            Week += "周六";
//        }
        return Week;
    }


    /**
     * start
     * 本周开始时间戳 - 以星期一为本周的第一天
     */
    public static String getWeekStartTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        cal.add(Calendar.DATE, -day_of_week + 1);
        return simpleDateFormat.format(cal.getTime()) + "000000000";
    }

    /**
     * end
     * 本周结束时间戳 - 以星期一为本周的第一天
     */
    public static String getWeekEndTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd", Locale.getDefault());
        Calendar cal = Calendar.getInstance();
        int day_of_week = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (day_of_week == 0) {
            day_of_week = 7;
        }
        cal.add(Calendar.DATE, -day_of_week + 7);
        return simpleDateFormat.format(cal.getTime()) + "235959999";
    }

    /**
     * 获取今天往后一周的日期（几月几号）
     */
    public static List<String> getSevendate() {
        List<String> dates = new ArrayList<>();
        final Calendar c = Calendar.getInstance();
        c.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));


        for (int i = 0; i < 7; i++) {
            String mYear = String.valueOf(c.get(Calendar.YEAR));// 获取当前年份
            String mMonth = String.valueOf(c.get(Calendar.MONTH) + 1);// 获取当前月份
            String mDay = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + i);// 获取当前日份的日期号码
            if (Integer.parseInt(mDay) > MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear), (i + 1))) {
                mDay = String.valueOf(MaxDayFromDay_OF_MONTH(Integer.parseInt(mYear), (i + 1)));
            }
            String date = mMonth + "月" + mDay + "日";
            dates.add(date);
        }
        return dates;
    }

    /**
     * 得到当年当月的最大日期
     **/
    public static int MaxDayFromDay_OF_MONTH(int year, int month) {
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR, year);
        time.set(Calendar.MONTH, month - 1);//注意,Calendar对象默认一月为0
        int day = time.getActualMaximum(Calendar.DAY_OF_MONTH);//本月份的天数
        return day;
    }
}
