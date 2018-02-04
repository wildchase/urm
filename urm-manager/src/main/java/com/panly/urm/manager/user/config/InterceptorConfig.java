package com.panly.urm.manager.user.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.panly.urm.manager.user.filter.MenuInterceptor;
import com.panly.urm.manager.user.filter.TokenInterceptor;
import com.panly.urm.manager.user.service.TokenService;

@Configuration
public class InterceptorConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	private TokenService tokenService;
	
	@Value("${login.url}")
	private String loginUrl;
	
	@Value("${login.exclude.url}")
	private String loginExcludeUrl;
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		TokenInterceptor tokenInterceptor = new TokenInterceptor();
		tokenInterceptor.setLoginUrl(loginUrl);
		tokenInterceptor.setTokenService(tokenService);
		
		registry.addInterceptor(tokenInterceptor).addPathPatterns("/**").excludePathPatterns(StringUtils.tokenizeToStringArray(loginExcludeUrl, ","));
        registry.addInterceptor(new MenuInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
	
}
