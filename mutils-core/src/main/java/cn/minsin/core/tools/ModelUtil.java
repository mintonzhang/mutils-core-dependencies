package cn.minsin.core.tools;

import cn.minsin.core.annotation.Ignore;
import cn.minsin.core.annotation.NotNull;
import cn.minsin.core.exception.MutilsException;
import lombok.Getter;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * mutils中对实体类的一个转换工具类
 * 需要配合 {@link NotNull} 注解使用
 *
 * @author mintonzhang
 * @date 2019年2月18日
 * @since 0.3.4
 */
public class ModelUtil {
    private static final String ERROR_MESSAGE_TEMPLATE = " '%s' Can't be empty,model field means '%s'";

    protected ModelUtil() {
        // allow Subclass
    }

    public static <T> SortedMap<String, String> toTreeMap(T model) {
        SortedMap<String, String> tree = new TreeMap<>();
        for (Field field : getAllFieldsAndFilter(model)) {
            ParseFiled parseFiled = parseFiled(field, model);
            if (parseFiled != null) {
                tree.put(parseFiled.getKey(), parseFiled.getStringValue());
            }
        }
        return tree;
    }

    /**
     * 获取所有字段 包括父类
     *
     * @param model
     * @param <T>
     */
    public static <T> Set<Field> getAllFields(T model) {
        return getAllFields(model.getClass());
    }

    /**
     * 获取所有字段 包括父类
     *
     * @param model
     */
    public static Set<Field> getAllFields(Class<?> model) {
        Set<Field> fieldSet = new HashSet<>(50);
        //第一个superClass
        Class<?> superClass = model.getSuperclass();

        //如果父类不是Object才进行添加
        if (superClass != null && !superClass.equals(Object.class)) {
            while (superClass != null) {
                Field[] fields = superClass.getDeclaredFields();
                fieldSet.addAll(Arrays.asList(fields));
                superClass = superClass.getSuperclass();
            }
        }
        Field[] declaredFields = model.getDeclaredFields();
        fieldSet.addAll(Arrays.asList(declaredFields));
        return fieldSet;
    }

    /**
     * 获取所有字段 包括父类
     *
     * @param model
     * @param <T>
     */
    public static <T> Set<Field> getAllFieldsAndFilter(T model) {
        Set<Field> allFields = getAllFields(model);
        allFields.removeIf(ModelUtil::verificationField);
        return allFields;
    }

    /**
     * 验证字段
     */
    public static <T> void verificationField(T model) throws MutilsException {
        for (Field field : getAllFieldsAndFilter(model)) {
            parseFiled(field, model);
        }
    }


    /**
     * 验证某个字段
     *
     * @param field
     */
    public static boolean verificationField(Field field) {
        int modifiers = field.getModifiers();
        Ignore ignore = field.getAnnotation(Ignore.class);
        return Modifier.isStatic(modifiers)
                || Modifier.isTransient(modifiers)
                || Modifier.isFinal(modifiers)
                || Modifier.isNative(modifiers)
                || !Modifier.isPrivate(modifiers)
                || ignore != null;
    }

    public static <T> ParseFiled parseFiled(Field field, T model) {
        ParseFiled parseFiled = new ParseFiled();
        try {
            NotNull annotation = field.getAnnotation(NotNull.class);
            String key = annotation.key();
            if (StringUtil.isBlank(key)) {
                key = field.getName();
            }
            field.setAccessible(true);
            Object object = field.get(model);
            if (object == null) {
                if (annotation.notNull()) {
                    String description = annotation.value();
                    throw new MutilsException(String.format(ERROR_MESSAGE_TEMPLATE, key, description));
                } else {
                    return null;
                }
            }
            parseFiled.setData(object, key);
            return parseFiled;
        } catch (Exception e) {
            // 抛出异常
            throw new MutilsException(e);
        }
    }

    /**
     * 初始书字段
     */
    @Getter
    public static class ParseFiled {

        private Object value;

        private String key;

        public String getStringValue() {
            return value != null ? value.toString() : null;
        }

        void setData(Object value, String key) {
            this.value = value;
            this.key = key;
        }
    }
}
