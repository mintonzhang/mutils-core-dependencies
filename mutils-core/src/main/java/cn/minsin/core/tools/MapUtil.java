package cn.minsin.core.tools;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 此方法提供一些map的常用操作， 更多的可以查询 {@link org.apache.commons.collections4.MapUtils}
 *
 * @author minsin
 */
public class MapUtil {

    protected MapUtil() {
        // allow Subclass
    }

    /**
     * 将一个对象中不为空 、不是static、Private的字段和值取出来生成一个map
     *
     * @param model
     */
    public static <T> Map<String, Object> toMap(T model) {
        HashMap<String, Object> hashMap = new HashMap<>();
        Set<Field> allFieldsAndFilter = ModelUtil.getAllFieldsAndFilter(model);
        for (Field field : allFieldsAndFilter) {
            try {
                String key = field.getName();
                field.setAccessible(true);
                Object object = field.get(model);
                if (object != null) {
                    hashMap.put(key, object);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return hashMap;
    }

    public static <T> T mapToObject(Map<String, ?> map, Class<T> clazz) {
        String jsonString = JSON.toJSONString(map);
        return JSON.parseObject(jsonString, clazz);
    }

    /**
     * 初始化hashmap并且将参数放入map
     *
     * @param k
     * @param v
     */
    public static <K, V> Map<K, V> newInstance(Class<K> k, Class<V> v) {
        return new HashMap<>();
    }

    /**
     * 初始化hashmap并且将参数放入map
     *
     * @param k
     * @param v
     */
    public static <K, V> Map<K, V> newInstanceAndPut(K k, V v) {
        Map<K, V> map = new HashMap<K, V>();
        map.put(k, v);
        return map;
    }
}
