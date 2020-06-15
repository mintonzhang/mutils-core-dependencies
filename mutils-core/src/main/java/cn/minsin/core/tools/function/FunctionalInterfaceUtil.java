package cn.minsin.core.tools.function;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 显示函数工具类
 *
 * @author: minton.zhang
 * @since: 2020/5/19 20:23
 */
public interface FunctionalInterfaceUtil {


    /**
     * 如果为空则返回t2
     *
     * @param t1           验证对象
     * @param defaultValue 默认对象
     */
    static <T> T ifNullOrDefault(T t1, T defaultValue) {
        return t1 == null ? defaultValue : t1;
    }


    /**
     * 转换成List
     *
     * @param source   源Collection
     * @param function 转换函数
     */
    static <R, S> List<R> convertList(Collection<S> source, Function<S, R> function) {
        return source.stream().map(function).collect(Collectors.toList());
    }

    /**
     * 转换成Set
     *
     * @param source   源Collection
     * @param function 转换函数
     */
    static <R, S> Set<R> convertSet(Collection<S> source, Function<S, R> function) {
        return source.stream().map(function).collect(Collectors.toSet());
    }


    /**
     * 将T转换为R
     *
     * @param source   源对象
     * @param function 转换函数
     */
    static <R, S> R convert(S source, Function<S, R> function) {
        return function.apply(source);
    }

    /**
     * 消费对象
     *
     * @param source   源对象
     * @param function 消费函数
     */
    static <S> S convert(S source, Consumer<S> function) {
        function.accept(source);
        return source;
    }

    /**
     * 移除
     *
     * @param source   源对象
     * @param function 消费函数
     */
    static <S> void remove(Collection<S> source, Predicate<S> function) {
        source.removeIf(next -> !function.test(next));
    }

    /**
     * 过滤
     *
     * @param source   源对象
     * @param function 消费函数
     */
    static <S> Stream<S> filter(Collection<S> source, Predicate<S> function) {
        return source.stream().filter(next -> !function.test(next));
    }

    /**
     * 分组
     *
     * @param source        源对象
     * @param keyFunction   key函数
     * @param valueFunction value函数
     */
    static <S, K, V> Map<K, List<V>> groupToHashMap(Collection<S> source, Function<S, K> keyFunction, Function<S, V> valueFunction) {
        return source.stream().collect(Collectors.groupingBy(keyFunction, Collectors.mapping(valueFunction, Collectors.toList())));
    }


    /**
     * 分组
     *
     * @param source        源stream
     * @param keyFunction   key函数
     * @param valueFunction value函数
     */
    static <S, K, V> Map<K, List<V>> groupToHashMap(Stream<S> source, Function<S, K> keyFunction, Function<S, V> valueFunction) {
        return source.collect(Collectors.groupingBy(keyFunction, Collectors.mapping(valueFunction, Collectors.toList())));

    }

    /**
     * 分组
     *
     * @param source        源stream
     * @param keyFunction   key函数
     * @param valueFunction value函数
     */
    static <S, K, V> Map<K, List<V>> groupToConcurrentMap(Stream<S> source, Function<S, K> keyFunction, Function<S, V> valueFunction) {
        return source.collect(Collectors.groupingByConcurrent(keyFunction, Collectors.mapping(valueFunction, Collectors.toList())));
    }

    /**
     * 分组
     *
     * @param source        源对象
     * @param keyFunction   key函数
     * @param valueFunction value函数
     */
    static <S, K, V> Map<K, List<V>> groupToConcurrentMap(Collection<S> source, Function<S, K> keyFunction, Function<S, V> valueFunction) {
        return source.stream().collect(Collectors.groupingByConcurrent(keyFunction, Collectors.mapping(valueFunction, Collectors.toList())));
    }

    /**
     * 如果为空则返回t2
     *
     * @param t1              验证对象
     * @param defaultValue    如果t1为空时返回默认值
     * @param notNullFunction t1不为空时返回的默认值
     */
    static <S, R> R ifNotNullExecuteOrDefault(S t1, R defaultValue, Function<S, R> notNullFunction) {
        if (t1 != null) {
            return notNullFunction.apply(t1);
        }
        return defaultValue;
    }

    /**
     * 如果不为空则执行函数
     */
    static <P1, P2> void ifNotNullExecute(P1 param1, P2 param2, BiConsumer<P1, P2> consumer) {
        if (param1 != null) {
            consumer.accept(param1, param2);
        }
    }

    /**
     * 如果不为空则执行函数
     */
    static <P1> void ifNotNullExecute(P1 param1, Consumer<P1> consumer) {
        if (param1 != null) {
            consumer.accept(param1);
        }
    }

    /**
     * <pre>
     *     参数：
     *     1.入参为param1
     *     2.被操作对象为param2
     *     3.消费函数
     *      3.1 第一个参数 为boolean类型  根据param1是否等于null
     *      3.2 第二个参数 param1 可用于第二次判断
     *      3.3 第三个参数 param2
     *      3.4 返回值
     *     例子：
     *        List<String> list =new ArrayList<>();
     *
     *         String[] array = {"张三","李四","王五",null,"王二麻子"};
     *
     *         for (String s : array) {
     *             conditionExecute(s, list, (a, b, c) -> {
     *                 if (!a) {
     *                     c.add(b);
     *                 }
     *             });
     *         }
     *         System.out.println(list);
     *
     *      输出： 10	20	30	40	60
     * 类似 {@link #conditionExecute(Object, Object, Function3)}  唯一的不同就是没有返回值
     * </pre>
     */
    static <P1, P2> void conditionExecute(P1 param1, P2 param2, Consumer3<Boolean, P1, P2> function) {
        function.accept(param1 == null, param1, param2);
    }

    /**
     * <pre>
     *     参数：
     *     1.入参为param1
     *     2.被操作对象为param2
     *     3.消费函数
     *      3.1 第一个参数 为boolean类型  根据param1是否等于null
     *      3.2 第二个参数 param1 可用于第二次判断
     *      3.3 第三个参数 param2
     *      3.4 返回值
     *     例子：
     *        List<String> list =new ArrayList<>();
     *
     *         String[] array = {"张三","李四","王五",null,"王二麻子"};
     *
     *         for (String s : array) {
     *             conditionExecute(s, list, (a, b, c) -> {
     *                 if (!a) {
     *                     c.add(b);
     *                 }
     *                 return list;
     *             });
     *         }
     *         System.out.println(list);
     *      输出：[张三, 李四, 王五, 王二麻子]
     *  类似 {@link #conditionExecute(Object, Object, Consumer3)}  唯一的不同就是没有返回值
     * </pre>
     */
    static <P1, P2, Return> Return conditionExecute(P1 param1, P2 param2, Function3<Boolean, P1, P2, Return> function) {
        return function.apply(param1 == null, param1, param2);
    }

    /**
     * 条件执行
     *
     * @param param1   参数1
     * @param function 消费函数
     */
    static <P1> void conditionExecute(P1 param1, BiConsumer<Boolean, P1> function) {
        function.accept(param1 == null, param1);
    }

    /**
     * 条件执行
     *
     * @param param1   参数1
     * @param function 转换函数
     */
    static <P1, Return> Return conditionExecute(P1 param1, BiFunction<Boolean, P1, Return> function) {
        return function.apply(param1 == null, param1);
    }


    /**
     * toSupplier
     */
    static <R> Supplier<R> toSupplier(R data) {
        return () -> data;
    }

}
