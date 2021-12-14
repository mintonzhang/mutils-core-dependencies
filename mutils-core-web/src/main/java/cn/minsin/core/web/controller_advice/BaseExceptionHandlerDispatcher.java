package cn.minsin.core.web.controller_advice;

import cn.minsin.core.tools.function.FunctionalInterfaceUtil;
import cn.minsin.core.tools.log.v3.GlobalDefaultLogger;
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
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.function.Function;

public abstract class BaseExceptionHandlerDispatcher<T> implements GlobalDefaultLogger {

    private final Map<Class<?>, Function<Throwable, ResponseEntity<T>>> HANDLERS = new ConcurrentHashMap<>();
    private final Set<Class<?>> NOT_ERROR_CLASSES = new CopyOnWriteArraySet<>();


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<T> exception(Throwable throwable, HttpServletRequest request, HttpServletResponse response) {

        boolean flag = FunctionalInterfaceUtil.containsAny(NOT_ERROR_CLASSES, w -> w.isInstance(throwable));
        this.log(throwable, !flag);

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

    /**
     * 注册异常处理器
     *
     * @param registrar 注册器
     */
    protected void addExceptionHandler(ExceptionHandlerRegistrar registrar) {

    }

    /**
     * 注册非异常的exception
     *
     * @param registrar 注册器
     */
    protected void addNotExceptionClasses(NotExceptionClassRegistrar registrar) {
        registrar.apply(BusinessException.class);
        //NOT_ERROR_CLASSES.add(AuthorizationRefuseException.class);
        //NOT_ERROR_CLASSES.add(AuthorizationInvalidException.class);
        registrar.apply(MethodArgumentNotValidException.class);
        registrar.apply(MethodArgumentConversionNotSupportedException.class);
        registrar.apply(HttpRequestMethodNotSupportedException.class);
        registrar.apply(MethodArgumentTypeMismatchException.class);
        registrar.apply(BindException.class);
        registrar.apply(HttpMessageNotReadableException.class);
        //registrar.apply(RateAccessLimitException.class);
        registrar.apply(HttpMediaTypeException.class);
        registrar.apply(HttpMediaTypeNotAcceptableException.class);
        registrar.apply(HttpMediaTypeNotSupportedException.class);
        registrar.apply(HttpSessionRequiredException.class);
    }


    protected abstract Set<Class<? extends Throwable>> initNotErrorClasses();


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


    protected class ExceptionHandlerRegistrar {
        private Function<Throwable, ResponseEntity<T>> function;

        public ExceptionHandlerRegistrar create(Function<Throwable, ResponseEntity<T>> function) {
            this.function = function;
            return this;
        }

        /**
         * 添加到处理器中
         */
        public void apply(Class<?>... clazz) {
            for (Class<?> aClass : clazz) {
                if (Throwable.class.isAssignableFrom(aClass)) {
                    HANDLERS.put(aClass, function);
                }
            }
        }
    }


    protected class NotExceptionClassRegistrar {

        /**
         * 添加到处理器中
         */
        public void apply(Class<?>... clazz) {
            for (Class<?> aClass : clazz) {
                if (Throwable.class.isAssignableFrom(aClass)) {
                    NOT_ERROR_CLASSES.add(aClass);
                }
            }
        }
    }
}
