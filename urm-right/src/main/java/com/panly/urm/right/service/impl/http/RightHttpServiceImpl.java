package com.panly.urm.right.service.impl.http;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.panly.urm.common.BeanCopyUtil;
import com.panly.urm.right.domain.Application;
import com.panly.urm.right.domain.Right;
import com.panly.urm.right.service.RightService;
import com.panly.urm.tran.auth.AppDTO;
import com.panly.urm.tran.auth.AuthRespDTO;
import com.panly.urm.tran.auth.TreeDTO;

public class RightHttpServiceImpl implements RightService {

	public final static Charset UTF8 = Charset.forName("UTF-8");

	// http://ip:port
	private String baseUrl;

	/**
	 * 获取应用的基础信息，判断是否存在
	 */
	@Override
	public Application checkApplication(String appCode) {
		Map<String, String> params = new HashMap<>();
		params.put("appCode", appCode);
		AppDTO vo = RightHttpUtil.httpPostParamsObject(getAppUrl(), params, AppDTO.class);
		Application app = BeanCopyUtil.copy(vo, Application.class);
		return app;
	}

	/**
	 * @param userId
	 * @param operCode
	 * @return
	 */
	@Override
	public Right checkRight(Long acctId, String operCode,String appCode) {
		Map<String, String> params = new HashMap<>();
		params.put("acctId", String.valueOf(acctId));
		params.put("operCode", operCode);
		params.put("appCode", appCode);
		AuthRespDTO resp = RightHttpUtil.httpPostParamsObject(getRightUrl(), params, AuthRespDTO.class);
		Right r = transToRight(resp, acctId);
		return r;
	}

	private Right transToRight(AuthRespDTO resp, Long acctId) {
		if (resp == null || resp.getOperId() == null) {
			return null;
		}else{
			Right r = new Right();
			r.setOperId(resp.getOperId());
			r.setAcctId(acctId);
			r.setOperCode(resp.getOperCode());
			List<String> rightSqls = resp.getRightSqls();
			r.setRightSql(rightSqls);
			return r;
		}
	}
	
	@Override
	public List<TreeDTO> getAppFuncTree(String appCode) {
		Map<String, String> params = new HashMap<>();
		params.put("appCode", appCode);
		List<TreeDTO> results = RightHttpUtil.httpPostParamsList(getAppFuncTreeUrl(), params, TreeDTO.class);
		return results;
	}

	@Override
	public List<TreeDTO> getAcctFuncTree(Long acctId, String appCode) {
		Map<String, String> params = new HashMap<>();
		params.put("appCode", appCode);
		params.put("acctId", String.valueOf(acctId));
		List<TreeDTO> results = RightHttpUtil.httpPostParamsList(getAcctAppFuncTreeUrl(), params, TreeDTO.class);
		return results;
	}
	
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	// 鉴权接口
	private String getRightUrl() {
		return baseUrl + "/auth/right";
	}

	// 鉴权应用code接口
	private String getAppUrl() {
		return baseUrl + "/auth/app";
	}

	//获取应用functree接口
	private String getAppFuncTreeUrl() {
		return baseUrl + "/auth/app/func/tree";
	}
	
	//获取应用下 acct 的functTree接口
	private String getAcctAppFuncTreeUrl() {
		return baseUrl + "/auth/acct/app/func/tree";
	}
	

}
