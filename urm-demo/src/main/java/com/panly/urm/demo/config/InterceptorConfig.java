package com.panly.urm.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.panly.urm.demo.menu.MenuInterceptor;
import com.panly.urm.right.login.TokenInterceptor;


@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
	
	@Value("${login.url}")
	private String loginUrl;
	
	@Value("${login.check.token.url}")
	private String checkTokenUrl;
	
	@Value("${login.exclude.url}")
	private String loginExcludeUrl;
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		TokenInterceptor tokenInterceptor = new TokenInterceptor();
		tokenInterceptor.setLoginUrl(loginUrl);
		tokenInterceptor.setCheckTokenUrl(checkTokenUrl);
		
		registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns(StringUtils.tokenizeToStringArray(loginExcludeUrl, ","));
        
		MenuInterceptor menuInterceptor = new MenuInterceptor();
		registry.addInterceptor(menuInterceptor).addPathPatterns("/**");
		
		super.addInterceptors(registry);
    }
	
}
