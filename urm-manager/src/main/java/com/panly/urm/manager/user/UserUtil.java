package com.panly.urm.manager.user;

import com.panly.urm.common.WebUtil;
import com.panly.urm.manager.user.login.LoginObject;

public class UserUtil {

	private static ThreadLocal<LoginObject> t = new ThreadLocal<>();

	public static void init(LoginObject o) {
		t.set(o);
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
			return null;
		}
	}

	/**
	 * 获取访问ip
	 * 
	 * @return
	 */
	public static String getVisitIp() {
		return WebUtil.getRemoteAddr();
	}

	public static boolean isLogin() {
		return t.get() != null;
	}

}
