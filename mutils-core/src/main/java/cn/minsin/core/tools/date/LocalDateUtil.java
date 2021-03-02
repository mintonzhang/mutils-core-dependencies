package cn.minsin.core.tools.date;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author minton.zhang
 * @since 2021/3/2 14:31
 */
public class LocalDateUtil {

	private final static DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


	public static String format() {
		return LocalDate.now().format(DEFAULT_FORMATTER);
	}

	public static String format(DateTimeFormatter dateTimeFormatter) {
		return LocalDate.now().format(dateTimeFormatter);
	}

	public static String format(LocalDate localDateTime, DateTimeFormatter dateTimeFormatter) {
		return localDateTime.format(dateTimeFormatter);
	}

	public static String format(LocalDate localDateTime) {
		return localDateTime.format(DEFAULT_FORMATTER);
	}


	public static LocalDate format(Date date) {
		if (null == date) {
			return null;
		}
		return date.toInstant().atZone(LocalDateTimeUtil.TIME_ZONE).toLocalDate();
	}

	public static Date format2Date(LocalDate localDate) {
		if (null == localDate) {
			return null;
		}
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(LocalDateTimeUtil.TIME_ZONE);
		return Date.from(zonedDateTime.toInstant());
	}

	public static long format2Long(LocalDate localDate) {
		return localDate.atStartOfDay(LocalDateTimeUtil.TIME_ZONE).toInstant().toEpochMilli();

	}


}
