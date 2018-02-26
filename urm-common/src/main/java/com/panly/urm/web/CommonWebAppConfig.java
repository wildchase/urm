package com.panly.urm.web;

import java.nio.charset.Charset;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.panly.urm.prop.PropConfig;

@Configuration
public class CommonWebAppConfig extends WebMvcConfigurerAdapter {
	
	@Bean
	public PropConfig props(){
		return new PropConfig();
	}
	
	@Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**").allowedOrigins("*")
        .allowedMethods("GET", "HEAD", "POST","PUT", "DELETE", "OPTIONS")
        .allowCredentials(false).maxAge(3600);;
    }
	
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/res/**").addResourceLocations("classpath:/res/");
        super.addResourceHandlers(registry);
    }
	
	@Bean
	public FastJsonHttpMessageConverter converter(){
		FastJsonConfig conf = new FastJsonConfig();
		SerializerFeature[] serializerFeatures = { SerializerFeature.DisableCircularReferenceDetect,
				SerializerFeature.WriteDateUseDateFormat, SerializerFeature.WriteNullListAsEmpty,
				SerializerFeature.WriteNullStringAsEmpty
		};
		conf.setSerializerFeatures(serializerFeatures);
		conf.setCharset(Charset.forName("UTF-8"));
		conf.setDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Feature[] features = {
				Feature.AutoCloseSource,
				Feature.AllowUnQuotedFieldNames,
				Feature.AllowComment,
				Feature.CustomMapDeserializer,
				Feature.IgnoreAutoType,
				Feature.IgnoreNotMatch,
				Feature.AllowArbitraryCommas,
		};
		conf.setFeatures(features);
		FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
		converter.setFastJsonConfig(conf);
		return converter;
	}
	
}
