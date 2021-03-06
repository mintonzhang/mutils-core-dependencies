package cn.minsin.core.tools.math;

import lombok.NoArgsConstructor;
import lombok.NonNull;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * 增强 BigDecimal
 *
 * @author: minton.zhang
 * @since: 2020/5/9 15:07
 */
@NoArgsConstructor
public class BigDecimalPlus extends Number implements Supplier<BigDecimal> {

    /**
     * 核心转换方法
     *
     * @param number       需要转换BigDecimal的值
     * @param defaultValue 转换失败时的默认值
     */
    public static BigDecimal valueOf(Number number, Function<Number, BigDecimal> defaultValue) {
        if (number instanceof BigDecimal) {
            return (BigDecimal) number;
        } else if (number instanceof Integer) {
            return BigDecimal.valueOf((Integer) number);
        } else if (number instanceof Long) {
            return BigDecimal.valueOf((Long) number);
        } else if (number instanceof Double) {
            return BigDecimal.valueOf((Double) number);
        } else if (number instanceof Float) {
            return BigDecimal.valueOf((Float) number);
        } else if (number instanceof BigDecimalPlus) {
            return ((BigDecimalPlus) number).get();
        } else {
            if (defaultValue == null) {
                throw new UnsupportedOperationException();
            }
            return defaultValue.apply(number);
        }
    }

    /**
     * 核心转换方法
     *
     * @param number       需要转换BigDecimal的值
     * @param defaultValue 转换失败时的默认值
     */
    public static BigDecimal valueOf(String number, Function<String, BigDecimal> defaultValue) {
        try {
            return new BigDecimal(number);
        } catch (Exception e) {
            if (defaultValue == null) {
                throw new UnsupportedOperationException();
            }
            return defaultValue.apply(number);
        }
    }


    public static BigDecimalPlus of(Number number) {
        return new BigDecimalPlus(number);
    }

    public static BigDecimalPlus of(String numberStr) {
        return new BigDecimalPlus(numberStr);
    }

    public static BigDecimalPlus of(Number number, Function<Number, BigDecimal> defaultValue) {
        return new BigDecimalPlus(number, defaultValue);
    }

    public static BigDecimalPlus of(String number, Function<String, BigDecimal> defaultValue) {
        return new BigDecimalPlus(number, defaultValue);
    }

    /**
     * 保留小数位数
     *
     * @param number       需要验证的数字
     * @param scale        小数位数
     * @param roundingMode 小数保留方式
     */
    public static BigDecimal setScale(BigDecimal number, int scale, RoundingMode roundingMode) {
        return number == null ? BigDecimal.ZERO : number.setScale(scale, roundingMode);
    }


    /**
     * 计算结果存储
     */
    private final BigDecimalContainer value = new BigDecimalContainer();

    public BigDecimalPlus(Number number) {
        this.value.set(valueOf(number, e -> {
            throw new UnsupportedOperationException();
        }));
    }

    public BigDecimalPlus(String numberStr) {
        this.value.set(valueOf(numberStr, e -> {
            throw new UnsupportedOperationException();
        }));
    }

    public BigDecimalPlus(Number number, Function<Number, BigDecimal> defaultValue) {
        this.value.set(valueOf(number, defaultValue));
    }

    public BigDecimalPlus(String numberStr, Function<String, BigDecimal> defaultValue) {
        this.value.set(valueOf(numberStr, defaultValue));
    }

    public void setValue(BigDecimal bigDecimal) {
        this.value.set(bigDecimal);
    }

    public BigDecimal getValue() {
        return this.value.get();
    }


    //********************************分割线****************************************//


    public BigDecimalPlus add(@NonNull final Number number) {
        this.value.computeAndSet(e -> e.add(valueOf(number, null)));
        return this;
    }

    public BigDecimalPlus add(@NonNull final String number) {
        this.value.computeAndSet(e -> e.add(valueOf(number, null)));
        return this;
    }

    public BigDecimalPlus add(@NonNull final Number number, int scale, RoundingMode roundingMode) {
        this.value.computeAndSet(e -> e.add(valueOf(number, null), new MathContext(scale, roundingMode)));
        return this;
    }

    public BigDecimalPlus add(@NonNull final String number, int scale, RoundingMode roundingMode) {
        this.value.computeAndSet(e -> e.add(valueOf(number, null), new MathContext(scale, roundingMode)));
        return this;
    }


    //********************************分割线****************************************//


    public BigDecimalPlus subtract(@NonNull final Number number) {
        this.value.computeAndSet(e -> e.add(valueOf(number, null).negate()));
        return this;
    }

    public BigDecimalPlus subtract(@NonNull final String number) {
        this.value.computeAndSet(e -> e.add(valueOf(number, null).negate()));
        return this;
    }

    public BigDecimalPlus subtract(@NonNull final Number number, int scale, RoundingMode roundingMode) {
        this.value.computeAndSet(e -> e.add(valueOf(number, null).negate(), new MathContext(scale, roundingMode)));
        return this;
    }

