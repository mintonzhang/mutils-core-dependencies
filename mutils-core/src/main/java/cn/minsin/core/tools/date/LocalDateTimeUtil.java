package cn.minsin.core.tools.date;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author minton.zhang
 * @since 2021/3/2 14:31
 */
public class LocalDateTimeUtil {

    private final static ZoneOffset TIME_ZONE = OffsetDateTime.now(ZoneId.systemDefault()).getOffset();

    private final static DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static String format() {
        return LocalDateTime.now().format(DEFAULT_FORMATTER);
    }

    public static String format(DateTimeFormatter dateTimeFormatter) {
        return LocalDateTime.now().format(dateTimeFormatter);
    }

    public static String format(LocalDateTime localDateTime, DateTimeFormatter dateTimeFormatter) {
        return localDateTime.format(dateTimeFormatter);
    }

    public static String format2String(LocalDateTime localDateTime, String dateTimeFormatter) {
        return localDateTime.format(DateTimeFormatter.ofPattern(dateTimeFormatter));
    }

    public static String format2String(String dateTimeFormatter) {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(dateTimeFormatter));
    }


    public static String format2String(LocalDateTime localDateTime) {
        return localDateTime.format(DEFAULT_FORMATTER);
    }

    public static LocalDateTime format(Date date) {
        return date.toInstant().atOffset(TIME_ZONE).toLocalDateTime();
    }

    public static LocalDateTime format(String dateTimeStr, DateTimeFormatter dateTimeFormatter) {
        return LocalDateTime.parse(dateTimeStr, dateTimeFormatter);
    }

    public static LocalDateTime format(Long timestamp) {
        return Instant.ofEpochMilli(timestamp).atZone(TIME_ZONE).toLocalDateTime();
    }

    public static LocalDateTime format(String dateTimeStr) {
        return LocalDateTime.parse(dateTimeStr, DEFAULT_FORMATTER);
    }


    //将java8 的 java.time.LocalDateTime 转换为 java.util.Date，默认时区为东8区
    public static Date format2Date(LocalDateTime localDateTime) {
        return Date.from(localDateTime.toInstant(TIME_ZONE));
    }

    /**
     * 获取毫秒数
     */
    public static long format2long(LocalDateTime localDateTime) {
        return localDateTime.toInstant(TIME_ZONE).toEpochMilli();
    }


    /**
     * 转换成一天的开始时间 eg:00:00:00
     */
    public static LocalDateTime format2StartOfDay(LocalDateTime localDateTime) {
        return localDateTime.withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);
    }

    /**
     * 转换成一天的结束时间 eg:23.59.59
     */
    public static LocalDateTime format2EndOfDay(LocalDateTime localDateTime) {
        return localDateTime.withHour(23)
                .withMinute(59)
                .withSecond(59)
                .withNano(999999999);
    }

}
