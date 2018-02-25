package com.panly.urm.web;


import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;



import com.panly.urm.web.JsonResult;


/**
 * <p>
 * Controller的公共处理
 * </p>
 * @project core
 * @class FrameControllerAdvice
 * @author a@panly.me
 * @date 2017年8月30日下午2:27:44
 */
@ControllerAdvice
public class FrameControllerAdvice {

	private final static Logger logger = LoggerFactory.getLogger(FrameControllerAdvice.class);
	
	/**
	 * 捕获 json 格式转换异常
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = HttpMessageNotReadableException.class)
	public JsonResult errorHandler(HttpMessageNotReadableException ex) {
		logger.error("json转换异常",ex);
		return new JsonResult(JsonResult.ERROR).setError("json数据格式有错");
	}
	
	/**
	 * 未知错误
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = Exception.class)
	public JsonResult errorHandler(Exception ex) {
		logger.error("出现错误",ex);
		if(StringUtils.equals(ex.getClass().getName(), "org.mybatis.spring.MyBatisSystemException") ){
			return  new JsonResult(JsonResult.ERROR).setError(ex.getCause().getCause().getMessage());
		}
		return new JsonResult(JsonResult.ERROR).setError(ex.getMessage());
	}
	
}
