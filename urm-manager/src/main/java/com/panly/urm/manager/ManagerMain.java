package com.panly.urm.manager;

import org.springframework.boot.Banner.Mode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

@ImportResource({ "classpath*:*/**/*.spring.xml" })
@SpringBootApplication(scanBasePackages = "com.panly.urm")
@PropertySource(value={
		"classpath:/config/config.properties",
		"classpath:/config/${spring.profiles.active}/config.properties"
	})
public class ManagerMain   {
	
	public static void main(String[] args) throws Exception {
		SpringApplication app = new SpringApplication(ManagerMain.class);
		app.setBannerMode(Mode.OFF);
		app.run(args);
    }


}
