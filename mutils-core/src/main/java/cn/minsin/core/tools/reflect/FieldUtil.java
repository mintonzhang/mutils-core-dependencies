package cn.minsin.core.tools.reflect;

import cn.minsin.core.tools.function.Consumer3;
import cn.minsin.core.tools.log.GlobalDefaultLogger;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * @author minsin/mintonzhang@163.com
 * @since 2022/7/18
 */
public class FieldUtil extends FieldUtils {

    /**
     * 通过对象获取带有某个注解的值
     *
     * @param dataModel  对象
     * @param annotation 注解
     * @param consumer   消费函数
     * @param <A>        注解类型
     */
    public static <A extends Annotation> void getOneFieldValueWithAnnotation(Object dataModel,
                                                                             Class<A> annotation,
                                                                             String fieldName,
                                                                             Consumer3<A, Object, Boolean> consumer
    ) {
        Field[] fieldsWithAnnotation = FieldUtils.getFieldsWithAnnotation(dataModel.getClass(), annotation);

        for (Field allField : fieldsWithAnnotation) {
            boolean equals = allField.getName().equals(fieldName);
            if (!equals) {
                continue;
            }
            try {
                A targetAnnotation = allField.getDeclaredAnnotation(annotation);
                Object value = FieldUtils.readField(dataModel, allField.getName(), true);
                consumer.accept(targetAnnotation, value, value == null);
            } catch (Exception e) {
                GlobalDefaultLogger.log.error("获取Field:[{}]的值失败", allField.getName());
            }
        }
    }

    /**
     * 通过对象获取带有某个注解的值
     *
     * @param dataModel  对象
     * @param annotation 注解
     * @param consumer   消费函数
     * @param <A>        注解类型
     */
    public static <A extends Annotation> void getFieldValueWithAnnotation(Object dataModel,
                                                                          Class<A> annotation,
                                                                          Consumer3<A, Object, Boolean> consumer
    ) {
        Field[] fieldsWithAnnotation = FieldUtils.getFieldsWithAnnotation(dataModel.getClass(), annotation);

        for (Field allField : fieldsWithAnnotation) {
            try {
                A targetAnnotation = allField.getDeclaredAnnotation(annotation);
                Object value = FieldUtils.readField(dataModel, allField.getName(), true);
                consumer.accept(targetAnnotation, value, value == null);
            } catch (Exception e) {
                GlobalDefaultLogger.log.error("获取Field:[{}]的值失败", allField.getName());
            }
        }
    }
}
