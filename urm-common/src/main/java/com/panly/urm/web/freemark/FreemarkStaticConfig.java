package com.panly.urm.web.freemark;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import com.panly.urm.web.FtlUtil;


@Configuration
@ConditionalOnClass({ freemarker.template.Configuration.class,FreeMarkerConfigurationFactory.class })
@EnableConfigurationProperties(FreemarkStaticProp.class)
public class FreemarkStaticConfig   extends WebMvcConfigurerAdapter  {

	private final FreemarkStaticProp properties;
	
	private static Map<String, Object> statics = new HashMap<String, Object>();

	public FreemarkStaticConfig( FreemarkStaticProp properties) {
		super();
		this.properties = properties;
	}
	
	@PostConstruct
	public void init(){
		Map<String, String> clazzes = properties.getClazzes();
		if(clazzes!=null){
			Set<String> keys = clazzes.keySet();
			for (String key : keys) {
				statics.put(key, FtlUtil.generateStaticModel(clazzes.get(key)));
			}
		}
	}
	
	@Override
    public void addInterceptors(InterceptorRegistry registry) {
		FreemarkInterceptor freemarkInterceptor = new  FreemarkInterceptor(statics);
		registry.addInterceptor(freemarkInterceptor).addPathPatterns("/**");
        super.addInterceptors(registry);
    }
	

}
