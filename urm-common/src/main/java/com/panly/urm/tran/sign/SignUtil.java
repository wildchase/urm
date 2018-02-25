package com.panly.urm.tran.sign;

import java.io.IOException;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Map;

public class SignUtil {
	
	//对所有API请求参数（包括公共参数和业务参数，但除去sign参数和byte[]类型的参数），根据参数名称的ASCII码表的顺序排序。如：foo:1, bar:2, foo_bar:3, foobar:4排序后的顺序是bar:2, foo:1, foo_bar:3, foobar:4。
	public static String signTopRequest(Map<String, String> params, String signMethod)
			throws IOException {
		
		// 第一步：检查参数是否已经排序
		String[] keys = params.keySet().toArray(new String[0]);
		Arrays.sort(keys);
		
		// 第二步：把所有参数名和参数值串在一起
		StringBuilder query = new StringBuilder();
		
		for (String key : keys) {
			String value = params.get(key);
			if (areNotEmpty(key, value)) {
				query.append(key).append(value);
			}
		}
		
		// 第三步：使用MD5加密
		byte[] bytes = encryptMD5(query.toString());
		
		return byte2hex(bytes);
	}

	private static boolean areNotEmpty(String key, String value) {
		if(key!=null&&!key.equals("")&&value!=null&&!value.equals("")){
			return true;
		}
		return false;
	}

	public static byte[] encryptMD5(String data) throws IOException {
		return encryptMD5(data.getBytes(SignConstants.CHARSET_UTF8));
	}

	private static byte[] encryptMD5(byte[] bytes) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(bytes);
			return md.digest();
		} catch (Exception e) {
			return null;
		}
	}
	
	public static String byte2hex(byte[] bytes) {
		StringBuilder sign = new StringBuilder();
		for (int i = 0; i < bytes.length; i++) {
			String hex = Integer.toHexString(bytes[i] & 0xFF);
			if (hex.length() == 1) {
				sign.append("0");
			}
			sign.append(hex.toUpperCase());
		}
		return sign.toString();
	}

}
