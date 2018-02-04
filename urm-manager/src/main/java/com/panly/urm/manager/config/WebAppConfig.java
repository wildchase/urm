package com.panly.urm.manager.config;

import java.nio.charset.Charset;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.panly.urm.manager.common.web.FastJsonHttpMessageConverter;

@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {
	
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
