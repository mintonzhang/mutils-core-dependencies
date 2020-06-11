package cn.minsin.core.web.result;

import cn.minsin.core.constant.SuppressWarningsTypeConstant;
import cn.minsin.core.exception.MutilsException;
import cn.minsin.core.override.JsonModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Consumer;

/**
 * <pre>
 *     作为API接口的返回值与{@linkplain MultiResult}不同的是,它可以添加多个data。
 *     内部使用{@linkplain HashMap}进行存储
 * </pre>
 *
 * @author: minton.zhang
 * @since: 2020/5/9 21:21
 */
@Getter
@Setter
@Accessors(chain = true)
@ToString
@NoArgsConstructor
public class Result<T> extends JsonModel implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     */
    private Integer code;

    /**
     * 状态消息
     */
    private String msg;

    /**
     * 存储对象
     */
    private T data;

    public Result(ResultOptions options, String... msg) {
        this.code = options.getCode();
        this.msg = this.chooseMsg(options.getMsg(), msg);
    }

    /**
     * 获取消息
     */
    protected String chooseMsg(String defaultMsg, String... msg) {
        return (msg == null || msg.length == 0) ? defaultMsg : msg[0];
    }

    protected <R> Result<R> copy(R data) {
        return new Result<R>().setData(data).setCode(this.code).setMsg(this.msg);
    }


    //********************************Data 二次消费 替换 初始化****************************************//

    /**
     * 对data进行二次操作
     */
    public Result<T> updateData(Consumer<T> function) {
        function.accept(this.data);
        return this;
    }

    /**
     * 清空数据
     */
    public Result<T> removeData() {
        this.data = null;
        return this;
    }

    public Result<T> setResultOptions(ResultOptions resultOptions) {
        this.code = resultOptions.getCode();
        this.msg = resultOptions.getMsg();
        return this;
    }

    //********************************Data 二次消费 替换 初始化****************************************//


    //********************************分割线****************************************//

    /**
     * 构造result
     *
     * @param option  需要实现接口ResultOptions 的枚举 默认枚举是 DefaultResultOptions
     * @param message 提示给前端的消息
     */
    public static <T> Result<T> builder(ResultOptions option, String... message) {
        return new Result<>(option, message);
    }

    /**
     * code 来自Result中的 SUCCESS 或 EXCEPTION
     *
     * @param msg this default is '操作成功'
     */
    public static <T> Result<T> ok(String... msg) {
        return builder(DefaultResultOptions.SUCCESS, msg);
    }

    /**
     * code 来自Result中的 SUCCESS 或 EXCEPTION
     */
    public static <T> Result<T> ok(T data) {
        return new Result<T>().setData(data).setResultOptions(DefaultResultOptions.SUCCESS);
    }


    /**
     * 返回成功 并且data为一个空Map
     */
    @SuppressWarnings({SuppressWarningsTypeConstant.UNCHECKED})
    public static <T> Result<T> emptyOk() {
        return new Result<T>().setData((T) Collections.EMPTY_MAP).setResultOptions(DefaultResultOptions.SUCCESS);
    }

    /**
     * code 来自Result中的 SUCCESS 或 EXCEPTION
     *
     * @param msg this default is '服务器异常'
     */
    public static <T> Result<T> exception(String... msg) {
        return builder(DefaultResultOptions.EXCEPTION, msg);
    }

    /**
     * 构建缺少参数的Result
     *
     * @param msg this default is '缺少必要参数'
     */
    public static <T> Result<T> missParam(String... msg) {
        return builder(DefaultResultOptions.MISS_PARAM, msg);
    }

    /**
     * 构建失败消息
     *
     * @param msg this default is '操作失败'
     */
    public static <T> Result<T> fail(String... msg) {
        return builder(DefaultResultOptions.FAIL, msg);
    }

    /**
     * 构建用户过期
     *
     * @param msg this default is '用户已失效'
     */
    public static <T> Result<T> timeout(String... msg) {
        return builder(DefaultResultOptions.OUT_TIME, msg);
    }

    /**
     * 构建错误
     *
     * @param msg this default is '服务器跑路了，请稍后重试'
     */
    public static <T> Result<T> error(String... msg) {
        return builder(DefaultResultOptions.ERROR, msg);
    }


    /**
     * 获取公共提示消息并封装成Result
     * 推荐 非新增和修改时使用
     *
     * @param typeOrId  操作类型或判断关键字段
     * @param isSuccess 操作是否成功
     */
    public static <T> Result<T> optionalResult(boolean isSuccess, String typeOrId) {
        final String realOperationType = OperationStore.isOperationType(typeOrId) ? typeOrId : OperationType.AUTO_CHOOSE(typeOrId);
        return builder(isSuccess ? DefaultResultOptions.SUCCESS : DefaultResultOptions.FAIL,
                OperationType.isSuccess(realOperationType, isSuccess));
    }

    /**
     * 解析Exception中的异常
     *
     * @param e             异常
     * @param operationType 操作类型
     */
    public static <T> Result<T> optionalResult(Throwable e, String operationType) {
        return Result.exception(getMessage(e, OperationType.isSuccess(operationType, false)));
    }

    protected static String getMessage(Throwable e, String defaultValue) {
        boolean b = e instanceof MutilsException;
        if (b) {
            String message = e.getMessage();
            return message == null ? defaultValue : defaultValue.concat(",").concat(message);
        }
        return defaultValue;
    }

    //********************************分割线****************************************//

}
