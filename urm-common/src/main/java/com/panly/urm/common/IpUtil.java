package com.panly.urm.common;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

import org.apache.commons.lang.StringUtils;

/**
 * <p>
 * 获取当前服务器的ip地址
 * </p>
 *
 * @project core
 * @class IpUtil
 * @author a@panly.me
 * @date 2017年7月31日上午10:32:56
 */
public class IpUtil {

	/**
	 * 服务器ip
	 * 
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String getIp() {
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
					if (!ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
						netip = ip.getHostAddress();
						finded = true;
						break;
					} else if (ip.isSiteLocalAddress() && !ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
						localip = ip.getHostAddress();
					}
				}
			}
		} catch (SocketException ex) {
			throw new RuntimeException("获取ip出错!", ex);
		}

		return StringUtils.isNotBlank(netip) ? netip : localip;
	}

}
