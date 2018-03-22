package com.panly.urm.demo.config;

import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.panly.urm.demo.web.AsyncServlet;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

	@Bean
	public ServletRegistrationBean testServletRegistration() {
		ServletRegistrationBean registration = new ServletRegistrationBean(new AsyncServlet());
		return registration;
	}

}
