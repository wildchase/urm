package com.panly.urm.manager.user;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.panly.umr.common.WebUtil;
import com.panly.urm.manager.user.model.Token;

public class UserUtil {

	private static ThreadLocal<Token> t = new ThreadLocal<>();

	public static void init(Token token) {
		t.set(token);
	}

	public static void remove() {
		t.remove();
	}

	public static Long getUserId() {
		if (t.get() != null) {
			return t.get().getUserId();
		} else {
			return null;
		}
	}

	public static String getUserName() {
		if (t.get() != null) {
			return t.get().getUserName();
		} else {
			return "";
		}
	}

	public static String getAccount() {
		if (t.get() != null) {
			return t.get().getAccount();
		} else {
			return "";
		}
	}

	public static String getToken() {
		if (t.get() != null) {
			return t.get().getToken();
		} else {
			return "";
		}
	}

	/**
	 * 获取访问ip
	 * 
	 * @return
	 */
	public static String getVisitIp() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		if (request != null) {
			return WebUtil.getRemoteAddr(request);
		}
		return "";
	}

	public static boolean isLogin() {
		return t.get() != null;
	}

}
