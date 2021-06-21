package cn.minsin.core.tools.secure;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;


/**
 * @author minton.zhang
 * @since 2021/5/25 15:32
 */
public class AESUtil {

    static {
        //避免低版本的jdk8 无法使用
        java.security.Security.setProperty("crypto.policy", "unlimited");
    }


    /**
     * 生产aes秘钥
     *
     * @return aes秘钥串
     */
    public static String generateKey() {
        KeyGenerator keyGenerator;
        try {
            keyGenerator = KeyGenerator.getInstance("aes");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e.getMessage());
        }
        keyGenerator.init(256);
        SecretKey secretKey = keyGenerator.generateKey();
        return Base64Util.encode(secretKey.getEncoded());
    }

    /**
     * AES (默认AES/ECB/PKCS5Padding)加密并 Base64
     *
     * @param aesKey  base64密钥
     * @param content 需要加密的文本
     * @return base64编码
     */
    public static String encrypt(String aesKey, String content) {
        Key key = new SecretKeySpec(Objects.requireNonNull(Base64Util.decode(aesKey)), "aes");
        byte[] result;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // 加密模式
            cipher.init(Cipher.ENCRYPT_MODE, key);
            result = cipher.doFinal(content.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return Base64Util.encode(result);
    }

    /**
     * AES (默认AES/ECB/PKCS5Padding)解密
     *
     * @param aesKey          base64密钥
     * @param encode64Content base64编码密文
     * @return 解码后的明文
     */
    public static String decrypt(String aesKey, String encode64Content) {
        Key key = new SecretKeySpec(Objects.requireNonNull(Base64Util.decode(aesKey)), "aes");
        String source;
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // 解密模式
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] result = cipher.doFinal(Objects.requireNonNull(Base64Util.decode(encode64Content)));
            source = new String(result, StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
        return source;
    }

}
