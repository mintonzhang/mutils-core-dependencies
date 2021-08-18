package cn.minsin.core.tools.secure;

import org.apache.commons.lang3.StringUtils;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author minton.zhang
 * @since 2021/5/25 16:33
 */
public class Base64Util {

    /**
     * base64编码
     *
     * @param data 编码源
     * @return base64编码
     */
    public static String encode(byte[] data) {
        return Base64.getEncoder().encodeToString(data);
    }

    /**
     * base64编码
     *
     * @param data 编码源
     * @return base64编码
     */
    public static String encode(String data) {
        return encode(data.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * base64解码
     *
     * @param data 解码源字符串
     * @return base64编码密文
     */
    public static byte[] decode(String data) {
        try {
            return StringUtils.isEmpty(data) ? null : Base64.getDecoder().decode(data);
        } catch (IllegalArgumentException e) {
            return null;
        }
    }

    /**
     * base64解码
     *
     * @param data 解码源字符串
     * @return base64编码密文
     */
    public static String decode2String(String data) {
        byte[] decode = decode(data);
        return decode == null ? null : new String(decode, StandardCharsets.UTF_8);
    }
}
