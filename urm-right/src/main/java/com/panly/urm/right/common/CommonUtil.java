package com.panly.urm.right.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.lang3.StringUtils;

public class CommonUtil {

	private static final String INIT_PARAM_DELIMITERS = ",; \t\n";

	private static String ip = null;

	/**
	 * 将 config split 成数组 ,; \t\n 支持5中拆分方式，自动trim
	 * 
	 * @param config
	 * @return
	 */
	public static String[] splitToArray(String config) {
		return org.springframework.util.StringUtils.tokenizeToStringArray(config, INIT_PARAM_DELIMITERS);
	}

	/**
	 * 服务器ip
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getCurrentIp() {
		if (ip == null) {
			String localip = null; // 本地IP，如果没有配置外网IP则返回它
			String netip = null; // 外网IP
			try {
				Enumeration netInterfaces = NetworkInterface.getNetworkInterfaces();
				InetAddress ip = null;
				boolean finded = false;// 是否找到外网IP
				while (netInterfaces.hasMoreElements() && !finded) {
					NetworkInterface ni = (NetworkInterface) netInterfaces.nextElement();
					Enumeration address = ni.getInetAddresses();
					while (address.hasMoreElements()) {
						ip = (InetAddress) address.nextElement();
						if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
								&& ip.getHostAddress().indexOf(":") == -1) {// 外网IP
							netip = ip.getHostAddress();
							finded = true;
							break;
						} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress()
								&& ip.getHostAddress().indexOf(":") == -1) {// 内网IP
							localip = ip.getHostAddress();
						}
					}
				}
			} catch (SocketException ex) {
				throw new RuntimeException("获取ip出错!", ex);
			}
			ip = StringUtils.isNotBlank(netip) ? netip : localip;
		}
		return ip;
	}

}
