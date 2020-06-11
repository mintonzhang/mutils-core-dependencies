package cn.minsin.core.web.result;

import cn.minsin.core.tools.StringUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * @author: minton.zhang
 * @since: 2020/5/9 21:01
 */
public class OperationStore {
    static {
        //默认加载
        init(OperationType.class);
    }

    protected final static CopyOnWriteArraySet<String> VALUES = new CopyOnWriteArraySet<>();


    /**
     * 初始化
     *
     * @param clazz 类
     * @return
     */
    public static void init(Class<?>... clazz) {
        for (Class<?> aClass : clazz) {
            Field[] declaredFields = aClass.getDeclaredFields();
            for (Field declaredField : declaredFields) {
                if (isConstant(declaredField)) {
                    try {
                        VALUES.add(declaredField.get(aClass).toString());
                    } catch (Exception ignored) {
                    }
                }
            }
        }
    }

    /**
     * 判断是否常量
     *
     * @param field
     * @return
     */
    private static boolean isConstant(Field field) {
        int modifiers = field.getModifiers();
        return Modifier.isFinal(modifiers) && Modifier.isStatic(modifiers);
    }

    /**
     * 是否是操作类型
     */
    public static boolean isOperationType(String value) {
        return StringUtil.isNotBlank(value) && VALUES.contains(value);
    }

}
