package com.nanyin.config.util;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import java.security.Key;
import java.security.SecureRandom;

/**
 * @Auther: NanYin
 * @Date: 11/10/18 18:14
 * @Description: EDSUtil 加密解密  https://blog.csdn.net/zhangt85/article/details/42122311
 */
public class EDSUtil {
    private static Key key;
    static {
        try {
            KeyGenerator generator = KeyGenerator.getInstance(EDSEnum.DES.getName());
            SecureRandom secureRandom = SecureRandom.getInstance(EDSEnum.SHA1PRNG.getName());
            secureRandom.setSeed(EDSEnum.KEY_STR.getName().getBytes());
            generator.init(secureRandom);
            key = generator.generateKey();
            generator = null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEncryptString(String str) {
        BASE64Encoder base64Encoder = new BASE64Encoder();
        try {
            byte[] strBytes = str.getBytes(EDSEnum.CHARTSET.getName());
            Cipher cipher = Cipher.getInstance(EDSEnum.DES.getName());
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encryptStrBytes = cipher.doFinal(strBytes);
            return base64Encoder.encode(encryptStrBytes);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static String getDecryptString(String str){
        BASE64Decoder base64Decoder = new BASE64Decoder();
        try
        {
            byte[] strBytes = base64Decoder.decodeBuffer(str);
            Cipher cipher = Cipher.getInstance(EDSEnum.DES.getName());
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptStrBytes = cipher.doFinal(strBytes);
            return new String(encryptStrBytes,EDSEnum.CHARTSET.getName());
        }
        catch (Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        System.out.println(getEncryptString("1"));
        System.out.println(getDecryptString("m34jIHLyaWo="));
    }
}