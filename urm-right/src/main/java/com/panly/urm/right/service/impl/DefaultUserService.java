package com.panly.urm.right.service.impl;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.panly.urm.right.service.UserService;

/**
 * 默认的获取acctId实现
 * @author a@panly.me
 */
public class DefaultUserService implements UserService{

	@Override
	public Long getAcctId() {
		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
				.getRequest();
		if(request==null){
			return null;
		}
		Long acctId = (Long) request.getAttribute(PARAM_ACCT_ID);
		return acctId;
	}

}
