package cn.minsin.core.web.config;

import cn.minsin.core.web.result.Result;
import org.slf4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;


/**
 * 被使用者只需要继承该接口,添加该注解{@link ControllerAdvice}
 *
 * @author minton.zhang
 * @since 2020/10/18 00:25
 */
public interface DefaultExceptionHandler extends ExceptionHandlerResult<Result<String>> {

	Logger log = org.slf4j.LoggerFactory.getLogger(ExceptionHandlerResult.class);

	@Override
	default Result<String> createResultWithException(Exception e, String message) {
		log.error("", e);
		return Result.fail(message);
	}

}
