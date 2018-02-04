package com.panly.urm.manager.user.filter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.panly.urm.manager.common.constants.StatusEnum;
import com.panly.urm.manager.user.anno.MenuOp;

public class MenuInterceptor implements HandlerInterceptor  {

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		if(handler.getClass().isAssignableFrom(HandlerMethod.class) ){
			HandlerMethod handlerMethod = (HandlerMethod) handler;
			MenuOp menuOp = handlerMethod.getMethodAnnotation(MenuOp.class);
			if(menuOp==null){
				menuOp = handlerMethod.getBeanType().getAnnotation(MenuOp.class);
			}
			if(menuOp!=null){
				request.setAttribute("menuId", menuOp.value());
			}
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null) {
			modelAndView.addObject("StatusEnum", StatusEnum.values());
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		
	}

}
