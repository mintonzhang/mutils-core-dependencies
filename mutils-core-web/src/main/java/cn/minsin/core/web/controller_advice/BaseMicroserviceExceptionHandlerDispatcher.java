package cn.minsin.core.web.controller_advice;

import cn.minsin.core.tools.FormatStringUtil;
import cn.minsin.core.web.exception.BusinessException;
import cn.minsin.core.web.result.DefaultResultOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import javax.validation.UnexpectedTypeException;
import javax.validation.ValidationException;

public abstract class BaseMicroserviceExceptionHandlerDispatcher extends BaseExceptionHandlerDispatcher<String> {

    @Override
    protected ResponseEntity<String> whenHandlerError(Throwable e, boolean matchedButHasException) {
        return super.newInstance(DefaultResultOptions.ERROR.getMsg(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    protected void addExceptionHandler(BaseExceptionHandlerDispatcher<String>.ExceptionHandlerRegistrar registrar) {
        super.addExceptionHandler(registrar);
        registrar.create((e -> this.expectationFailed(e.getMessage()))).apply(BusinessException.class);

        registrar.create(e -> {
            HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) e;
            return this.expectationFailed(FormatStringUtil.format("该接口不支持'{}'的请求方式,仅支持:'{}'", exception.getMethod(), exception.getSupportedMethods()));
        }).apply(HttpRequestMethodNotSupportedException.class);

        registrar.create(e -> {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;

            String field = exception.getBindingResult().getFieldError().getField();
            return this.expectationFailed(String.format("参数'%s'校验失败,请检查后重试", field));
        }).apply(MethodArgumentNotValidException.class);
        registrar.create(e -> this.expectationFailed("RequestBody缺少、参数类型有误或枚举错误")).apply(HttpMessageNotReadableException.class);
        registrar.create(e -> this.expectationFailed("请求参数与文档的不一致,导致转换异常")).apply(HttpMessageConversionException.class);
        registrar.create(e -> this.expectationFailed(e.getMessage())).apply(HttpMediaTypeNotSupportedException.class);
        registrar.create(e -> this.expectationFailed("'" + ((MethodArgumentTypeMismatchException) e).getName() + "'类型有误,请检查后重试"))
                .apply(MethodArgumentTypeMismatchException.class);
        registrar.create(e -> this.expectationFailed("缺少必传参数")).apply(MissingServletRequestPartException.class);
        registrar.create(e -> this.expectationFailed("参数类型处理异常")).apply(UnexpectedTypeException.class);
        registrar.create(e -> this.expectationFailed(e.getMessage())).apply(ValidationException.class);
        registrar.create(e -> this.expectationFailed(String.format("参数'%s',类型异常", ((MethodArgumentConversionNotSupportedException) e).getName())))
                .apply(MethodArgumentConversionNotSupportedException.class);
    }

    @Override
    protected void addNotExceptionClasses(BaseExceptionHandlerDispatcher<String>.NotExceptionClassRegistrar registrar) {
        super.addNotExceptionClasses(registrar);
    }


    protected ResponseEntity<String> expectationFailed(String data) {
        return new ResponseEntity<>(data, HttpStatus.EXPECTATION_FAILED);
    }
}
