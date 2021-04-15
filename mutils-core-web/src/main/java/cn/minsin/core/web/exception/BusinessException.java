package cn.minsin.core.web.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author minton.zhang
 * @since 2020/6/11 14:28
 */
@Getter
@Setter
public class BusinessException extends RuntimeException {

	/**
	 * 业务员错误码
	 */
	private int code = 10000;

	public BusinessException() {
	}

	public BusinessException(String message) {
		super(message);
	}

	public BusinessException(String message, Throwable cause) {
		super(message, cause);
	}

	public BusinessException(String message, int code) {
		super(message);
		this.code = code;
	}

	public BusinessException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}

	public BusinessException(Throwable cause) {
		super(cause);
	}

	public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}
}
