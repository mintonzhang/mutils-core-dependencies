package cn.minsin.core.web.controller_advice;

import cn.minsin.core.tools.FormatStringUtil;
import cn.minsin.core.web.exception.BusinessException;
import cn.minsin.core.web.result.DefaultResultOptions;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
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
    protected void initHandler() {

        super.addHandler(e -> this.expectationFailed(e.getMessage()), BusinessException.class);
        super.addHandler(
                e -> {
                    HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) e;
                    return this.expectationFailed(FormatStringUtil.format("该接口不支持'{}'的请求方式,仅支持:'{}'", exception.getMethod(), exception.getSupportedMethods()));
                }, HttpRequestMethodNotSupportedException.class
        );
        super.addHandler(
                e -> {
                    MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;

                    String field = exception.getBindingResult().getFieldError().getField();
                    return this.expectationFailed(String.format("参数'%s'校验失败,请检查后重试", field));
                }, MethodArgumentNotValidException.class
        );
        super.addHandler(
                e -> {
                    BindException exception = (BindException) e;

                    String field = exception.getBindingResult().getFieldError().getField();
                    String errorMsg = String.format("参数'%s'校验失败,请检查后重试", field);
                    return this.expectationFailed(errorMsg);
                }, BindException.class
        );

        super.addHandler(e -> this.expectationFailed("RequestBody缺少、参数类型有误或枚举错误"), HttpMessageNotReadableException.class);
        super.addHandler(e -> this.expectationFailed("请求参数与文档的不一致,导致转换异常"), HttpMessageConversionException.class);
        super.addHandler(e -> this.expectationFailed(e.getMessage()), HttpMediaTypeNotSupportedException.class);
        super.addHandler(e -> this.expectationFailed("'" + ((MethodArgumentTypeMismatchException) e).getName() + "'类型有误,请检查后重试"), MethodArgumentTypeMismatchException.class);
        super.addHandler(e -> this.expectationFailed("缺少必传参数"), MissingServletRequestPartException.class);
        super.addHandler(e -> this.expectationFailed("参数类型处理异常"), UnexpectedTypeException.class);
        super.addHandler(e -> this.expectationFailed(e.getMessage()), ValidationException.class);
        super.addHandler(
                e -> this.expectationFailed(String.format("参数'%s',类型异常", ((MethodArgumentConversionNotSupportedException) e).getName()))
                , MethodArgumentConversionNotSupportedException.class);
    }

    protected ResponseEntity<String> expectationFailed(String data) {
        return new ResponseEntity<>(data, HttpStatus.EXPECTATION_FAILED);
    }
}
