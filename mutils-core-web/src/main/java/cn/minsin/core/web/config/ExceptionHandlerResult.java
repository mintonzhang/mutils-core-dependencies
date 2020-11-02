package cn.minsin.core.web.config;

import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.UnexpectedTypeException;

/**
 * @author: minton.zhang
 * @since: 2020/10/19 23:08
 */

public interface ExceptionHandlerResult<T> {

	/**
	 * 参数不符合规范异常
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(UnexpectedTypeException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	default T unexpectedTypeExceptionHandler(UnexpectedTypeException e) {
		//自定义处理
		return this.createResultWithException(e, ("参数类型处理异常"));
	}

	/**
	 * 参数异常
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler({MethodArgumentNotValidException.class, BindException.class})
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	default T methodArgumentNotValidException(MethodArgumentNotValidException e) {
		String errorMsg;
		String field = e.getBindingResult().getFieldError().getField();
		errorMsg = String.format("'%s'校验失败", field);
		return this.createResultWithException(e, (errorMsg));
	}

	/**
	 * 默认处理所有异常
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	default T exception(Exception e) {
		return this.createResultWithException(e, "未知异常");
	}

	T createResultWithException(Exception e, String message);

}
