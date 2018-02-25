package com.panly.urm.right.common;

public class RightUrlUtil {

	public static String getJdbcUrl(RightURL u){
		if(u ==null){
			return "";
		}
		return "jdbc:"+u.getProtocol()+"://"+u.getHost()+":"+u.getPort()+u.getPath();
	}

}
