package com.panly.urm.manager.common.web;

import java.util.HashMap;

import com.alibaba.fastjson.JSON;

/**
 * <p>
 * web json 通用返回
 * </p>
 * @class JsonResult
 * @author a@panly.me
 * @date 2017年5月27日 下午2:46:23
 */
public class JsonResult extends HashMap<String, Object> {

	private final static long serialVersionUID = 1L;

	// 失败状态码
	public final static String ERROR = "0";
	
	// 成功状态码
	public final static String SUCCESS = "1";
	
	// 无权限
	public final static String FORBID = "-1";
	
	// 登录超时
	public final static String LOGIN_TIMEOUT = "-2";

	private final static String KEY_STATUS = "status";
	
	private final static String KEY_ERROR = "error";
	
	private final static String KEY_DATA = "data";

	public JsonResult() {
		super();
		this.put(KEY_STATUS, JsonResult.SUCCESS);
	}

	/**
	 * @param status
	 */
	public JsonResult(String status) {
		super();
		this.put(KEY_STATUS, status);
	}

	public Object getData() {
		return this.get(KEY_DATA);
	}

	public JsonResult setData(Object data) {
		this.put(KEY_DATA, data);
		return this;
	}

	public String getStatus() {
		return (String) this.get(KEY_STATUS);
	}

	public JsonResult setStatus(String status) {
		this.put(KEY_STATUS, status);
		return this;
	}

	public String getError() {
		return (String) this.get(KEY_ERROR);
	}

	public JsonResult setError(String error) {
		this.put(KEY_ERROR, error);
		return this;
	}

	@Override
	public String toString() {
		return JSON.toJSONString(this);
	}

	public static void main(String[] args) throws Exception {
		JsonResult j = new JsonResult();
		j.setData("test");
		j.setError("xxxxx");
		j.setStatus(SUCCESS);
		System.out.println(JSON.toJSONString(j));
	}

}
