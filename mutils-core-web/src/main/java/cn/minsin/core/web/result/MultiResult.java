package cn.minsin.core.web.result;

import lombok.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * <pre>
 *     作为API接口的返回值与{@linkplain Result}不同的是,可以添加多个data。
 *     内部使用{@linkplain HashMap}进行存储
 *     使用{@linkplain MultiResult#data(String, Object)}进行添加数据
 *
 * </pre>
 *
 * @author: minton.zhang
 * @since: 2020/5/9 21:32
 */
public class MultiResult extends Result<Map<String, Object>> {

    private static final long serialVersionUID = 1L;

    public MultiResult(ResultOptions options, String... msg) {
        super(options, msg);
        super.setData(new HashMap<>(3));
    }

    /**
     * 添加Data
     */
    public MultiResult data(@NonNull final String key, @NonNull final Object value) {
        this.updateData(e -> e.put(key, value));
        return this;
    }

    @Override
    public MultiResult updateData(Consumer<Map<String, Object>> function) {
        function.accept(this.getData());
        return this;
    }

    @Override
    public MultiResult removeData() {
        this.getData().clear();
        return this;
    }

    /**
     * code 来自Result中的 SUCCESS 或 EXCEPTION
     *
     * @param msg this default is '操作成功'
     */
    public static MultiResult ok(String... msg) {
        return new MultiResult(DefaultResultOptions.DO_SUCCESS, msg);
    }

    /**
     * code 来自Result中的 SUCCESS 或 EXCEPTION
     *
     * @param msg this default is '操作成功'
     */
    public static MultiResult fail(String... msg) {
        return new MultiResult(DefaultResultOptions.DO_FAILED, msg);
    }
}
