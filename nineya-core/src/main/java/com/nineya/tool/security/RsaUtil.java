package com.nineya.tool.security;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * rsa加密解密工具类
 *
 * @author 殇雪话诀别
 * 2021/2/24
 */
public class RsaUtil {
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private static final String ENCRYPT_TYPE = "RSA";

    public RsaUtil(String priKey, String pubKey) {
        setKey(priKey, pubKey);
    }

    public void setKey(String priKey, String pubKey) {
        this.privateKey = privateStringToKey(priKey);
        this.publicKey = publicStringToKey(pubKey);
    }

    /**
     * 使用公钥进行加密
     *
     * @return
     */
    public String encrypt(String data) {
        return encrypt(publicKey, data);
    }

    /**
     * 使用公钥进行加密
     *
     * @param publicKey 公钥
     * @param data      消息内容
     * @return
     */
    public String encrypt(String publicKey, String data) {
        PublicKey key = publicStringToKey(publicKey);
        return encrypt(key, data);
    }

    /**
     * 使用私钥进行界面
     *
     * @param data
     * @return
     */
    public String decrypt(String data) {
        return decrypt(privateKey, data);
    }

    /**
     * 使用私钥进行界面
     *
     * @param privateKey 私钥
     * @param data
     * @return
     */
    public String decrypt(String privateKey, String data) {
        PrivateKey key = privateStringToKey(privateKey);
        return decrypt(key, data);
    }

    /**
     * 使用私钥进行签名
     *
     * @param data
     * @return
     */
    public String sign(String data) {
        return encrypt(privateKey, data);
    }

    /**
     * 使用私钥进行签名
     *
     * @param privateKey 私钥
     * @param data       消息内容
     * @return
     */
    public String sign(String privateKey, String data) {
        PrivateKey key = privateStringToKey(privateKey);
        return encrypt(key, data);
    }

    /**
     * 使用公钥进行认证
     *
     * @param data
     * @return
     */
    public String certification(String data) {
        return decrypt(publicKey, data);
    }

    /**
     * 使用公钥进行认证
     *
     * @param publicKey
     * @param data
     * @return
     */
    public String certification(String publicKey, String data) {
        PublicKey key = publicStringToKey(publicKey);
        return decrypt(key, data);
    }

    /**
     * 实际执行加密的方法
     *
     * @param key  公钥加密，私钥签名
     * @param data 数据内容
     * @return
     */
    public String encrypt(Key key, String data) {
        try {
            byte[] bytes = data.getBytes(StandardCharsets.UTF_8);
            if (key != null) {
                try {
                    Cipher cipher = Cipher.getInstance(ENCRYPT_TYPE);
                    cipher.init(Cipher.ENCRYPT_MODE, key);
                    byte[] resultBytes = cipher.doFinal(bytes);
                    return Base64.getEncoder().encodeToString(resultBytes);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 实际进行解密的方法
     *
     * @param key  私钥解密，公钥认证
     * @param data 数据内容
     * @return
     */
    public String decrypt(Key key, String data) {
        byte[] bytes = Base64.getDecoder().decode(data);
        if (key != null) {
            try {
                Cipher cipher = Cipher.getInstance("RSA");
                cipher.init(Cipher.DECRYPT_MODE, key);
                byte[] resultBytes = cipher.doFinal(bytes);
                return new String(resultBytes, StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 将String转公钥类型
     *
     * @param pubKey 公钥
     * @return
     */
    public PublicKey publicStringToKey(String pubKey) {
        byte[] key = Base64.getDecoder().decode(pubKey);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(key);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPT_TYPE);
            return keyFactory.generatePublic(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将String转私钥类型
     *
     * @param priKey
     * @return
     */
    public PrivateKey privateStringToKey(String priKey) {
        byte[] key = Base64.getDecoder().decode(priKey);
        PKCS8EncodedKeySpec keySpec2 = new PKCS8EncodedKeySpec(key);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPT_TYPE);
            return keyFactory.generatePrivate(keySpec2);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        return null;
    }
}
