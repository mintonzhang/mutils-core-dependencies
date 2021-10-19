package cn.minsin.core.tools;

import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author minton.zhang
 * @since 2021/9/22 15:16
 */
public interface MapUtil {


    static Integer getMaxKey(Map<Integer, ?> map) {
        if (map == null) {
            return null;
        }
        Integer[] key = map.keySet().toArray(new Integer[0]);
        Arrays.sort(key);
        return key[key.length - 1];
    }

    static Integer getMinKey(Map<Integer, ?> map) {
        if (map == null) {
            return null;
        }
        Integer[] key = map.keySet().toArray(new Integer[0]);
        Arrays.sort(key);
        return key[0];
    }


    /**
     * JDK8
     * 按照Value排序
     * {@link Map.Entry#comparingByValue()}
     * {@link Map.Entry#comparingByKey()}
     * {@link Map.Entry#comparingByValue(Comparator)}
     * {@link Map.Entry#comparingByKey(Comparator)}
     *
     * @return A new map
     */
    static <K, V> Map<K, V> sort(Map<K, V> map, Comparator<? super Map.Entry<K, V>> cmp) {
        Map<K, V> result = new LinkedHashMap<>();
        map.entrySet()
                .stream()
                .sorted(cmp)
                .forEachOrdered(e -> result.put(e.getKey(), e.getValue()));
        return result;
    }

}
