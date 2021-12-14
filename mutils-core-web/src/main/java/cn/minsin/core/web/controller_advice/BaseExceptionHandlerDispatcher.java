package cn.minsin.core.web.controller_advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public abstract class BaseExceptionHandlerDispatcher<T> {

    private final Map<Class<?>, Function<Throwable, ResponseEntity<T>>> HANDLERS = new ConcurrentHashMap<>();


    {
        this.initHandler();
    }


    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public ResponseEntity<T> exception(Throwable throwable, HttpServletRequest request, HttpServletResponse response) {
        this.log(throwable);

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


    @SafeVarargs
    protected final void addHandler(Function<Throwable, ResponseEntity<T>> function, Class<? extends Throwable>... clazzArray) {

        for (Class<? extends Throwable> clazz : clazzArray) {
            HANDLERS.put(clazz, function);
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


    protected abstract void log(Throwable e);


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
