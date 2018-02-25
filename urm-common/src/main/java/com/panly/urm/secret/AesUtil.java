package com.panly.urm.secret;


import java.security.Key;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.lang3.StringUtils;

import com.panly.urm.secret.base64.Base64Util;



/**
 * 
 * <p>
 * AES 加解密
 * </p>
 *
 * @project core
 * @class AesUtil
 * @author a@panly.me
 * @date 2017年5月27日 下午3:38:45
 */
public class AesUtil {

	public static final String KEY_ALGORITHM = "AES";

	public static final String CIPHER_ALGORITHM = "AES/ECB/PKCS7Padding";

	/**
	 * aes 加密 encrypt
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String encrypt(String data, String key) {
		try {
			Key k = toKey(key);
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
			cipher.init(Cipher.ENCRYPT_MODE, k);
			byte[] dataBytes = data.getBytes("UTF-8");
			byte[] encryptData = cipher.doFinal(dataBytes);
			return Base64Util.encodeByteToStr(encryptData);
		} catch (Exception ex) {
			throw new RuntimeException("aes加密错误!", ex);
		}
	}

	/**
	 * aes 解密 decrypt
	 * 
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(String encryptData, String key) throws RuntimeException {
		try {
			Key k = toKey(key);
			/**
			 * 实例化 使用 PKCS7PADDING 填充方式，按如下方式实现,就是调用bouncycastle组件实现
			 * Cipher.getInstance(CIPHER_ALGORITHM,"BC")
			 */
			Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
			Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM, "BC");
			cipher.init(Cipher.DECRYPT_MODE, k);
			byte[] encryptDataBytes = Base64Util.decodeStrToByte(encryptData);
			byte[] r = cipher.doFinal(encryptDataBytes);
			return new String(r, "UTF-8");
		} catch (RuntimeException rex) {
			throw rex;
		} catch (Exception ex) {
			throw new RuntimeException("aes解密错误!", ex);
		}
	}

	/**
	 * 将key转换成 16位的大小
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	private static Key toKey(String key) throws Exception {
		if (StringUtils.isEmpty(key)) {
			throw new IllegalArgumentException("密钥参数为空");
		}
		key = cutLength(key, 16);
		byte[] keyBytes = key.getBytes();
		SecretKey secretKey = new SecretKeySpec(keyBytes, KEY_ALGORITHM);
		return secretKey;
	}

	private static String cutLength(String key, int length) {
		if (key.length() >= length) {
			return key.substring(0, length);
		} else {
			StringBuilder sb = new StringBuilder(key);
			for (int i = 0; i < length - key.length(); i++) {
				sb.append("0");
			}
			return sb.toString();
		}
	}

}
