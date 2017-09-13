package com.example.datepicker.util;

import android.annotation.SuppressLint;
import android.text.TextUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 计算器用日期类
 *
 * @author colin
 * @version 1.0, 2015-11-18 上午9:18
 */
@SuppressLint("SimpleDateFormat")
public class DateUtil {
    public static final String LONG_TERM_DATE = "2100-01-01 00:00:00";

    private static final String TIME_PATTERN = "HH:mm:ss";
    private static final String YEAR_PATTERN = "yyyy";
    private static final String DATE_PATTERN = "yyyy-MM-dd";
    private static final String MONTH_PATTERN = "MM";
    private static final String DATE_TIME_PATTERN = DATE_PATTERN + " HH:mm:ss";
    private static final String DATE_TIME_AFTER = "HH:mm:ss";
    private static final String DATE_TIMESTAMP_PATTERN = DATE_TIME_PATTERN + ".S";
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static SimpleDateFormat sdfShort = new SimpleDateFormat("yyyy-MM-dd");

    //获取当前时间字符串
    public static String getCurrentDateString() {
        return sdf.format(new Date());
    }

    //获取时分秒
    public static String getCurrentTime() {
        return new SimpleDateFormat(TIME_PATTERN).format(new Date());
    }

    //获取当前小时
    public static int getCurrentHour() {
        return Integer.valueOf(getCurrentTime().split(":")[0]);
    }

    //把日期转为字符串
    public static String convertToString(Date date) {
        if (date == null) return "";
        DateFormat df = new SimpleDateFormat(DATE_PATTERN, Locale.US);
        return df.format(date);
    }

    //把日期转为年字符串
    public static String convertToYearString(String dateString) {
        if (StringUtil.isEmpty(dateString)) {
            return "";
        }
        Date date = getDateByString(dateString);
        DateFormat df = new SimpleDateFormat(YEAR_PATTERN, Locale.US);
        return df.format(date);
    }

    //把完成时间戳转换成年月日字符串
    public static String convertToYearMonthDayString(String dateString) {
        if (StringUtil.isEmpty(dateString)) {
            return "";
        }
        Date date = getDateByString(dateString);
        DateFormat df = new SimpleDateFormat(DATE_PATTERN, Locale.US);
        return df.format(date);
    }

    //把日期转为月份字符串
    public static String convertToMonthString(String dateString) {
        if (StringUtil.isEmpty(dateString)) {
            return "";
        }
        Date date = getDateByString(dateString);
        DateFormat df = new SimpleDateFormat(MONTH_PATTERN, Locale.US);
        return df.format(date);
    }

    //把日期字符串转换为年字符串
    public static String stringConvertToYearString(String dateString) {
        if (StringUtil.isEmpty(dateString)) {
            return "";
        }
        Date date = getDateByString(dateString);
        DateFormat df = new SimpleDateFormat(YEAR_PATTERN, Locale.US);
        return df.format(date);
    }

