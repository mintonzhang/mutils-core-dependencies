package cn.minsin.core.tools.date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

/**
 * 不同时间类型之间的转换
 * {@link DateFormat} 函数式接口,以下使用到的地方均可使用
 * {@link DefaultDateFormat } 默认时间格式化枚举
 *
 * @author mintonzhang@163.com
 * @date 2018年6月8日
 */
public class DateUtil extends DateUtils {
    private static final Calendar calendar = Calendar.getInstance();

    protected DateUtil() {
        //Allow subClass
    }

    public static String date2String(Date date) {
        return date2String(date, DefaultDateFormat.yyyy_MM_dd_HH_mm_ss);
    }

    public static String date2String(Date date, DateFormat format) {
        return DateFormatUtils.format(date, format.getFormat());
    }

    public static String date2String(Date date, DateFormat format, String defaultValue) {
        try {
            return date2String(date, format);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static String long2DateStr(long source) {
        return long2DateStr(source, DefaultDateFormat.yyyy_MM_dd_HH_mm_ss);
    }

    public static String long2DateStr(long source, DateFormat format) {
        return DateFormatUtils.format(new Date(source), format.getFormat());
    }

    public static String long2DateStr(long source, DateFormat format, String defaultValue) {
        try {
            return long2DateStr(source, format);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    public static Date long2Date(long source) {
        return new Date(source);
    }

    /**
     * 根据日期字符串格式化为指定的日期
     *
     * @param source 时间字符串
     * @param format 字符串格式
     */
    public static Date string2Date(String source, DateFormat format) {
        try {
            return DateUtils.parseDate(source, format.getFormat());
        } catch (ParseException e) {
            return null;
        }
    }

    /**
     * 获取一天的开始时间
     *
     * @param date
     */
    public static Date getBeginOfDay(Date date) {
        if (date == null) {
            return null;
        }
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取一天的结束
     *
     * @param date
     */
    public static Date getEndOfDay(Date date) {
        if (date == null) {
            return null;
        }
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 获取一天的开始时间
     *
     * @param dateStr
     */
    public static Date getBeginOfDay(String dateStr, DateFormat dateEnum) {
        Date date = string2Date(dateStr, dateEnum);
        if (date == null) {
            return null;
        }
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获取一天的结束 可使用lamuda表达式 eg: getEndOfDay(new Date(),()->"yyyy-MM-dd");
     *
     * @param dateStr
     */
    public static Date getEndOfDay(String dateStr, DateFormat dateEnum) {
        Date date = string2Date(dateStr, dateEnum);
        if (date == null) {
            return null;
        }
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 创建日期
     *
     * @param year
     * @param month
     * @param dayOfMonth
     */
    public static Date createDate(int year, int month, int dayOfMonth) {
        LocalDate of = LocalDate.of(year, month, dayOfMonth);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = of.atStartOfDay().atZone(zone).toInstant();
        return Date.from(instant);
    }
}
