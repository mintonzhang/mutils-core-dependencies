package cn.minsin.core.tools;

import cn.minsin.core.exception.MutilsException;
import cn.minsin.core.override.AutoCloneable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

/**
 * util的帮助类
 *
 * @author minsin
 */
public class ListUtil {


    protected ListUtil() {
        // allow Subclass 
    }

    /**
     * 创建一个list
     */
    public static <E> ArrayList<E> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * 创建一个并制定类型
     */
    public static <E> ArrayList<E> newArrayList(Class<E> clazz) {
        if (clazz == null) {
            return newArrayList();
        }
        return new ArrayList<>();
    }

    @SafeVarargs
    public static <E> ArrayList<E> newArrayList(E... elements) {
        checkNotNull(elements);
        int capacity = computeArrayListCapacity(elements.length);
        ArrayList<E> list = new ArrayList<>(capacity);
        Collections.addAll(list, elements);
        return list;
    }

    public static <E> ArrayList<E> newArrayList(Iterator<? extends E> elements) {
        ArrayList<E> list = newArrayList();
        addAll(list, elements);
        return list;
    }

    public static <T> boolean addAll(final Collection<T> collection, final Iterator<? extends T> iterator) {
        Objects.requireNonNull(collection);
        Objects.requireNonNull(iterator);
        boolean wasModified = false;
        while (iterator.hasNext()) {
            wasModified |= collection.add(iterator.next());
        }
        return wasModified;
    }

    static int computeArrayListCapacity(int arraySize) {
        checkNonnegative(arraySize, "arraySize");
        return saturatedCast(5L + arraySize + (arraySize / 10));
    }

    static int checkNonnegative(int value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " cannot be negative but was: " + value);
        }
        return value;
    }

    static long checkNonnegative(long value, String name) {
        if (value < 0) {
            throw new IllegalArgumentException(name + " cannot be negative but was: " + value);
        }
        return value;
    }

    public static <T> T checkNotNull(T reference) {
        if (reference == null) {
            throw new NullPointerException();
        }
        return reference;
    }

    public static int saturatedCast(long value) {
        if (value > Integer.MAX_VALUE) {
            return Integer.MAX_VALUE;
        }
        if (value < Integer.MIN_VALUE) {
            return Integer.MIN_VALUE;
        }
        return (int) value;
    }

    /**
     * 如果集合为空 则返回空list 如果不是就返回本身
     *
     * @param list
     */
    public static <T> List<T> emptyIfNull(final List<T> list) {
        return list == null ? Collections.emptyList() : list;
    }

    /**
     * 判断集合是否为空 如果为空则返回true 反之为false
     *
     * @param list
     */
    public static boolean isEmpty(final List<?> list) {

        return list == null || list.isEmpty();
    }

    /**
     * 判断数组集合
     *
     * @param list
     */
    public static boolean isEmpty(final List<?>... list) {
        for (List<?> list2 : list) {
            if (!isEmpty(list2)) {
                return false;
            }
        }
        return true;
    }

    public static boolean isNotEmpty(final List<?> list) {

        return !isEmpty(list);
    }

    public static boolean isNotEmpty(final List<?>... list) {
        return !isEmpty(list);
    }


    /**
     * 深拷贝 List对象
     *
     * @param source 被克隆对象
     * @param <T>    T 必须实现{@link AutoCloneable} 并且将访问修饰符修改为public
     *               新对象
     */
    @SuppressWarnings("unchecked")
    public static <T extends AutoCloneable> List<T> clone(List<T> source) {
        MutilsException.notNull(source, "The source must not be null.Please check");
        try {
            ArrayList<T> list = new ArrayList<>(source.size());
            for (T t : source) {
                Object o = t.deepClone();
                MutilsException.notNull(o, "The Result of deepClone is null");
                list.add((T) o);
            }
            list.trimToSize();
            return list;
        } catch (Exception e) {
            throw new MutilsException(e, "Please set The clone method is public");
        }
    }

    /**
     * 某些特殊情况不能再数据库分页 采用自行分页
     *
     * @param source 分页对象
     * @param page   页码
     * @param limit  分页条数
     *               新对象
     *               注意 该方法返回的List只是source的映射，如果修改此返回结果的数据，会影响到原数据。
     *               如果不想这样，请使用 {@linkplain ListUtil#clone(List)}
     */
    public static <T> List<T> subList(List<T> source, int page, int limit) {
        MutilsException.notNull(source, "Source must not be null. Please Check.");
        try {
            List<T> result;
            int size = source.size();
            if (size < limit) {
                result = source;
            } else if (page * limit > size) {
                result = source.subList((page - 1) * limit, size);
            } else {
                result = source.subList((page - 1) * limit, limit * page);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
