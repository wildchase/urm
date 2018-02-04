package com.panly.umr.secret;

import java.security.MessageDigest;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * <p>
 * md5 转换
 * </p>
 *
 * @project core
 * @class Md5Util
 * @author a@panly.me
 * @date 2017年5月27日 下午3:32:30
 */
public class Md5Util {

	public static String encrpt(String msg) {
		try {
			if (StringUtils.isEmpty(msg)) {
				return null;
			}
			byte[] bytes = msg.getBytes("UTF-8");
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytes);
			byte[] result = md.digest();
			return HexUtil.bytesToHexString(result);
		} catch (Exception ex) {
			throw new RuntimeException("md5 error!", ex);
		}
	}

	public static String encrptWithSalt(String password, String salt) {
		return Md5Util.encrpt(password + "[" + salt + "]");
	}
}
