package cn.minsin.core.web.controller_advice;

import cn.minsin.core.web.exception.BusinessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.HttpSessionRequiredException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentConversionNotSupportedException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.BindException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public abstract class BaseExceptionHandlerDispatcher<T> {

    private final Map<Class<?>, Function<Throwable, ResponseEntity<T>>> HANDLERS = new ConcurrentHashMap<>();
    private final Set<Class<?>> NOT_ERROR_CLASSES = new HashSet<>();


    {
        this.initHandler();
        NOT_ERROR_CLASSES.add(BusinessException.class);
        //NOT_ERROR_CLASSES.add(AuthorizationRefuseException.class);
        //NOT_ERROR_CLASSES.add(AuthorizationInvalidException.class);
        NOT_ERROR_CLASSES.add(MethodArgumentNotValidException.class);
        NOT_ERROR_CLASSES.add(MethodArgumentConversionNotSupportedException.class);
        NOT_ERROR_CLASSES.add(HttpRequestMethodNotSupportedException.class);
        NOT_ERROR_CLASSES.add(MethodArgumentTypeMismatchException.class);
        NOT_ERROR_CLASSES.add(BindException.class);
        NOT_ERROR_CLASSES.add(HttpMessageNotReadableException.class);
        //NOT_ERROR_CLASSES.add(RateAccessLimitException.class);
        NOT_ERROR_CLASSES.add(HttpMediaTypeException.class);
        NOT_ERROR_CLASSES.add(HttpMediaTypeNotAcceptableException.class);
        NOT_ERROR_CLASSES.add(HttpMediaTypeNotSupportedException.class);
        NOT_ERROR_CLASSES.add(HttpSessionRequiredException.class);

    }


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<T> exception(Throwable throwable, HttpServletRequest request, HttpServletResponse response) {
        this.log(throwable, !NOT_ERROR_CLASSES.contains(throwable.getClass()));

        List<Class<?>> allSuperClass = this.getAllSuperClass(throwable);
        //匹配最佳
        return HANDLERS.keySet()
                .stream()
                .filter(w -> w.isInstance(throwable))
                .min(Comparator.comparingInt(allSuperClass::indexOf))
                .map(w -> {
                    try {
                        return HANDLERS.get(w).apply(throwable);
                    } catch (Exception ex) {
                        return this.whenHandlerError(ex, true);
                    }
                })
                .orElse(this.whenHandlerError(throwable, false));
    }


    protected final void addHandler(Function<Throwable, ResponseEntity<T>> function, Class<?>... clazzArray) {

        for (Class<?> clazz : clazzArray) {
            //只添加异常的子类
            if (Throwable.class.isAssignableFrom(clazz)) {
                HANDLERS.put(clazz, function);
            }
        }
    }

    protected ResponseEntity<T> newInstance(T data, HttpStatus httpStatus) {
        return new ResponseEntity<>(data, httpStatus);
    }

    protected ResponseEntity<T> newInstance(T data) {
        return new ResponseEntity<>(data, HttpStatus.OK);
    }


    /**
     * 没有匹配器匹配成功
     */
    protected abstract ResponseEntity<T> whenHandlerError(Throwable e, boolean matchedButHasException);


    protected abstract void initHandler();

    protected abstract void addNotErrorClass(Class<? extends Throwable> clazz);

    protected abstract void addNotErrorClasses(Set<Class<? extends Throwable>> clazz);


    protected abstract void log(Throwable e, boolean error);


    protected List<Class<?>> getAllSuperClass(Throwable throwable) {
        List<Class<?>> list = new ArrayList<>(5);

        Class<?> superClass = throwable.getClass();
        while (true) {
            list.add(superClass);
            if (superClass.getSuperclass().equals(Throwable.class)) {
                break;
            }
            superClass = superClass.getSuperclass();
        }
        return list;
    }
}
