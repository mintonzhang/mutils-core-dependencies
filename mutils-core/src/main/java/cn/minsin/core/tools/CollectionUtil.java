package cn.minsin.core.tools;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Supplier;

/**
 * 更多查看{@link CollectionUtils}
 *
 * @author minton.zhang
 * @since 2021/6/9 10:59
 */
public interface CollectionUtil {


    static <T> void addIgnoreNull(final Collection<T> collection, final T object, boolean throwException) {

        if (throwException) {
            CollectionUtils.addIgnoreNull(collection, object);
        } else {
            if (object != null) {
                collection.add(object);
            }
        }
    }

    static <T> void addIgnoreNull(final Collection<T> collection, final T object) {
        addIgnoreNull(collection, object, false);
    }

    static <T> List<T> singletonUnModifyList(final T object) {
        if (object == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(object);
    }

    static <T> List<T> singletonList(final T object) {
        final List<T> list = new ArrayList<>(1);
        if (object != null) {
            list.add(object);
        }
        return list;
    }

    static boolean isAnyEmpty(final Collection<?>... collections) {
        for (Collection<?> collection : collections) {
            if (CollectionUtils.isEmpty(collection)) {
                return true;
            }
        }
        return false;
    }

    static boolean isAllEmpty(final Collection<?>... collections) {

        for (Collection<?> collection : collections) {
            if (CollectionUtils.isNotEmpty(collection)) {
                return false;
            }
        }
        return true;
    }

    static <T> Set<T> newSingleHashSet(final T object) {

        Set<T> set = Sets.newHashSet();
        if (object != null) {
            set.add(object);
        }
        return set;

    }

    static <T> List<T> newSingleArrayList(final T object) {

        List<T> set = Lists.newArrayList();
        if (object != null) {
            set.add(object);
        }
        return set;
    }


    /**
     * 数组循环
     *
     * @param array    需要循环的对象
     * @param consumer 第一个参数 是下标
     */
    static <T> void foreach(@NonNull T[] array, @NonNull BiConsumer<Integer, T> consumer) {
        for (int i = 0; i < array.length; i++) {
            consumer.accept(i, array[i]);
        }
    }

    /**
     * 数组循环
     *
     * @param array    需要循环的对象
     * @param function 第一个参数 是下标
     */
    static <T, R> List<R> foreach(@NonNull T[] array, @NonNull BiFunction<Integer, T, R> function) {
        List<R> ret = new ArrayList<>(array.length);
        for (int i = 0; i < array.length; i++) {
            ret.add(function.apply(i, array[i]));
        }
        return ret;
    }

    /**
     * 数组循环
     *
     * @param collection 需要循环的对象
     * @param consumer   第一个参数 是下标
     */
    static <T> void foreach(@NonNull Collection<T> collection, @NonNull BiConsumer<Integer, T> consumer) {

        if (collection instanceof List) {
            List<T> list = (List<T>) collection;
            for (int i = 0; i < list.size(); i++) {
                consumer.accept(i, list.get(i));
            }
        } else {
            int index = 0;
            for (T t : collection) {
                consumer.accept(index, t);
                index++;
            }
        }

    }

    /**
     * 数组循环
     *
     * @param list     需要循环的对象
     * @param supplier 容器提供工厂
     * @param function 第一个参数 是下标
     */
    static <T, R> Collection<R> foreach(@NonNull List<T> list, @NonNull Supplier<Collection<R>> supplier, BiFunction<Integer, T, R> function) {
        Collection<R> rs = supplier.get();

        for (int i = 0; i < list.size(); i++) {
            rs.add(function.apply(i, list.get(i)));
        }
        return rs;
    }

    /**
     * 数组循环
     *
     * @param list     需要循环的对象
     * @param function 第一个参数 是下标
     */
    static <T, R> List<R> foreach(@NonNull List<T> list, @NonNull BiFunction<Integer, T, R> function) {
        List<R> rs = new ArrayList<>(list.size());

        for (int i = 0; i < list.size(); i++) {
            rs.add(function.apply(i, list.get(i)));
        }
        return rs;
    }


    /**
     * 获取N个的数据,并返回到集合中。 注意这个接口如果传一个无序的集合返回的结果可能不是理想的结果
     *
     * @param data     源数据
     * @param size     需要获取的额数据
     * @param supplier 集合工厂
     * @param <T>      数据类型
     * @return 集合
     */
    static <T, C extends Collection<T>> C filterTopN(Collection<T> data, int size, Supplier<C> supplier) {
        int i = 0;
        Iterator<T> iterator = data.iterator();
        C ts = supplier.get();
        while (iterator.hasNext() && i < size) {
            ts.add(iterator.next());
            i++;
        }
        return ts;
    }


}
