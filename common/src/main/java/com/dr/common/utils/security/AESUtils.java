package com.dr.common.utils.security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * @author ylw
 * @description AES加密工具
 * @date 2019/5/2715:06
 */
public class AESUtils {
    private static final String ALG_NAME = "AES";
    private static final int ALG_KEY_SIZE = 128;
    public static final String DEFAULT_KEY = "eLCrQHDHoBSJgJa+GaS1Fw==";
    private static final String ALG_FULL_NAME_AES_CBC_PKCS5 = "AES/CBC/PKCS5Padding";
    private static final byte[] AES_IV = initIv("AES/CBC/PKCS5Padding");

    public AESUtils() {
    }

    private static byte[] initIv(String algFullName) {
        byte[] iv;
        int i;
        try {
            Cipher cipher = Cipher.getInstance(algFullName);
            int blockSize = cipher.getBlockSize();
            iv = new byte[blockSize];

            for(i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }

            return iv;
        } catch (Exception var5) {
            int blockSize = 16;
            iv = new byte[blockSize];

            for(i = 0; i < blockSize; ++i) {
                iv[i] = 0;
            }

            return iv;
        }
    }

    public static String generatorKey() throws Exception {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
            keyGenerator.init(128);
            return Base64.getEncoder().encodeToString(keyGenerator.generateKey().getEncoded());
        } catch (Exception var1) {
            throw new Exception("AES生成密钥失败：keySize = 128", var1);
        }
    }

    public static String encrypt(String content, String key, String charset) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(1, new SecretKeySpec(Base64.getDecoder().decode(key.getBytes()), "AES"), iv);
            byte[] encBytes = cipher.doFinal(content.getBytes(charset));
            return Base64.getEncoder().encodeToString(encBytes);
        } catch (Exception var6) {
            throw new Exception("AES加密失败：AesContent = " + content + "; charset = " + charset, var6);
        }
    }

    public static String decrypt(String content, String key, String charset) throws Exception {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(AES_IV);
            cipher.init(2, new SecretKeySpec(Base64.getDecoder().decode(key.getBytes()), "AES"), iv);
            byte[] cleanBytes = cipher.doFinal(Base64.getDecoder().decode(content.getBytes()));
            return new String(cleanBytes, charset);
        } catch (Exception var6) {
            throw new Exception("AES解密失败：AesContent = " + content + "; charset = " + charset, var6);
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println(encrypt("111", "eLCrQHDHoBSJgJa+GaS1Fw==", "UTF-8"));
    }
}
