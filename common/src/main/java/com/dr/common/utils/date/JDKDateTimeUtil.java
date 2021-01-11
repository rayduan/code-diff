package com.dr.common.utils.date;

import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zb Created in 5:29 PM 2018/10/29
 */
public class JDKDateTimeUtil {


    public static LocalDateTime date2LocalDateTime(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDateTime();
    }

    public static LocalDate date2LocalDate(Date date) {
        Instant instant = date.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return instant.atZone(zoneId).toLocalDate();
    }

    public static String localDateTime2String(LocalDateTime localDateTime) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTimeFormatter.format(localDateTime);
    }

    public static Date localDateTime2Date(LocalDateTime localDateTime) {
        ZoneId zoneId = ZoneId.systemDefault();
        ZonedDateTime zdt = localDateTime.atZone(zoneId);
        return Date.from(zdt.toInstant());
    }

    public static Date plusMonth(Date date, int month) {
        return localDateTime2Date(date2LocalDateTime(date).plusMonths(month));
    }

    public static Date plusDays(Date date, int day) {
        return localDateTime2Date(date2LocalDateTime(date).plusDays(day));
    }

    public static Date plusHours(Date date, int hour) {
        return localDateTime2Date(date2LocalDateTime(date).plusHours(hour));
    }

    public static Date plusMinutes(Date date, int min) {
        return localDateTime2Date(date2LocalDateTime(date).plusMinutes(min));
    }

    public static Date plusSeconds(Date date, int sec) {
        return localDateTime2Date(date2LocalDateTime(date).plusSeconds(sec));
    }


    public static String getStartOfDateStr(int day) {
        Date date =localDateTime2Date(date2LocalDateTime(new Date()).plusDays(day));
        SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return temp.format(startOfToday(date));
    }

    public static String getEndOfDateStr(int day) {
        Date date =localDateTime2Date(date2LocalDateTime(new Date()).plusDays(day));
        SimpleDateFormat temp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return temp.format(endOfToday(date));
    }

    public static Date offsetDays(int days) {
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR) + days);

        return cal.getTime();
    }

    public static Date startOfToday(Date date) {
        LocalDateTime todayStart = LocalDateTime.of(date2LocalDate(date), LocalTime.MIN);

        return localDateTime2Date(todayStart);
    }

    public static Date endOfToday(Date date) {
        LocalDateTime end = LocalDateTime.of(date2LocalDate(date), LocalTime.MAX);

        return localDateTime2Date(end);
    }


    public static void main(String[] args) {
        System.out.println(offsetDays(-1));


        System.out.println(date2LocalDateTime(new Date()));
        System.out.println(localDateTime2String(date2LocalDateTime(new Date())));
        System.out.println(localDateTime2Date(date2LocalDateTime(new Date())));
        System.out.println(plusDays(new Date(), -30));
        System.out.println(startOfToday(new Date()));
        System.out.println(endOfToday(new Date()));
        System.out.println(getStartOfDateStr(-7));
    }
}
