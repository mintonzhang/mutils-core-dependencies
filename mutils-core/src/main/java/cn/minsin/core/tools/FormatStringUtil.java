package cn.minsin.core.tools;

import lombok.NonNull;
import org.slf4j.helpers.MessageFormatter;

/**
 * @author minton.zhang
 * @since 2021/3/10 10:15
 */
public interface FormatStringUtil {

	static String format(@NonNull String message, Object... param) {
		if (param.length == 0) {
			return message;
		} else {
			return MessageFormatter.arrayFormat(message, param).getMessage();
		}
	}
}
