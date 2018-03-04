package com.panly.urm.demo.menu;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.panly.urm.right.login.TokenInterceptor;
import com.panly.urm.right.util.RightUtil;
import com.panly.urm.tran.auth.TreeDTO;


public class MenuInterceptor implements HandlerInterceptor  {
	
	public static Cache<String, List<TreeDTO>> cache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterAccess(300, TimeUnit.SECONDS)
            .recordStats()
            .build();
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if(modelAndView !=null){
			String token = TokenInterceptor.getToken(request);
			List<TreeDTO> menus = cache.getIfPresent(token);
			if(menus ==null){
				 menus = RightUtil.getAcctTree();
				 cache.put(token, menus);
			}
			modelAndView.addObject("menus", menus);
			
			if(handler.getClass().isAssignableFrom(HandlerMethod.class) ){
				HandlerMethod handlerMethod = (HandlerMethod) handler;
				MenuAnno menuAnno = handlerMethod.getMethodAnnotation(MenuAnno.class);
				if(menuAnno==null){
					menuAnno = handlerMethod.getBeanType().getAnnotation(MenuAnno.class);
				}
				if(menuAnno!=null){
					request.setAttribute("menuCode", menuAnno.value());
				}
			}
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
