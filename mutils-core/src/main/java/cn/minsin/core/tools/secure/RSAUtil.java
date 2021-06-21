package cn.minsin.core.tools.secure;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Objects;
import java.util.function.BiFunction;

/**
 * EncryptUtil
 * 加解密工具类
 *
 * @author yizzuide
 * @since 1.13.0
 * Create at 2019/09/21 19:58
 */
@Slf4j
public class RSAUtil {


    /**
     * SHA128
     */
    public static final String SIGN_TYPE_RSA1 = "SHA1WithRSA";

    /**
     * SHA256
     */
    public static final String SIGN_TYPE_RSA2 = "SHA256WithRSA";


    public static void genKeyPair() throws NoSuchAlgorithmException {
        genKeyPair((a, b) -> {
            System.out.println("publicKey:\n" + a);
            System.out.println("privateKey:\n" + b);
            return null;
        });
    }

    public static <T> T genKeyPair(BiFunction<String, String, T> function) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGen;
        keyPairGen = KeyPairGenerator.getInstance("RSA");
        keyPairGen.initialize(1024, new SecureRandom());
        KeyPair keyPair = keyPairGen.generateKeyPair();
        String priKey = Base64Util.encode(keyPair.getPrivate().getEncoded());
        String pubKey = Base64Util.encode(keyPair.getPublic().getEncoded());
        return function.apply(pubKey, priKey);
    }

    /**
     * RAS公钥加密
     *
     * @param pubKey base64公钥
     * @param data   原始数据
     * @return 加密后的base64
     */
    public static String encrypt(String pubKey, String data) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Objects.requireNonNull(Base64Util.decode(pubKey)));
        byte[] output;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);

            output = blockCodec(cipher, Cipher.ENCRYPT_MODE, data.getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            log.info("Encrypt happen exception: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return Base64Util.encode(output);
    }

    /**
     * RSA私钥解密
     *
     * @param priKey       base64私钥
     * @param encryptedStr 加密后的base64
     * @return 原始数据
     */
    public static String decrypt(String priKey, String encryptedStr) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Objects.requireNonNull(Base64Util.decode(priKey)));
        String source;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] output = blockCodec(cipher, Cipher.DECRYPT_MODE, Base64Util.decode(encryptedStr));
            source = new String(output, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.info("Encrypt happen exception: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return source;
    }

    /**
     * 数据分段加解密
     *
     * @param cipher   Cipher
     * @param mode     加解密模式
     * @param dataByte 数据源
     * @return 目标数据
     */
    private static byte[] blockCodec(Cipher cipher, int mode, byte[] dataByte) {
        int maxBlock;
        if (mode == Cipher.DECRYPT_MODE) {
            maxBlock = 128;
        } else {
            maxBlock = 117;
        }
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            int offSet = 0;
            byte[] buff;
            int i = 0;
            while (dataByte.length > offSet) {
                if (dataByte.length - offSet > maxBlock) {
                    buff = cipher.doFinal(dataByte, offSet, maxBlock);
                } else {
                    buff = cipher.doFinal(dataByte, offSet, dataByte.length - offSet);
                }
                out.write(buff, 0, buff.length);
                i++;
                offSet = i * maxBlock;
            }
            return out.toByteArray();
        } catch (Exception e) {
            throw new RuntimeException("加解密块大小为[" + maxBlock + "]时发生异常", e);
        }
    }

    /**
     * 生成签名
     *
     * @param data     签名数据
     * @param priKey   base64私钥
     * @param signType 签名类型，使用常量：SIGN_TYPE_RSA1、SIGN_TYPE_RSA2
     * @return 签的base64
     */
    public static String sign(String data, String priKey, String signType) {
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(Objects.requireNonNull(Base64Util.decode(priKey)));
        byte[] sign;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Signature signature = Signature.getInstance(signType);
            signature.initSign(privateKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            sign = signature.sign();
        } catch (Exception e) {
            log.info("Encrypt happen exception: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return Base64Util.encode(sign);
    }

    /**
     * 验签
     *
     * @param data     签名数据
     * @param sign     签名base64串
     * @param pubKey   base64公钥
     * @param signType 签名类型，使用常量：SIGN_TYPE_RSA1、SIGN_TYPE_RSA2
     * @return 验证签名结果
     */
    public static boolean verify(String data, String sign, String pubKey, String signType) {
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(Objects.requireNonNull(Base64Util.decode(pubKey)));
        boolean verify;
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Signature signature = Signature.getInstance(signType);
            signature.initVerify(publicKey);
            signature.update(data.getBytes(StandardCharsets.UTF_8));
            verify = signature.verify(Base64Util.decode(sign));
        } catch (Exception e) {
            log.info("Encrypt happen exception: {}", e.getMessage(), e);
            throw new RuntimeException(e.getMessage());
        }
        return verify;
    }


}