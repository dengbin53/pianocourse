package com.zconly.pianocourse.util;

import android.text.TextUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 时间工具类
 *
 * @author DengBin
 */
public class DateTool {

    private static Locale locale = Locale.getDefault();

    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy", Locale.getDefault());
        }
    };

    // 当前时间戳
    public static long getCurrentTime() {
        return System.currentTimeMillis();
    }

    // 获取当前时间的GMT Unix时间戳
    public static long getGMTUnixTime() {
        Calendar calendar = Calendar.getInstance();
        // 获取当前时区下日期时间对应的时间戳
        long unixTime = calendar.getTimeInMillis();
        // 获取标准格林尼治时间下日期时间对应的时间戳
        long unixTimeGMT = unixTime - TimeZone.getDefault().getRawOffset();
        return unixTimeGMT;
    }

    // 当前年份
    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        return calendar.get(Calendar.YEAR);
    }

    /**
     * 得到格式为 yyyy年MM月dd日 格式日期字符串
     *
     * @param time yyyy-MM-dd 格式的提起字符串
     */
    public static String getTimeEndDayForMatChinese(String time) {
        SimpleDateFormat olddf1 = new SimpleDateFormat("yyyy-MM-dd", locale);
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日", locale);
        Date date = null;
        try {
            date = olddf1.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return df1.format(date);
    }

    // 返回相差分钟数
    public static int differMinute(Date endDate, Date startDate) {
        if (endDate == null || startDate == null)
            return 0;
        Calendar c1 = Calendar.getInstance();
        c1.setTime(endDate);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(startDate);
        long l = c1.getTimeInMillis() - c2.getTimeInMillis();
        return (int) (l / (1000 * 60));
    }

    // 返回相差秒数数
    public static int differSecond(Date endDate, Date startDate) {
        if (endDate == null || startDate == null)
            return 0;

        Calendar c1 = Calendar.getInstance();
        c1.setTime(endDate);
        Calendar c2 = Calendar.getInstance();
        c2.setTime(startDate);
        long l = c1.getTimeInMillis() - c2.getTimeInMillis();

        return (int) (l / (1000));
    }

    // 判断给定字符串时间是否为今年
    public static boolean isYear(long sdate) {
        boolean b = false;
        Date time = new Date(sdate);
        Date today = new Date();
        if (time != null) {
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if (nowDate.equals(timeDate)) {
                b = true;
            }
        }
        return b;
    }

    /**
     * 将字符串转位日期类型 yyyy-MM-dd HH:mm:ss
     */
    public static long getMaxBirth() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getCurrentTime());
        int y = c.get(Calendar.YEAR) - 12;
        c.set(y, 11, 31);
        return c.getTimeInMillis();
    }

    /**
     * 该方法将时间格式化到日 yyyy年MM月dd日
     */
    public static String localToDay(long time) {
        String s = "";
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy年MM月dd日", locale);
        try {
            s = df1.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }


    // 该方法将时间格式化到日 yyyy年MM月
    public static String localToMonth(long time) {
        String s = "";
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy年MM月", locale);
        try {
            s = df1.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    /**
     * 当年时间显示
     */
    public static String detailMinute(long time) {
        String s = "";
        SimpleDateFormat df1 = new SimpleDateFormat("MM月dd日 HH:mm", locale);
        try {
            s = df1.format(time);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s;
    }

    public static String formatYMD(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", locale);
        String birthStr = sdf.format(time);
        return birthStr;
    }

    public static String formatYMDGMT(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", locale);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        String birthStr = sdf.format(time);
        return birthStr;
    }

    public static String formatYMD2(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd", locale);
        String birthStr = sdf.format(time);
        return birthStr;
    }

    public static String formatSecond(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", locale);
        String birthStr = sdf.format(time);
        return birthStr;
    }

    public static String StringToDay(String birth) {
        if (TextUtils.isEmpty(birth))
            birth = "2016-01-01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", locale);
        try {
            Date date = sdf.parse(birth);
            return localToDay(date.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return birth;
    }

    /**
     * 获取时间的年、月、日
     */
    public static int[] getYMd(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);// 获取年份
        int month = cal.get(Calendar.MONTH);// 获取月份
        int day = cal.get(Calendar.DATE);// 获取日
        return new int[]{year, month, day};
    }

    public static boolean isDayAfterToday(Calendar date) {
        Calendar cal = Calendar.getInstance();
        return cal.getTimeInMillis() < date.getTimeInMillis();
    }

    public static boolean isDayBeforeToday(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        if (date == null)
            return false;
        return cal.getTimeInMillis() > date.getTime();
    }

    public static boolean isToday(Date issueDate) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.HOUR, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(issueDate);
        calendar2.set(Calendar.HOUR_OF_DAY, 0);
        calendar2.set(Calendar.HOUR, 0);
        calendar2.set(Calendar.MINUTE, 0);
        calendar2.set(Calendar.SECOND, 0);
        calendar2.set(Calendar.MILLISECOND, 0);

        return !(calendar.after(calendar2) || calendar.before(calendar2));
    }

    // 将系统默认时区的Unix时间戳转换为GMT Unix时间戳
    public static long getGMTUnixTime(long unixTime) {
        return unixTime - TimeZone.getDefault().getRawOffset();
    }

    // 将GMT Unix时间戳转换为系统默认时区的Unix时间戳
    public static long getCurrentTimeZoneUnixTime(long gmtUnixTime) {
        return gmtUnixTime + TimeZone.getDefault().getRawOffset();
    }


}
