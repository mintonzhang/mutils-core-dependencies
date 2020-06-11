package cn.minsin.core.tools;

import lombok.NonNull;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Predicate;

/**
 * @author: minton.zhang
 * @since: 2020/6/11 14:45
 */
public class ReflectUtil {

    private static Predicate<Field> fieldPredicate = (Field field) -> {
        int modifiers = field.getModifiers();
        //必须是private 不是static 不是final
        return Modifier.isPrivate(modifiers) && !Modifier.isStatic(modifiers) && !Modifier.isFinal(modifiers);
    };

    /**
     * 创建实例 必须要有无参构造方法
     *
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T newInstance(Class<T> type) {
        try {
            Constructor<T> declaredConstructor = type.getDeclaredConstructor();
            return declaredConstructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建实例
     */
    public static <T> T newInstance(Class<T> type, Class<?>[] parameterTypes, Object[] args) {
        try {
            return getConstructor(type, parameterTypes).newInstance(args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    //********************************分割线****************************************//
    //获取字段

    /**
     * 获取类的构造器
     *
     * @param type           class
     * @param parameterTypes 参数类型
     * @param <T>            具体class类型
     * @return 类构造器
     */
    public static <T> Constructor<T> getConstructor(Class<T> type, Class<?>[] parameterTypes) {
        try {
            Constructor<T> constructor = type.getDeclaredConstructor(parameterTypes);
            constructor.setAccessible(true);
            return constructor;
        } catch (NoSuchMethodException e) {
            throw new UnsupportedOperationException(e);
        }
    }

    /**
     * 获取所有字段 包括父类
     *
     * @param model
     */
    public static Set<Field> getFields(Class<?> model, boolean callSuper) {
        Set<Field> fieldSet = new HashSet<>(10);
        if (callSuper) {
            //第一个superClass
            Class<?> superClass = model.getSuperclass();

            //如果父类不是Object才进行添加
            if (superClass != null && !superClass.equals(Object.class)) {
                while (superClass != null) {
                    Field[] fields = superClass.getDeclaredFields();
                    checkField(fieldSet, fields);
                    superClass = superClass.getSuperclass();
                }
            }
        }
        checkField(fieldSet, model.getDeclaredFields());
        return fieldSet;
    }

    protected static void checkField(Collection<Field> collection, Field[] fields) {
        for (Field field : fields) {
            boolean test = fieldPredicate.test(field);
            if (test) {
                collection.add(field);
            }
        }
    }

    /**
     * 获取字段值
     *
     * @param field
     * @param model
     * @return
     */
    public static Object getFieldValue(@NonNull Field field, @NonNull Object model) {
        Set<Field> fields = getFields(model.getClass(), true);
        if (fields.contains(field)) {
            field.setAccessible(true);
            try {
                return field.get(model);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取字段值
     *
     * @param field
     * @param model
     * @return
     */
    public static Optional<Object> getFieldValueOptional(@NonNull Field field, @NonNull Object model) {
        Object fieldValue = getFieldValue(field, model);
        return Optional.ofNullable(fieldValue);
    }

    /**
     * 将对象转换成Treemap
     *
     * @param model
     * @return
     */
    public static void getFieldValue(Object model, BiConsumer<String, Object> biConsumer) {
        Set<Field> fields = getFields(model.getClass(), true);
        for (Field field : fields) {
            Optional<Object> fieldValue = getFieldValueOptional(field, model);
            fieldValue.ifPresent(e -> biConsumer.accept(field.getName(), e));
        }
    }
}
