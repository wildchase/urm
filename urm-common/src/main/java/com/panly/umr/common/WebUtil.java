package com.panly.umr.common;


import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;



/**
 * @description 
 * <p> web工具类 </p> 
 *
 * @project 	core
 * @class 		WebUtil
 * @author 		a@panly.me
 * @date 		2017年5月27日 上午10:21:02
 */
public class WebUtil {

	
	private WebUtil(){};

	/**
	 * 是否contentType为json请求或者ajax请求
	 * @return
	 */
	public static boolean isJsonRequest(HttpServletRequest req) {
		String contentType = req.getContentType();
		if (StringUtils.contains(contentType, "json")) {
			return true;
		}
		String requestType = req.getHeader("X-Requested-With");
		if (StringUtils.isNotEmpty(requestType)) {
			return true;
		}
		// 上传文件是ajax 的请求， 对应的contentType 为  Content-Type:multipart/form-data; boundary=----WebKitFormBoundaryugckYFBKEyzA6etX
		if (StringUtils.contains(contentType, "multipart/form-data")) {
			return true;
		}
		return false;
	}


	/**
	 * 获取真实ip ，经过f5 或者 nginx 分发后，获取实际的访问ip
	 * @param request
	 * @return
	 */
	public static String getRemoteAddr(HttpServletRequest request) {
		String ip = request.getHeader("HTTP_X_FORWARDED_FOR"); // X-Real-IP
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("x-forwarded-for");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		// 多级反向代理
		if (null != ip && !"".equals(ip.trim())) {
			StringTokenizer st = new StringTokenizer(ip, ",");
			String ipTmp = "";
			if (st.countTokens() > 1) {
				while (st.hasMoreTokens()) {
					ipTmp = st.nextToken();
					if (ipTmp != null && ipTmp.length() != 0 && !"unknown".equalsIgnoreCase(ipTmp)) {
						ip = ipTmp;
						break;
					}
				}
			}
		}
		return ip;
	}
	
	
}
