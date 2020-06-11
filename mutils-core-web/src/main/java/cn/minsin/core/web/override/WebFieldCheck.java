package cn.minsin.core.web.override;

import cn.minsin.core.annotation.NotNull;
import cn.minsin.core.exception.MutilsException;
import cn.minsin.core.override.FieldCheck;
import cn.minsin.core.tools.ModelUtil;
import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;
import java.util.Set;

/**
 * web提交数据检查
 *
 * @author: minton.zhang
 * @since: 0.0.8.RELEASE
 */
public interface WebFieldCheck<T> extends FieldCheck {

    String NOT_NULL_MESSAGE = "数据验证失败,%s不允许为空";


    /**
     * 数据字段检查 使用mutils-core中的{@link NotNull} 进行检查
     *
     * @return 异常则返回 {@link WebFieldCheck#returnValue(Throwable)} 正常返回null
     */
    default T verificationByNotNull() {
        Set<Field> allFields = ModelUtil.getAllFields(this);
        for (Field field : allFields) {
            NotNull annotation = field.getAnnotation(NotNull.class);
            if (annotation != null && annotation.notNull()) {
                try {
                    field.setAccessible(true);
                    Object object = field.get(this);
                    MutilsException.throwException(object == null, String.format(NOT_NULL_MESSAGE, annotation.value()));
                } catch (Throwable e) {
                    return this.returnValue(e);
                }
            }
        }
        return null;
    }

    /**
     * 数据字段检查 使用swagger2中的{@link ApiModelProperty} 进行检查
     * @return 异常则返回 {@link WebFieldCheck#returnValue(Throwable)} 正常返回null
     */
    default T verificationByApiModelProperty() {
        Set<Field> allFields = ModelUtil.getAllFields(this);
        for (Field field : allFields) {
            ApiModelProperty annotation = field.getAnnotation(ApiModelProperty.class);
            if (annotation != null) {
                try {
                    field.setAccessible(true);
                    Object object = field.get(this);
                    MutilsException.throwException(object == null, String.format(NOT_NULL_MESSAGE, annotation.value()));
                } catch (Throwable e) {
                    return this.returnValue(e);
                }
            }
        }
        return null;
    }

    /**
     * 自定义验证 需要手动重写
     *
     * @return 异常则返回 {@link WebFieldCheck#returnValue(Throwable)} 正常返回null
     */
    default T verificationByCustomize() {
        return null;
    }

    /**
     * 验证处理
     *
     * @param e 异常信息
     * @return 验证异常后返回的数据
     */
    T returnValue(Throwable e);

}
