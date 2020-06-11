package cn.minsin.core.constant;

/**
 * {@link SuppressWarnings} value常用类型
 * @author: minton.zhang
 * @date: 2020/4/19 21:42
 * @description:
 */
public interface SuppressWarningsTypeConstant {


    /**
     * 使用了不赞成使用的类或方法时的警告
     * {@link Deprecated} 该注解
     */
    String DEPRECATION ="deprecation";
    /**
     *执行了未检查的转换时的警告，例如当使用集合时没有用泛型 (Generics) 来指定集合保存的类型
     */
    String UNCHECKED ="unchecked";
    /**
     *当 Switch 程序块直接通往下一种情况而没有 Break 时的警告
     */
    String FALLTHROUGH ="fallthrough";
    /**
     * 在类路径、源文件路径等中有不存在的路径时的警告
     */
    String PATH ="path";
    /**
     * 当在可序列化的类上缺少 serialVersionUID 定义时的警告;
     */
    String SERIAL ="serial";

    /**
     * 任何 finally 子句不能正常完成时的警告
      */
    String FINALLY ="finally";
    /**
     * 屏蔽所有警告
     */
    String ALL ="all";
    /**
     *
     */
    String RAW_TYPES ="rawtypes";

}
