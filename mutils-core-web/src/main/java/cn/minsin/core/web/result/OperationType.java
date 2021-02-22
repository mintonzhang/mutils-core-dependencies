package cn.minsin.core.web.result;

import cn.minsin.core.tools.StringUtil;

/**
 * 默认操作
 *
 * @author minton.zhang
 * @Date: 2019/9/22 17:47
 * @since 0.0.8.RELEASE
 */
public abstract class OperationType {

    public static final String SUCCESS = "成功";

    public static final String FAIL = "失败";


    public static final String
            //常规操作
            DELETE = "删除",
            COMMIT = "提交",
            INSERT = "添加",
            UPDATE = "修改",
            SELECT = "查询",
            CANCEL = "取消",
            GENERATE = "生成",
            PUBLISH = "发布",
            //登录操作
            SIGN_IN = "登录",
            SIGN_OUT = "注销",
            SIGN_UP = "注册",
            //泛称 默认操作
            DO = "操作",
            //校验操作
            CHECK = "校验",
            REVIEW = "审核",
            CONFIRM = "确认",
            RECALL = "撤回",
            //通知
            SEND = "发送",
            PUSH = "推送",
            NOTIFY = "通知",
            //报表
            EXPORT = "导出",
            IMPORT = "导入",
            //流程
            URGE = "催促",
            REVOKE = "撤销",

            //绑定操作
            MATCH = "匹配",
            BIND = "绑定",
            UNBIND = "解绑",
            //
            AUTHORIZATION = "授权",
            //支付
            PAY = "支付",
            TRANSFER = "转账",
            WITHDRAW = "提现",
            REFUND = "退款",
            //双向操作
            DISABLE = "禁用",
            ENABLE = "启用",
            UPLOAD = "上传",
            DOWNLOAD = "下载";


    /**
     * 自动选择类型 只适用于 添加和修改
     *
     * @param id 需要判断的值
     */
    public static String AUTO_CHOOSE(CharSequence id) {
        return StringUtil.isNotBlank(id) ? OperationType.UPDATE : OperationType.INSERT;
    }

    /**
     * 自动选择类型 只适用于 添加和修改
     *
     * @param condition 需要判断的值
     */
    public static String AUTO_CHOOSE(boolean condition) {

        return condition ? OperationType.UPDATE : OperationType.INSERT;
    }

    /**
     * 判断是否成功
     *
     * @param isSuccess
     */
    public static String isSuccess(String operationType, boolean isSuccess) {
        return operationType.concat(isSuccess ? SUCCESS : FAIL);
    }
}
