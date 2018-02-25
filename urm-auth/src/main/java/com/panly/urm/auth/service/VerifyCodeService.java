package com.panly.urm.auth.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.panly.urm.auth.dao.VerifyCodeDao;
import com.panly.urm.auth.model.VerifyCode;


/**
 * 验证码，存储和校验，
 * 可以存在在缓存中，也可以存在数据库中
 * 
 * 默认 有效时间是 10分钟
 * @author a@panly.me
 */
@Service
public class VerifyCodeService {
	
	private final static Logger logger =LoggerFactory.getLogger(VerifyCodeService.class);
	
	@Autowired
	private VerifyCodeDao verifyCodeDao;
	
	public void save(String key,String verifyCode){
		try {
			VerifyCode code = verifyCodeDao.getVerifyCode(key);
			if(code==null){
				VerifyCode insert = new VerifyCode();
				insert.setCreateTime( new Date());
				insert.setVerifyCode(verifyCode.toLowerCase());
				insert.setVerifyKey(key);
				verifyCodeDao.insert(insert);
			}else{
				VerifyCode update = new VerifyCode();
				update.setCreateTime( new Date());
				update.setVerifyCode(verifyCode.toLowerCase());
				update.setVerifyKey(key);
				verifyCodeDao.update(update);
			}
		} catch (Exception e) {
			logger.error("验证码生成失败");
		}
	}
	
	public void check(String key,String verifyCode){
		if (StringUtils.isEmpty(verifyCode)) {
			throw new RuntimeException("验证码不能为空");
		}
		//通过key 获取验证码
		VerifyCode code = verifyCodeDao.getVerifyCode(key);
		if(code==null){
			throw new RuntimeException("验证码错误");
		}
		long createTime = code.getCreateTime().getTime();
		if((createTime+1000*600)<=System.currentTimeMillis()){
			throw new RuntimeException("验证码失效");
		}
		if (!StringUtils.equalsIgnoreCase(code.getVerifyCode(), verifyCode)) {
			throw new RuntimeException("验证码错误");
		}
	}
}
