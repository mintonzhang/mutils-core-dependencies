package cn.minsin.core.tools.math;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.Nullable;

/**
 * java多数compare方法 逻辑如下
 * <pre>
 * BigDecimal b1 = new BigDecimal(0.1);
 * BigDecimal b2 = new BigDecimal(0.2);
 * int c = b1.compareTo(b2);  // -1
 * c=1表示b1大于b2
 * c=0表示b1等于b2
 * c=-1表示b1小于b2
 * </pre>
 *
 * @author minsin/mintonzhang@163.com
 * @since 2022/8/14
 */
@Getter
@RequiredArgsConstructor
public enum CompareResult {
    /**
     * 大于
     */
    GT(1),
    /**
     * 小于
     */
    LT(-1),
    /**
     * 等于
     */
    EQ(0),
    ;

    /**
     * java中compare方法返回值
     */
    private final int code;


    @Nullable
    public static CompareResult getResult(int code) {
        for (CompareResult value : CompareResult.values()) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }


}
