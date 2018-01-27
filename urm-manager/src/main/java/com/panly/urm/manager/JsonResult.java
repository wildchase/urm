package com.panly.urm.manager;

import java.io.Serializable;

/**
 * <p>
 * web json 通用返回
 * </p>
 *
 * @project core
 * @class JsonResult
 * @author a@panly.me
 * @date 2017年5月27日 下午2:46:23
 */
public class JsonResult implements Serializable {
	private static final long serialVersionUID = 1L;

	// 失败状态码
	public static final String ERROR = "0";
	// 成功状态码
	public static final String SUCCESS = "1";
	// 成功状态文字描述
	public static final String MSG = "OK";
	// 无权限
	public static final String FORBID = "-1";
	// 登录超时
	public static final String LOGIN_TIMEOUT = "-2";

	// 状态码
	private String status = JsonResult.SUCCESS;
	// 返回结果描述
	private String msg;
	
	private String error;

	// 返回结果
	private Object results;

	public JsonResult() {
		super();
		this.status = JsonResult.SUCCESS;
	}

	/**
	 * @param status
	 */
	public JsonResult(String status) {
		super();
		this.status = status;
	}

	/**
	 * @param status
	 */
	public JsonResult(String status, String msg) {
		super();
		this.status = status;
		this.msg = msg;
	}

	public String getStatus() {
		return status;
	}

	public JsonResult setStatus(String status) {
		this.status = status;
		return this;
	}

	public String getMsg() {
		return msg;
	}

	public JsonResult setMsg(String msg) {
		this.msg = msg;
		return this;
	}

	public Object getResults() {
		return results;
	}

	public JsonResult setResults(Object results) {
		this.results = results;
		return this;
	}
	
	public boolean isSuccess() {
		return SUCCESS.equals(getStatus());
	}
	
	
	public String getError() {
		return error;
	}

	public JsonResult setError(String error) {
		this.error = error;
		return this;
	}

	public void defaultSuccess(Object obj){
		this.setMsg(MSG);
		this.setResults(obj);
		this.setStatus(SUCCESS);
	}

	public void defaultError(String errorInfo){
		this.setMsg(errorInfo);
		this.setResults(null);
		this.setStatus(ERROR);
	}
	@Override
	public String toString() {
		return "JsonResult [status=" + status + ", msg=" + msg + ", results=" + results + "]";
	}
	
}
