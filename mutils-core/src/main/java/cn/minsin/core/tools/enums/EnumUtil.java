package cn.minsin.core.tools.enums;

import cn.minsin.core.tools.function.FunctionalInterfaceUtil;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * @author minton.zhang
 * @since 2020/6/2 20:45
 */
public interface EnumUtil {

    /**
     * 按照key进行寻找
     *
     * @param clazz
     * @param key
     * @param function
     * @param <T>
     * @param <Key>
     * @return
     */
    static <T extends KeyEnum<Key>, Key> T findByKey(Class<T> clazz, Key key, BiFunction<Boolean, T, T> function) {
        T[] enumConstants = clazz.getEnumConstants();
        for (T enumConstant : enumConstants) {
            Key key1 = enumConstant.key();
            if (key1.equals(key)) {
                return function.apply(true, enumConstant);
            }
        }
        return function.apply(false, null);
    }

    /**
     * 按照key进行寻找
     */
    static <T extends Enum<T>> T find(Class<T> clazz, Predicate<T> predicate) {
        return find(clazz, predicate, null);
    }

    /**
     * 按照key进行寻找
     */
    static <T extends Enum<T>> T find(Class<T> clazz, Predicate<T> predicate, T defaultValue) {
        return Stream.of(clazz.getEnumConstants()).filter(predicate).findFirst().orElse(defaultValue);
    }

    /**
     * 按照key进行寻找 如果没有找到则返回null
     */
    static <T extends KeyEnum<Key>, Key> T findByKey(Class<T> clazz, Key key) {
        return findByKey(clazz, key, (a, b) -> b);
    }

    /**
     * 按照name进行寻找 如果没有找到则返回null
     */
    static <T extends Enum<T>> T findByName(T[] clazz, String name) {
        return findByName(clazz, name, b -> b);
    }

    /**
     * 按照name进行寻找
     */
    static <T extends Enum<T>> T findByName(T[] clazz, String name, Function<T, T> function) {
        for (T t : clazz) {
            String name1 = t.name();
            if (name1.equals(name)) {
                return function.apply(t);
            }
        }
        return function.apply(null);
    }


    static <T extends Enum<?>> void ifNotNullExecute(T value, Consumer<T> consumer) {
        FunctionalInterfaceUtil.ifNotNullExecute(value, consumer);
    }

    static <R, T extends Enum<?>> R ifNotNullExecute(T value, Function<T, R> function) {
        return FunctionalInterfaceUtil.ifNotNullExecute(value, function);
    }
}
