package cn.minsin.core.web.config;

import cn.minsin.core.web.result.Result;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.UnexpectedTypeException;



/**
 *
 * 被使用者只需要继承该接口,添加该注解{@link ControllerAdvice}
 * @author: minton.zhang
 * @since: 2020/10/18 00:25
 */
public interface DefaultExceptionHandler {


	/**
	 * 参数不符合规范异常
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(value = UnexpectedTypeException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	default Object unexpectedTypeExceptionHandler(UnexpectedTypeException e) {
		//自定义处理
		return Result.error("参数类型处理异常");
	}

	/**
	 * 参数异常
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	default Object methodArgumentNotValidException(MethodArgumentNotValidException e) {
		String errorMsg;
		String field = e.getBindingResult().getFieldError().getField();
		errorMsg = String.format("'%s'校验失败", field);
		return Result.error(errorMsg);
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	default Object exception(Exception e) {
		return Result.error("未知异常");
	}
}
