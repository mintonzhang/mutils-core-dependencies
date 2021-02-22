package cn.minsin.core.tools.enums;

import java.util.function.BiFunction;
import java.util.function.Function;

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
     * 按照key进行寻找 如果没有找到则返回null
     */
    static <T extends KeyEnum<Key>, Key> T findByKey(Class<T> clazz, Key key) {
        return findByKey(clazz, key, (a, b) -> b);
    }

    /**
     * 按照name进行寻找 如果没有找到则返回null
     */
    static <T extends Enum<T>> T findByName(T[] clazz, String name) {
        return findByName(clazz, name,b -> b);
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


}
