package cn.minsin.core.web.controller_advice;

import cn.minsin.core.tools.FormatStringUtil;
import cn.minsin.core.web.exception.BusinessException;
import cn.minsin.core.web.result.Result;
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


public abstract class BaseMvcExceptionHandlerDispatcher extends BaseExceptionHandlerDispatcher<Result<Object>> {

    @Override
    protected ResponseEntity<Result<Object>> whenHandlerError(Throwable e, boolean matchedButHasException) {
        return super.newInstance(Result.error());
    }

    @Override
    protected void addExceptionHandler(BaseExceptionHandlerDispatcher<Result<Object>>.ExceptionHandlerRegistrar registrar) {
        super.addExceptionHandler(registrar);

        registrar.create(e -> ResponseEntity.ok(Result.fail(e.getMessage()))).apply(BusinessException.class);
        registrar.create(e -> {
            HttpRequestMethodNotSupportedException exception = (HttpRequestMethodNotSupportedException) e;
            String format = FormatStringUtil.format("该接口不支持'{}'的请求方式,仅支持:'{}'", exception.getMethod(), exception.getSupportedMethods());
            return ResponseEntity.ok(Result.fail(format));
        }).apply(HttpRequestMethodNotSupportedException.class);
        registrar.create(e -> {
            MethodArgumentNotValidException exception = (MethodArgumentNotValidException) e;
            String field = exception.getBindingResult().getFieldError().getField();
            String errorMsg = String.format("参数'%s'校验失败,请检查后重试", field);
            return ResponseEntity.ok(Result.fail(errorMsg));
        }).apply(MethodArgumentNotValidException.class);
        registrar.create(e -> {
            BindException exception = (BindException) e;
            String field = exception.getBindingResult().getFieldError().getField();
            String errorMsg = String.format("参数'%s'校验失败,请检查后重试", field);
            return ResponseEntity.ok(Result.fail(errorMsg));
        }).apply(BindException.class);

        registrar.create(e -> ResponseEntity.ok(Result.fail("RequestBody缺少、参数类型有误或枚举错误"))).apply(HttpMessageNotReadableException.class);
        registrar.create(e -> ResponseEntity.ok(Result.fail("请求参数与文档的不一致,导致转换异常"))).apply(HttpMessageConversionException.class);
        registrar.create(e -> ResponseEntity.ok(Result.fail(e.getMessage()))).apply(HttpMediaTypeNotSupportedException.class);
        registrar.create(e -> ResponseEntity.ok(Result.fail("'" + ((MethodArgumentTypeMismatchException) e).getName() + "'类型有误,请检查后重试"))).apply(MethodArgumentTypeMismatchException.class);
        registrar.create(e -> ResponseEntity.ok(Result.fail("缺少必传参数"))).apply(MissingServletRequestPartException.class);
        registrar.create(e -> ResponseEntity.ok(Result.fail("参数类型处理异常"))).apply(UnexpectedTypeException.class);
        registrar.create(e -> ResponseEntity.ok(Result.fail(e.getMessage()))).apply(ValidationException.class);
        registrar.create(e -> ResponseEntity.ok(Result.fail(String.format("参数'%s',类型异常", ((MethodArgumentConversionNotSupportedException) e).getName()))))
                .apply(MethodArgumentConversionNotSupportedException.class);
    }


}
