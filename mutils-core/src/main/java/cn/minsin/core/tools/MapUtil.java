package cn.minsin.core.tools;


import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

/**
 * 此方法提供一些map的常用操作， 更多的可以查询 {@link org.apache.commons.collections4.MapUtils}
 *
 * @author minsin
 */
public class MapUtil {

    /**
     * 将一个对象中不为空 、不是static、Private的字段和值取出来生成一个map
     */
    public static <T> Map<String, Object> toMap(T model) {
        HashMap<String, Object> hashMap = new HashMap<>();
        ReflectUtil.getFieldValue(model, hashMap::put);
        return hashMap;
    }

    /**
     * 将一个对象中不为空 、不是static、Private的字段和值取出来生成一个map
     */
    public static <T> Map<String, Object> toTreeMap(T model) {
        Map<String, Object> hashMap = new TreeMap<>();
        ReflectUtil.getFieldValue(model, hashMap::put);
        return hashMap;
    }
}
