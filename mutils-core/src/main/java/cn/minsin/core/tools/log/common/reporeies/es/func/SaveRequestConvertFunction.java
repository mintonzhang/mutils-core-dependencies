package cn.minsin.core.tools.log.common.reporeies.es.func;

import cn.minsin.core.tools.log.common.reporeies.es.request.BaseSaveRequest;
import cn.minsin.core.tools.tuple.Tuple2;

/**
 * @author minton.zhang
 * @since 2021/11/11 17:03
 */
public interface SaveRequestConvertFunction<R extends BaseSaveRequest> {


    Tuple2<String, R> apply(Throwable throwable);

}
