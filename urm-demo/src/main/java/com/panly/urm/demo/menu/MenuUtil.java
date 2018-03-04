package com.panly.urm.demo.menu;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.panly.urm.right.login.TokenInterceptor;
import com.panly.urm.tran.auth.TreeDTO;


public class MenuUtil {

	public boolean isActive(String chooseMenu,String code){
		if(StringUtils.equals(chooseMenu, code))
		{
			return true;
		}		
		//查找 code 的上级 menu 
		
		
		
		
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		if(request==null){
			return false;
		}
		String token = TokenInterceptor.getToken(request);
		List<TreeDTO> menus = MenuInterceptor.cache.getIfPresent(token);
		Set<String> parentMenus = getParentMenus(code,menus);
		if(parentMenus.contains(chooseMenu)){
			return true;
		}
		return false;
	}

	private Set<String> getParentMenus(String code, List<TreeDTO> menus) {
		Set<String> sets = new HashSet<>();
		for (int i = 0; i < menus.size(); i++) {
			
			
		}
		return null;
	}
	
}
