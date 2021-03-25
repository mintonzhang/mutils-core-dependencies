package cn.minsin.core.tools.date;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author minton.zhang
 * @since 2021/3/2 14:31
 */
public class LocalDateUtil {

	private final static ZoneOffset TIME_ZONE = OffsetDateTime.now(ZoneId.systemDefault()).getOffset();

	private final static DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");


	public static String format() {
		return LocalDate.now().format(DEFAULT_FORMATTER);
	}

	public static String format(DateTimeFormatter dateTimeFormatter) {
		return LocalDate.now().format(dateTimeFormatter);
	}

	public static String format(LocalDate localDateTime, String dateTimeFormatter) {
		return localDateTime.format(DateTimeFormatter.ofPattern(dateTimeFormatter));
	}

	public static String format(String dateTimeFormatter) {
		return LocalDate.now().format(DateTimeFormatter.ofPattern(dateTimeFormatter));
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
		return date.toInstant().atZone(TIME_ZONE).toLocalDate();
	}

	public static Date format2Date(LocalDate localDate) {
		if (null == localDate) {
			return null;
		}
		ZonedDateTime zonedDateTime = localDate.atStartOfDay(TIME_ZONE);
		return Date.from(zonedDateTime.toInstant());
	}

	public static long format2Long(LocalDate localDate) {
		return localDate.atStartOfDay(TIME_ZONE).toInstant().toEpochMilli();

	}


}
