package com.panly.urm.auth.web;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.panly.urm.auth.common.TokenUtil;
import com.panly.urm.auth.log.thread.AuthLogThreadContext;
import com.panly.urm.auth.model.AcctEntity;
import com.panly.urm.auth.model.AppEntity;
import com.panly.urm.auth.model.AuthLog;
import com.panly.urm.auth.model.OperEntity;
import com.panly.urm.auth.service.AuthService;
import com.panly.urm.auth.service.CacheService;
import com.panly.urm.auth.service.TokenService;
import com.panly.urm.common.WebUtil;
import com.panly.urm.tran.auth.AppDTO;
import com.panly.urm.tran.auth.AuthReqDTO;
import com.panly.urm.tran.auth.AuthRespDTO;
import com.panly.urm.tran.auth.TokenDTO;
import com.panly.urm.tran.auth.TreeDTO;
import com.panly.urm.tran.constants.SuccessTypeEnum;
import com.panly.urm.web.JsonResult;

@Controller
@RequestMapping("/auth")
public class AuthController {
	
	private final static Logger logger= LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private TokenService tokenService;
	
	@Autowired
	private CacheService cacheService;
	
	
	@RequestMapping("/right")
	@ResponseBody
	public JsonResult auth(AuthReqDTO authReqDTO){
		long start = System.currentTimeMillis();
		JsonResult ret = new JsonResult();
		AuthLog log = new AuthLog();
		try {
			AuthRespDTO authRespDTO = authService.getAuthRight(authReqDTO);
			ret.setData(authRespDTO);
			log.setSuccess(SuccessTypeEnum.SUCCESS.getCode());
			if(authRespDTO.getRightSqls()!=null&&authRespDTO.getRightSqls().size()>0){
				log.setDataRight(JSON.toJSONString(authRespDTO.getRightSqls()));
			}
		} catch (Exception e) {
			ret.setStatus(JsonResult.ERROR);
			ret.setError(e.getMessage());
			log.setSuccess(SuccessTypeEnum.ERROR.getCode());
			log.setDataRight(e.getMessage());
			logger.error("鉴权失败",e);
		}finally{
			log.setOperCode(authReqDTO.getOperCode());
			log.setOperName(getOperName(authReqDTO.getOperCode()));
			log.setAcctId(authReqDTO.getAcctId());
			log.setAcctName(getAcctName(authReqDTO.getAcctId()));
			log.setAppCode(authReqDTO.getAppCode());
			log.setAppName(getAppName(authReqDTO.getAppCode()));
			log.setAuthCost(System.currentTimeMillis()-start);
			log.setReqIp(WebUtil.getRemoteAddr());
			log.setCreateTime(new Date());
			AuthLogThreadContext.addLog(log);
		}
		return ret;
	}
	
	private String getAcctName(Long acctId){
		AcctEntity acct = cacheService.getAcct(acctId);
		if(acct!=null){
			return acct.getAcctName();
		}
		return "";
	}
	
	private String getAppName(String appCode){
		AppEntity app = cacheService.getApp(appCode);
		if(app!=null){
			return app.getAppName();
		}
		return "";
	}
	
	private String getOperName(String operCode){
		OperEntity acct = cacheService.getOper(operCode);
		if(acct!=null){
			return acct.getOperName();
		}
		return "";
	}
	
	@RequestMapping("/token")
	@ResponseBody
	public JsonResult token(HttpServletRequest request){
		String token = TokenUtil.getToken(request);
		if(StringUtils.isEmpty(token)){
			throw new RuntimeException("token字段不存在");
		}
		TokenDTO tokenDTO = tokenService.getToken(token);
		if(tokenDTO==null){
			return new JsonResult().setStatus(JsonResult.ERROR).setError("token不存在");
		}
		return new JsonResult().setData(tokenDTO);
	}
	
	@RequestMapping("/app")
	@ResponseBody
	public JsonResult getApp(String appCode, HttpServletRequest request){
		AppDTO appVo  = authService.getApp(appCode);
		return new JsonResult().setData(appVo);
	}
	
	
	/**
	 * 获取该应用拥有的func tree结构
	 * @param appCode
	 * @return
	 */
	@RequestMapping("/app/func/tree")
	@ResponseBody
	public JsonResult getAppFuncTree(String appCode){
		List<TreeDTO> trees = authService.getAppFuncTree(appCode);
		return new JsonResult().setData(trees);
	}
	
	/**
	 * 获取用户 在应用下拥有的func tree结构
	 * @param appCode
	 * @param acctId
	 * @return
	 */
	@RequestMapping("/acct/app/func/tree")
	@ResponseBody
	public JsonResult getAcctAppFuncTree(String appCode,Long acctId){
		List<TreeDTO> trees = authService.getAcctFuncTreeForApp(appCode,acctId);
		return new JsonResult().setData(trees);
	}
	
}