    public BigDecimalPlus subtract(@NonNull final String number, int scale, RoundingMode roundingMode) {
        this.value.computeAndSet(e -> e.add(valueOf(number, null).negate(), new MathContext(scale, roundingMode)));
        return this;
    }


    //********************************分割线****************************************//

    public BigDecimalPlus multiply(@NonNull final Number number) {
        this.value.computeAndSet(e -> e.multiply(valueOf(number, null)));
        return this;
    }

    public BigDecimalPlus multiply(@NonNull final String number) {
        this.value.computeAndSet(e -> e.multiply(valueOf(number, null)));
        return this;
    }

    public BigDecimalPlus multiply(@NonNull final Number number, int scale, RoundingMode roundingMode) {
        this.value.computeAndSet(e -> e.multiply(valueOf(number, null), new MathContext(scale, roundingMode)));
        return this;
    }

    public BigDecimalPlus multiply(@NonNull final String number, int scale, RoundingMode roundingMode) {
        this.value.computeAndSet(e -> e.multiply(valueOf(number, null), new MathContext(scale, roundingMode)));
        return this;
    }

    public BigDecimalPlus multiply100() {
        this.value.computeAndSet(e -> e.multiply(BigDecimal.valueOf(100)));
        return this;
    }


    //********************************分割线****************************************//

    public BigDecimalPlus divide(@NonNull final Number number) {
        this.value.computeAndSet(e -> e.divide(valueOf(number, null), new MathContext(2, RoundingMode.HALF_UP)));
        return this;
    }

    public BigDecimalPlus divide(@NonNull final String number) {
        this.value.computeAndSet(e -> e.divide(valueOf(number, null), new MathContext(2, RoundingMode.HALF_UP)));
        return this;
    }

    public BigDecimalPlus divide(@NonNull final Number number, int scale, RoundingMode roundingMode) {
        this.value.computeAndSet(e -> e.divide(valueOf(number, null), new MathContext(scale, roundingMode)));
        return this;
    }

    public BigDecimalPlus divide(@NonNull final String number, int scale, RoundingMode roundingMode) {
        this.value.computeAndSet(e -> e.divide(valueOf(number, null), new MathContext(scale, roundingMode)));
        return this;
    }

    public BigDecimalPlus divide100() {
        this.value.computeAndSet(e -> e.divide(BigDecimal.valueOf(100), new MathContext(2, RoundingMode.HALF_UP)));
        return this;
    }

    //********************************分割线****************************************//


    public BigDecimalPlus remainder(@NonNull final Number number) {
        this.value.computeAndSet(e -> e.remainder(valueOf(number, null), new MathContext(2, RoundingMode.HALF_UP)));
        return this;
    }

    public BigDecimalPlus remainder(@NonNull final String number) {
        this.value.computeAndSet(e -> e.remainder(valueOf(number, null), new MathContext(2, RoundingMode.HALF_UP)));
        return this;
    }

    public BigDecimalPlus remainder(@NonNull final Number number, int scale, RoundingMode roundingMode) {
        this.value.computeAndSet(e -> e.remainder(valueOf(number, null), new MathContext(scale, roundingMode)));
        return this;
    }

    public BigDecimalPlus remainder(@NonNull final String number, int scale, RoundingMode roundingMode) {
        this.value.computeAndSet(e -> e.remainder(valueOf(number, null), new MathContext(scale, roundingMode)));
        return this;
    }

    //********************************分割线****************************************//

    public BigDecimalPlus abs() {
        this.value.computeAndSet(BigDecimal::abs);
        return this;
    }

    public BigDecimalPlus abs(int scale, RoundingMode roundingMode) {
        this.value.computeAndSet(e -> e.abs(new MathContext(scale, roundingMode)));
        return this;
    }


    //********************************分割线****************************************//

    public BigDecimalPlus setScale(int scale, RoundingMode roundingMode) {
        this.value.computeAndSet(e -> e.setScale(scale, roundingMode));
        return this;
    }


    //********************************分割线****************************************//

    public BigDecimalPlus pow(int n) {
        this.value.computeAndSet(e -> e.pow(n));
        return this;
    }


    @Override
    public int intValue() {
        return this.getValue().intValue();
    }

    @Override
    public long longValue() {
        return this.getValue().longValue();
    }

    @Override
    public float floatValue() {
        return this.getValue().floatValue();

    }

    @Override
    public double doubleValue() {
        return this.getValue().doubleValue();
    }


    @Override
    public String toString() {
        return this.getValue().toString();
    }

    @Override
    public BigDecimal get() {
        return this.getValue();
    }


    /**
     * 内部存储容器
     */
    private final static class BigDecimalContainer {

        private BigDecimal value;


        public final void computeAndSet(UnaryOperator<BigDecimal> updateFunction) {
            value = updateFunction.apply(value);
        }

        public final void set(BigDecimal bigDecimal) {
            this.value = bigDecimal;
        }

        public final BigDecimal get() {
            return this.value;
        }
    }
}
