package com.panly.urm.manager.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.panly.urm.manager.user.filter.MenuInterceptor;
import com.panly.urm.manager.user.filter.LoginInterceptor;
import com.panly.urm.manager.user.login.LoginUtil;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
	
	@Value("${login.url}")
	private String loginUrl;
	
	@Value("${login.exclude.url}")
	private String loginExcludeUrl;
	
	@Autowired
	private LoginUtil loginUtil;
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		LoginInterceptor tokenInterceptor = new LoginInterceptor();
		tokenInterceptor.setLoginUrl(loginUrl);
		tokenInterceptor.setLoginUtil(loginUtil);
		
		registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns(StringUtils.tokenizeToStringArray(loginExcludeUrl, ","));
        registry.addInterceptor(new MenuInterceptor()).addPathPatterns("/**").excludePathPatterns("/res/**");
        super.addInterceptors(registry);
    }
	
}
