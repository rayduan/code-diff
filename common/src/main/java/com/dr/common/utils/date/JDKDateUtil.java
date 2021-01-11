package com.dr.common.utils.date;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;

/**
 * @description: jdk8以上的时间工具类
 * @author: yangdehong
 * @date: 2018/4/11 11:17
 */
public class JDKDateUtil {

    /**
     * LocalDateTime ----> DateUtil
     *
     * @param localDateTime
     * @return
     */
    public static Date localDateTimeToUdate(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     * LocalDate ----> DateUtil
     *
     * @param localDate
     * @return
     */
    public static Date localDateToUdate(LocalDate localDate) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDate.atStartOfDay().atZone(zone).toInstant();
        Date date = Date.from(instant);
        return date;
    }

    /**
     * DateUtil ----> LocalDateTime
     *
     * @param date
     * @return
     */
    public static LocalDateTime uDateToLocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        return localDateTime;
    }

    /**
     * DateUtil ----> LocalDate
     *
     * @param date
     * @return
     */
    public static LocalDate uDateToLocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zone = ZoneId.systemDefault();
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, zone);
        LocalDate localDate = localDateTime.toLocalDate();
        return localDate;
    }

    /**
     * 获取指定月份天数
     *
     * @param year
     * @param month
     * @return
     */
    public static int getYearOrMonthDays(int year, int month) {
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }

    public static int getYearOrMonthDays(int year, Month month) {// Month.AUGUST
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }

    /**
     * 指定年天数
     *
     * @param year
     * @return
     */
    public static int getYearOrMonthDays(int year) {
        return Year.now().length();
    }

    /**
     * 当前月 天数
     *
     * @return
     */
    public static int getCurrentMonthDays() {
        YearMonth currentYearMonth = YearMonth.now();
        return currentYearMonth.lengthOfMonth();
    }

    /**
     * 当前年 天数
     *
     * @return
     */
    public static int getCurrentYearDays() {
        YearMonth currentYearMonth = YearMonth.now();
        return currentYearMonth.lengthOfYear();
    }

    /**
     * 获取当前年、月、日
     *
     * @return
     */
    public static int getCurrentYear() {
        return YearMonth.now().getYear();
    }

    public static int getCurrentMonth() {
        return YearMonth.now().getMonthValue();
    }

    public static int getCurrentDay() {
        return MonthDay.now().getDayOfMonth();
    }

    /**
     * 当前时间
     *
     * @return
     */
    public static LocalDate currentDate() {
        return LocalDate.now();
    }

    public static LocalDateTime currentTime() {
        return LocalDateTime.now();
    }

    /**
     * 是否周末
     *
     * @return
     */
    public static boolean isWeekend() {
        boolean flag = false;
        DayOfWeek dayOfWeek = LocalDate.now().getDayOfWeek();
        if (dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY) {
            flag = true;
        }
        return flag;
    }

    /**
     * 每周的第一天为礼拜一而不是周日
     *
     * @return 每周第一天
     */
    public static Date getStartOfWeek() {
        LocalDate inputDate = LocalDate.now();
        TemporalAdjuster firstOfWeek = TemporalAdjusters.ofDateAdjuster(localDate
                -> localDate.minusDays(localDate.getDayOfWeek().getValue() - DayOfWeek.MONDAY.getValue()));
        inputDate.with(firstOfWeek);
        return localDate2Date(inputDate);
    }

    /**
     * 每个月第一天
     *
     * @return 每个月第一天
     */
    public static Date getStartOfMonth(Date date) {
        LocalDate inputDate = LocalDate.now();
        if (date != null) {
            inputDate = uDateToLocalDate(date);
        }
        inputDate = inputDate.minusDays(inputDate.getDayOfMonth() - 1);
        return localDate2Date(inputDate);
    }

    /**
     * 当前天之前的days天
     *
     * @param days 向前推移的天数
     * @return 计算后的天数
     */
    public static Date minusDays(Long days) {
        if (days == null) {
            return new Date();
        }
        LocalDate localDate = currentDate();
        localDate = localDate.minusDays(days);
        return localDate2Date(localDate);
    }

    /**
     * LocalDate 转换为 Date
     *
     * @param localDate LocalDate时间
     * @return Date格式时间
     */
    public static Date localDate2Date(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDate.atStartOfDay(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static Date getStartOfToday(String date) {
        LocalDate localDate;
        if (date != null) {
            localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            localDate = currentDate();
        }

        return localDate2Date(localDate);
    }

    public static Date getStartOfDate(String date) {
        LocalDate localDate;
        if (date != null) {
            localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        } else {
            localDate = currentDate();
        }

        return localDate2Date(localDate);
    }

    public static Date getEndOfToday(String date) {
        LocalDate localDate;
        if (date != null) {
            localDate = LocalDate.parse(date, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } else {
            localDate = currentDate();
        }
        LocalDateTime todayEnd = LocalDateTime.of(localDate, LocalTime.MAX);

        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = todayEnd.atZone(zoneId);

        return Date.from(zdt.toInstant());
    }

    public static Date addMinute(Date startTime, int minute) {
        if (startTime == null) {
            startTime = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    public static Date addDays(Date startTime, int days) {
        if (startTime == null) {
            startTime = new Date();
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startTime);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * @Description: 获取当前月第一天
     * @Author: WCJ
     * @Date: 2018/9/25
     */
    public static String mouthFirstDay() {
        Calendar cale = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        String firstday = format.format(cale.getTime());
        return firstday;
    }

    /**
     * @Description: 获取当前月最后一天
     * @Author: WCJ
     * @Date: 2018/9/25
     */
    public static String mouthLastDay() {
        Calendar cale = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        cale.add(Calendar.MONTH, 1);
        cale.set(Calendar.DAY_OF_MONTH, 0);
        String lastday = format.format(cale.getTime());
        return lastday;
    }

    public static void main(String[] args) {
//        LocalDate localDate = currentDate();
//        localDate = localDate.minusDays(11);
//        System.out.println(localDate);
//        System.out.println(getStartOfMonth());
//        System.out.println(mouthLastDay());
    }

}
