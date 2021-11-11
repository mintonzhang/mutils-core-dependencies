package cn.minsin.core.tools.tuple;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * @author: minton.zhang
 * @since: 2020/12/16 下午2:22
 */
@EqualsAndHashCode
@Getter
@NoArgsConstructor
public class Tuple2<T1, T2> {

    private T1 t1;
    private T2 t2;

    public Tuple2(T1 t1, T2 t2) {
        this.t1 = t1;
        this.t2 = t2;
    }


}