    //把年转换为带有时间的字符串
    public static String yearConvertToCompleteString(String yearString) {
        if (StringUtil.isEmpty(yearString)) {
            return "";
        }
        Date date = getDateByString(yearString + "-08-08 18:50:11");
        DateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.US);
        return df.format(date);
    }

    //把日期转换为带有时间的字符串
    public static String convertToCompleteString(String dateString) {
        if (StringUtil.isEmpty(dateString)) {
            return "";
        }
        Date date = getDateByString(dateString + " 18:50:11");
        DateFormat df = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.US);
        return df.format(date);
    }

    //把字符串转为日期
    public static Date getDateByString(String dateString) {
        if (StringUtil.isEmpty(dateString)) return null;
        try {
            return sdf.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getDateByShortString(String dateString) {
        try {
            return sdfShort.parse(dateString);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int getIntegerDate(Date dateTime) {
        Long date;
        if (dateTime == null) {
            date = new Date().getTime();
        } else {
            date = dateTime.getTime();
        }
        return Integer.valueOf(date.toString().substring(0, date.toString().length() - 3));
    }


    public static String preOrBeforeYear(int year) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy", Locale.US);
        Calendar c = Calendar.getInstance();//此时打印它获取的是系统当前时间
        c.add(Calendar.YEAR, year);
        return df.format(c.getTime());
    }

    /**
     * 获取前几月或后几月
     *
     * @param month ss
     * @return 获取前几月或后几月
     */
    public static String getMonth(int month) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月", Locale.US);
        Calendar c = Calendar.getInstance();//获取当前日期
        c.add(Calendar.MONTH, month);
        return df.format(c.getTime());
    }

    /**
     * 获取指定时间的前几个月和后几个月
     *
     * @param month ss
     * @param date  ss
     * @return ss
     */
    public static Date getDateMonthDay(int month, Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar c = Calendar.getInstance();//获取当前日期
        c.setTime(date);
        c.add(Calendar.MONTH, month);
        return c.getTime();
    }

    /**
     * 获取指定时间月的第一天
     *
     * @param date s
     * @return s
     */
    public static Date getFirstMonthDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar c = Calendar.getInstance();//获取当前日期
        c.setTime(date);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /**
     * 获取指定时间月的第几号
     *
     * @param date a
     * @return a
     */
    public static String getMonthDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        return df.format(date);
    }

    /**
     * 获取指定年的第一天
     *
     * @param date a
     * @return a
     */
    public static Date getFirstYearDate(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
        Calendar c = Calendar.getInstance();//获取当前日期
        c.setTime(date);
        c.set(Calendar.MONTH, 0);
        c.set(Calendar.DAY_OF_MONTH, 1);
        return c.getTime();
    }

    /**
     * 获取本周的第一天
     *
     * @return a
     */
    public static Date getFistWeekDay() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_WEEK, 0);
        return c.getTime();
    }

    //获取本季度的第一天
    public static Date getCurrentQuarterStartTime() {
        Calendar c = Calendar.getInstance();
        int currentMonth = c.get(Calendar.MONTH) + 1;
        if (currentMonth >= 1 && currentMonth <= 3)
            c.set(Calendar.MONTH, 0);
        else if (currentMonth >= 4 && currentMonth <= 6)
            c.set(Calendar.MONTH, 3);
        else if (currentMonth >= 7 && currentMonth <= 9)
            c.set(Calendar.MONTH, 4);
        else if (currentMonth >= 10 && currentMonth <= 12)
            c.set(Calendar.MONTH, 9);
        c.set(Calendar.DATE, 1);
        return c.getTime();
    }


    /**
     * @return a
     */
    public static String getMonthFirstDay() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return DateUtil.format(calendar.getTime(), DateUtil.DATE_PATTERN) + " 00:00:00";
    }

    /**
     *
     * @return s
     */
    public static String getCurrentSeasonFirstDay() {
        Calendar c = Calendar.getInstance();//获取当前日期
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        StringBuilder csfd = new StringBuilder();
        csfd.append(year).append("-");
        if (month >= Calendar.JANUARY && month <= Calendar.MARCH) {
            csfd.append("01-01 00:00:00");
        } else if (month >= Calendar.APRIL && month <= Calendar.JUNE) {
            csfd.append("04-01 00:00:00");
        } else if (month >= Calendar.JULY && month <= Calendar.SEPTEMBER) {
            csfd.append("07-01 00:00:00");
        } else {
            csfd.append("10-01 00:00:00");
        }

        return csfd.toString();
    }

    /**
     *
     * @return s
     */
    public static String getCurrentSeasonLastDay() {
        Calendar c = Calendar.getInstance();//获取当前日期
        int month = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        StringBuilder csfd = new StringBuilder();
        csfd.append(year).append("-");
        if (month >= Calendar.JANUARY && month <= Calendar.MARCH) {
            csfd.append("03-31 23:59:59");
        } else if (month >= Calendar.APRIL && month <= Calendar.JUNE) {
            csfd.append("06-30 23:59:59");
        } else if (month >= Calendar.JULY && month <= Calendar.SEPTEMBER) {
            csfd.append("09-30 23:59:59");
        } else {
            csfd.append("12-31 23:59:59");
        }

        return csfd.toString();

    }

    /**
     *
     * @param date   日期
     * @param format 日期格式
     * @return 字符串
     */
    public static String format(Date date, String format) {
        if (null == date) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
        return sdf.format(date);
    }

    /**
     * 获取某一天最后一秒时间
     *
     * @param date s
     * @return s
     */
    public static Date getTodayLastTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 获取指定天的最开始的时间
     *
     * @param date s
     * @return s
     */
    public static Date getTodayFirstTime(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.set(Calendar.HOUR, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        return c.getTime();
    }

    public static Date getSystemStartTime() {
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_PATTERN, Locale.US);
        Date time = new Date();
        try {
            time = sdf.parse("1970-01-01 00:00:00");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return time;
    }

    public static Date getHoursBeforeTime(Date date, Integer hour) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.HOUR, hour);
        return c.getTime();
    }

    public static int monthBetween(String date1, String date2) throws ParseException {

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy/MM", Locale.US);

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        int result = 0;
        c1.setTime(sdFormat.parse(date1));

        c2.setTime(sdFormat.parse(date2));

        result = (c2.get(Calendar.YEAR) - c1.get(Calendar.YEAR)) * 12 + c2.get(Calendar.MONTH)
                - c1.get(Calendar.MONTH);

        return result == 0 ? 1 : Math.abs(result) + 1;
    }

    /**
     * 获取几天前的时间
     *
     * @param days s
     * @return s
     */
    public static Date getBeforeDayTime(int days) {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DAY_OF_YEAR, days);
        return c.getTime();
    }

    /**
     * 转换日期 将日期转为今天, 昨天, 前天, XXXX-XX-XX, ...
     *
     * @param time 时间
     * @return 当前日期转换为更容易理解的方式
     */
    public static String translateDate(Long time) {
        long oneDay = 24 * 60 * 60 * 1000;
        Calendar current = Calendar.getInstance();
        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        long todayStartTime = today.getTimeInMillis();

        if (time >= todayStartTime && time < todayStartTime + oneDay) { // today
            return "今天";
        } else if (time >= todayStartTime - oneDay && time < todayStartTime) { // yesterday
            return "昨天";
        } else if (time >= todayStartTime - oneDay * 2 && time < todayStartTime - oneDay) { // the day before yesterday
            return "前天";
        } else if (time > todayStartTime + oneDay) { // future
            return "将来某一天";
        } else {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.US);
            Date date = new Date(time);
            return dateFormat.format(date);
        }
    }

    /**
     * 转换日期 转换为更为人性化的时间
     *
     * @param time 时间
     * @return s
     */
    private String translateDate(long time, long curTime) {
        long oneDay = 24 * 60 * 60;
        Calendar today = Calendar.getInstance();    //今天
        today.setTimeInMillis(curTime * 1000);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        long todayStartTime = today.getTimeInMillis() / 1000;
        if (time >= todayStartTime) {
            long d = curTime - time;
            if (d <= 60) {
                return "1分钟前";
            } else if (d <= 60 * 60) {
                long m = d / 60;
                if (m <= 0) {
                    m = 1;
                }
                return m + "分钟前";
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("今天 HH:mm", Locale.US);
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            }
        } else {
            if (time < todayStartTime && time > todayStartTime - oneDay) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("昨天 HH:mm", Locale.US);
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {

                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            } else if (time < todayStartTime - oneDay && time > todayStartTime - 2 * oneDay) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("前天 HH:mm", Locale.US);
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            } else {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.US);
                Date date = new Date(time * 1000);
                String dateStr = dateFormat.format(date);
                if (!TextUtils.isEmpty(dateStr) && dateStr.contains(" 0")) {
                    dateStr = dateStr.replace(" 0", " ");
                }
                return dateStr;
            }
        }
    }

    /**
     * 秒数转换成时分秒
     *
     * @param time s
     * @return s
     */
    public static String secToTime(int time) {
        String timeStr = null;
        int hour = 0;
        int minute = 0;
        int second = 0;
        if (time <= 0)
            return "00:00";
        else {
            minute = time / 60;
            if (minute < 60) {
                second = time % 60;
                timeStr = unitFormat(minute) + ":" + unitFormat(second);
            } else {
                hour = minute / 60;
                if (hour > 99)
                    return "99:59:59";
                minute = minute % 60;
                second = time - hour * 3600 - minute * 60;
                timeStr = unitFormat(hour) + ":" + unitFormat(minute) + ":" + unitFormat(second);
            }
        }
        return timeStr;
    }

    public static String unitFormat(int i) {
        String retStr = null;
        if (i >= 0 && i < 10)
            retStr = "0" + Integer.toString(i);
        else
            retStr = "" + i;
        return retStr;
    }

    /**
     * turn 2016-07-26 to 2016-07-26 00:00:00
     *
     * @param date s
     * @return s
     */
    public static String dateToDateTime(String date) {
        if (StringUtil.isEmpty(date)) return null;
        if ("长期".equals(date)) return LONG_TERM_DATE;
        return date + " 00:00:00";
    }

    /**
     * turn 2016-07-26 00:00:00 to 2016-07-26
     *
     * @param dateTime s
     * @return s
     */
    public static String dateTimeToDate(String dateTime) {
        if (StringUtil.isEmpty(dateTime)) return "";
        if (LONG_TERM_DATE.equals(dateTime)) return "长期";
        try {
            return dateTime.split(" ")[0];
        } catch (NullPointerException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 按日期获取两个时间之间的天数间隔
     *
     * @param startDate s
     * @param endDate s
     * @return s
     */
    public static int daysInterval(Date startDate, Date endDate) {

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(startDate);
        long time1 = calendar.getTimeInMillis();

        calendar.setTime(endDate);
        long time2 = calendar.getTimeInMillis();

        long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

        return (int) betweenDays;

    }

    /**
     * 判断时间间隔是否大于4个月
     *
     * @param startDate s
     * @param endDate s
     * @return s
     */
    public static boolean isGreaterThanFourMonths(String startDate, String endDate) {
        if (startDate != null && endDate != null) {

            int mStartDateYear = Integer.valueOf(startDate.split("-")[0]);
            int mStartDateMonth = Integer.valueOf(startDate.split("-")[1]);
            int mStartDateDay = Integer.valueOf(startDate.split("-")[2]);

            int mEndDateYear = Integer.valueOf(endDate.split("-")[0]);
            int mEndDateMonth = Integer.valueOf(endDate.split("-")[1]);
            int mEndDateDay = Integer.valueOf(endDate.split("-")[2]);

            if (mStartDateYear == mEndDateYear) {
                return (mEndDateMonth - mStartDateMonth) > 4 || ((mEndDateMonth - mStartDateMonth) == 4 && mEndDateDay >= mStartDateDay);
            } else
                return (mEndDateYear > mStartDateYear && (((12 - mStartDateMonth) + mEndDateMonth) > 4) || (((12 - mStartDateMonth) + mEndDateMonth) == 4 && mEndDateDay >= mStartDateDay));
        } else {
            return false;
        }
    }

    /**
     * 判断目标时间是否早于指定的时间
     *
     * @param targetDate s
     * @param templateDate s
     * @return s
     */
    public static boolean isBefore(String targetDate, String templateDate) {
        if (!StringUtil.isEmpty(targetDate) && !StringUtil.isEmpty(templateDate)) {

            int mStartDateYear = Integer.valueOf(targetDate.split("-")[0]);
            int mStartDateMonth = Integer.valueOf(targetDate.split("-")[1]);
            int mStartDateDay = Integer.valueOf(targetDate.split("-")[2]);

            int mEndDateYear = Integer.valueOf(templateDate.split("-")[0]);
            int mEndDateMonth = Integer.valueOf(templateDate.split("-")[1]);
            int mEndDateDay = Integer.valueOf(templateDate.split("-")[2]);
            if (mStartDateYear == mEndDateYear) {
                if (mStartDateMonth == mEndDateMonth) {
                    return mStartDateDay <= mEndDateDay;
                } else {
                    return mStartDateMonth <= mEndDateMonth;
                }
            } else {
                return mStartDateYear <= mEndDateYear;
            }
        } else {
            return false;
        }
    }

    /**
     * 根据OCR扫描出的结果获取身份证有效期限
     *
     * @param scanResult s
     * @return s
     */
    public static String getScanIdCardExpireDate(String scanResult) {
        if (!StringUtil.isEmpty(scanResult)) {
            String lastExpireDate = scanResult.split("-")[1];
            if ("长期".equals(lastExpireDate)) {
                return "长期";
            } else {
                List<String> expireList = new ArrayList<>();
                for (int i = 0, m = lastExpireDate.split("\\.").length; i < m; i++) {
                    expireList.add(lastExpireDate.split("\\.")[i]);
                }
                StringBuilder sb = new StringBuilder();
                for (int i = 0, m = expireList.size(); i < m; i++) {
                    sb.append(expireList.get(i));
                    if (i != m - 1) {
                        sb.append("-");
                    }
                }
                return String.valueOf(sb);
            }
        }
        return "";
    }
}
