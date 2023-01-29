package cn.minsin.core.tools.secure;

import java.util.function.BiConsumer;

/**
 * basic身份验证
 *
 * @author minsin/mintonzhang@163.com
 * @since 2022/6/20
 */
public interface BasicAuthentication {

    static String encrypt(String username, String password) {
        String encode = Base64Util.encode(username + ":" + password);
        return "Basic ".concat(encode);
    }

    static void encrypt(String username, String password, BiConsumer<String, String> consumer) {
        consumer.accept("Authorization", encrypt(username, password));
    }
}
