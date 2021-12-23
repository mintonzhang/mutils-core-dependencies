package cn.minsin.core.tools.regular;

import cn.minsin.core.tools._assert.CA;

import java.util.regex.Pattern;

public interface RegularExpWrapper {


    Pattern getPattern();

    default boolean isMatched(CharSequence charSequence) {
        return this.getPattern().matcher(charSequence).matches();
    }

    /**
     * 正则表达式验证
     *
     * @param charSequence 被验证的字符串
     * @param errorBool    错误的bool 有可能true是异常 false异常
     * @param message      错误消息
     * @param params       错误消息提示
     */
    default void isMatched(CharSequence charSequence, boolean errorBool, String message, Object... params) {
        boolean matches = this.getPattern().matcher(charSequence).matches();
        CA.isTrue(matches == errorBool, message, params);
    }
}
