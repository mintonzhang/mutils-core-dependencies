package cn.minsin.core.web.result;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * <pre>
 *     code设计参考：
 *     <a href="http://www.ruanyifeng.com/blog/2014/05/restful_api.html">第七点</a>
 * web返回对象必须实现ResultOptions
 * </pre>
 *
 * @author mintonzhang
 */
@Getter
@RequiredArgsConstructor
public enum DefaultResultOptions implements ResultOptions {

	//包含 200  201 202  204
	DO_SUCCESS(200, "操作成功"),

	//操作失败 没有对数据库进行操作
	DO_FAILED(400, "操作失败"),
	//没有权限
	FORBIDDEN(403, "对不起,您没有访问权限"),
	//身份过期
	UNAUTHORIZED(401, "您的身份已过期,请重新授权"),
	//查询时没有查询到数据可以使用该状态
	NOT_FOUND(404, "您访问的资源不存在,请检查路径后重试"),
	//服务出现异常
	ERROR(500, "系统开小差了，请稍后重试"),

	/**
	 * 参数验证失败
	 */
	VERIFY_FAILED(422, "参数验证失败");


	/**
     * 状态码
     */
    private final int code;

    /**
     * 提示消息
     */
    private final String msg;

}
