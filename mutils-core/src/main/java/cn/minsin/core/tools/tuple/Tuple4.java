package cn.minsin.core.tools.tuple;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author: minton.zhang
 * @since: 2020/12/16 下午2:22
 */
@EqualsAndHashCode(callSuper = true)
@Getter
@NoArgsConstructor
public class Tuple4<T1, T2, T3, T4> extends Tuple3<T1, T2, T3> {

    private T4 t4;

    public Tuple4(T1 t1, T2 t2, T3 t3, T4 t4) {
        super(t1, t2, t3);
        this.t4 = t4;
    }
}
