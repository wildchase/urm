package com.panly.urm.auth;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.Banner.Mode;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@ImportResource({ "classpath*:*/**/*.spring.xml" })
@SpringBootApplication(scanBasePackages = "com.panly.urm")
@PropertySource(value={
		"classpath:/config/config.properties",
		"classpath:/config/${spring.profiles.active}/config.properties"
	})
public class AuthMain implements EnvironmentAware  {
	
	private final static Logger logger = LoggerFactory.getLogger(AuthMain.class);
	
	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(AuthMain.class);
		app.setBannerMode(Mode.OFF);
		app.run(args);
    }

	@Override
	public void setEnvironment(Environment environment) {
		logger.info("logging.config:{}",environment.getProperty("logging.config"));
	}

}
