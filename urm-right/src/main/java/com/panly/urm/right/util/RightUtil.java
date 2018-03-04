package com.panly.urm.right.util;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import com.panly.urm.right.domain.Application;
import com.panly.urm.right.service.RightService;
import com.panly.urm.right.service.UserService;
import com.panly.urm.tran.auth.TreeDTO;

public class RightUtil implements ApplicationContextAware,EnvironmentAware{

	private static ApplicationContext context;
	
	private static Environment env;
	
	private static RightService rightService;
	
	private static UserService userService;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		context = applicationContext;
		rightService = context.getBean(RightService.class);
		userService = context.getBean(UserService.class);
	}
	
	@Override
	public void setEnvironment(Environment environment) {
		env = environment;
	}

	/**
	 * 获取应用基础信息
	 * @return
	 */
	public static Application getApplication(){
		return context.getBean(Application.class);
	}
	
	/**
	 * 获取应用functree
	 * @return
	 */
	public static List<TreeDTO> getAppTree(){
		List<TreeDTO> list = rightService.getAppFuncTree(getApplication().getAppCode());
		changeTreeVo(list);
		return list;
	}
	
	/**
	 * 获取应用acct functree
	 * @return
	 */
	public static List<TreeDTO> getAcctTree(){
		Long acctId = userService.getAcctId();
		List<TreeDTO> list = rightService.getAcctFuncTree(acctId, getApplication().getAppCode());
		changeTreeVo(list);
		return list;
	}
	
	
	/**
	 * 通过配置文件中配置，修改 funcTree中url和 icon
	 * @param list
	 */
	private static void changeTreeVo(List<TreeDTO> list){
		if(list!=null&&list.size()>0){
			for (int i = 0; i < list.size(); i++) {
				TreeDTO vo = list.get(i);
				String funcCode = vo.getCode();
				String url = env.getProperty("menu.url."+funcCode);
				String icon = env.getProperty("menu.icon."+funcCode,"fa-laptop");
				vo.setUrl(url);
				vo.setIcon(icon);
				changeTreeVo(vo.getNodes());
			}
		}
	}
	
	
}
