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
public class Tuple3<T1, T2, T3> extends Tuple2<T1, T2> {


    private T3 t3;

    public Tuple3(T1 t1, T2 t2, T3 t3) {
        super(t1, t2);
        this.t3 = t3;
    }
}
