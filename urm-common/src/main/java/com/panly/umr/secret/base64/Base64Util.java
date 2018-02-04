package com.panly.umr.secret.base64;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

/**
 * @description
 * 				<p>
 *              Base64工具类
 *              </p>
 *
 * @project core
 * @class Base64
 * @author a@panly.me
 * @date 2017年5月27日 下午3:27:59
 */
public class Base64Util {

	/**
	 * 将byte数组转换成 base64码str
	 * 
	 * @param bytes
	 * @return
	 * @throws CommonException
	 */
	public static String encodeByteToStr(byte[] bytes) throws RuntimeException {
		try {
			return new String(encode(bytes), "ASCII");
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("ASCII is not supported!", ex);
		}
	}

	/**
	 * base64码str转换成 byte数组
	 * 
	 * @param str
	 * @return
	 * @throws CommonException
	 */
	public static byte[] decodeStrToByte(String str) throws RuntimeException {
		try {
			byte[] bytes = str.getBytes("ASCII");
			return decode(bytes);
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("ASCII is not supported!", ex);
		}
	}

	/**
	 * 转换str成base64码
	 * 
	 * @param str
	 * @return
	 * @throws CommonException
	 */
	public static String encode(String str) throws RuntimeException {
		byte[] bytes = str.getBytes();
		byte[] encoded = encode(bytes);
		try {
			return new String(encoded, "ASCII");
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("ASCII is not supported!", ex);
		}
	}

	/**
	 * 将base64编码数据解码
	 * 
	 * @param str
	 * @return
	 * @throws CommonException
	 */
	public static String decode(String str) throws RuntimeException {
		try {
			byte[] bytes = str.getBytes("ASCII");
			return new String(decode(bytes));
		} catch (UnsupportedEncodingException ex) {
			throw new RuntimeException("ASCII is not supported!", ex);
		}

	}

	/**
	 * 将原始数组转换成 base64数组
	 * 
	 * @param bytes
	 * @return
	 * @throws CommonException
	 */
	public static byte[] encode(byte[] bytes) throws RuntimeException {
		return encode(bytes, 0);
	}

	/**
	 */
	public static byte[] encode(byte[] bytes, int wrapAt) throws RuntimeException {
		ByteArrayInputStream inputStream = null;
		ByteArrayOutputStream outputStream = null;
		try {
			inputStream = new ByteArrayInputStream(bytes);
			outputStream = new ByteArrayOutputStream();
			encode(inputStream, outputStream, wrapAt);
			return outputStream.toByteArray();
		} catch (IOException ex) {
			throw new RuntimeException("Unexpected I/O error", ex);
		} finally {
			closeQuietly(inputStream);
			closeQuietly(outputStream);
		}

	}

	/**
	 * 解码byte
	 * 
	 * @param bytes
	 * @return
	 * @throws CommonException
	 */
	public static byte[] decode(byte[] bytes) throws RuntimeException {
		ByteArrayInputStream inputStream = null;
		ByteArrayOutputStream outputStream = null;
		try {
			inputStream = new ByteArrayInputStream(bytes);
			outputStream = new ByteArrayOutputStream();
			decode(inputStream, outputStream);
			return outputStream.toByteArray();
		} catch (IOException ex) {
			throw new RuntimeException("Unexpected I/O error", ex);
		} finally {
			closeQuietly(inputStream);
			closeQuietly(outputStream);
		}

	}

	/**
	 * 解码流
	 * 
	 * @param inputStream
	 * @param outputStream
	 * @throws IOException
	 */
	public static void encode(InputStream inputStream, OutputStream outputStream) throws IOException {
		encode(inputStream, outputStream, 0);
	}

	/**
	 * 编码流
	 * 
	 * @param inputStream
	 * @param outputStream
	 * @param wrapAt
	 * @throws IOException
	 */
	public static void encode(InputStream inputStream, OutputStream outputStream, int wrapAt) throws IOException {
		Base64OutputStream aux = new Base64OutputStream(outputStream, wrapAt);
		copy(inputStream, aux);
		aux.commit();
	}

	/**
	 * 解码流
	 * 
	 * @param inputStream
	 * @param outputStream
	 * @throws IOException
	 */
	public static void decode(InputStream inputStream, OutputStream outputStream) throws IOException {
		copy(new Base64InputStream(inputStream), outputStream);
	}

	/**
	 * 编码文件
	 * 
	 * @param source
	 * @param target
	 * @param wrapAt
	 * @throws IOException
	 */
	public static void encode(File source, File target, int wrapAt) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(target);
			Base64Util.encode(inputStream, outputStream, wrapAt);
		} finally {
			closeQuietly(inputStream);
			closeQuietly(outputStream);
		}
	}

	/**
	 * 编码文件
	 * 
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	public static void encode(File source, File target) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(target);
			Base64Util.encode(inputStream, outputStream);
		} finally {
			closeQuietly(inputStream);
			closeQuietly(outputStream);
		}
	}

	/**
	 * 解码文件
	 * 
	 * @param source
	 * @param target
	 * @throws IOException
	 */
	public static void decode(File source, File target) throws IOException {
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			inputStream = new FileInputStream(source);
			outputStream = new FileOutputStream(target);
			decode(inputStream, outputStream);
		} finally {
			closeQuietly(inputStream);
			closeQuietly(outputStream);
		}
	}

	/**
	 * copy stream
	 * 
	 * @param inputStream
	 * @param outputStream
	 * @throws IOException
	 */
	private static void copy(InputStream inp, OutputStream out) throws IOException {
		byte[] buff = new byte[4096];
		int count;
		while ((count = inp.read(buff)) != -1) {
			if (count > 0) {
				out.write(buff, 0, count);
			}
		}
	}

	protected static void closeQuietly(final Closeable closeable) {
		try {
			closeable.close();
		} catch (Exception exc) {
		}
	}

	
}
