package com.panly.urm.i18n;

import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

public class I18nResource {
	
	private static ReloadableResourceBundleMessageSource i18nMessage;
	
	static {
		i18nMessage = new ReloadableResourceBundleMessageSource();
		i18nMessage.setBasenames(new String[] { "classpath:i18n/i18n"});
	}

	// 消息类I18N资源码 国际化
	public static String getMessage(String messageKey) {
		return getMessage(messageKey, null);
	}
	
	// 消息类I18N资源码 国际化
	public static String getMessage(String messageKey, Object[] messageArgs) {
		String message = null;
		try {
			message = i18nMessage.getMessage(messageKey, messageArgs, LocaleContextHolder.getLocale());
		} catch (Exception e) {
			e.printStackTrace();
			return "";
		}
		return message;
	}
	
}
