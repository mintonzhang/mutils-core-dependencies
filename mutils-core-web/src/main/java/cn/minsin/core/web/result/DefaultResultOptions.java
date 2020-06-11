package cn.minsin.core.web.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * web返回对象必须实现ResultOptions
 *
 * @author mintonzhang
 */
@Getter
@RequiredArgsConstructor
public enum DefaultResultOptions implements ResultOptions {
    /**
     * 服务器出现不可逆错误
     */
    ERROR(500, "服务器跑路了，请稍后重试"),
    /**
     * 服务器发生异常
     */
    EXCEPTION(404, "系统开小差了，请稍后重试"),
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 操作失败
     */
    FAIL(201, "操作失败"),
    /**
     * 用户信息失效
     */
    OUT_TIME(202, "您的身份过期了，请重新登录"),
    /**
     * 缺少参数
     */
    MISS_PARAM(203, "您好像少提交了参数");

    /**
     * 状态码
     */
    private final int code;

    /**
     * 提示消息
     */
    private final String msg;
}
