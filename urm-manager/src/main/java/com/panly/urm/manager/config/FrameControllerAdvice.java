package com.panly.urm.manager.config;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.ValidationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.ui.Model;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;

import com.panly.urm.manager.common.web.JsonResult;


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
	 * 应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器
	 * 
	 * @param binder
	 */
	@InitBinder
	public void initBinder(WebDataBinder binder) {
	}

	/**
	 * 把值绑定到Model中，使全局@RequestMapping可以获取到该值
	 * 
	 * @param model
	 */
	@ModelAttribute
	public void addAttributes(Model model,HttpServletRequest request) {
	}

	/**
	 * 捕获校验异常
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = ConstraintViolationException.class)
	public JsonResult errorHandler(ConstraintViolationException ex) {
		logger.error("校验异常",ex);
		Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
		StringBuilder strBuilder = new StringBuilder();
		for (ConstraintViolation<?> violation : violations) {
			strBuilder.append(violation.getMessage() + "\n");
		}
		return new JsonResult(JsonResult.ERROR).setError("校验异常"+strBuilder.toString());
	}

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
	 * springboot 自带校验错误
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public JsonResult errorHandler(MethodArgumentNotValidException ex) {
		logger.error("校验错误",ex);
		List<ObjectError> errors = ex.getBindingResult().getAllErrors();
		StringBuilder sb = new StringBuilder();
		for (ObjectError objectError : errors) {
			sb.append(objectError.getDefaultMessage());
		}
		return new JsonResult(JsonResult.ERROR).setError("校验错误"+sb.toString());
	}
	
	/**
	 * 校验错误
	 * @param ex
	 * @return
	 */
	@ResponseBody
	@ExceptionHandler(value = ValidationException.class)
	public JsonResult errorHandler(ValidationException ex) {
		logger.error("校验错误",ex);
		return new JsonResult(JsonResult.ERROR).setError("校验错误:"+ex.getMessage());
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
		return new JsonResult(JsonResult.ERROR).setError(ex.getMessage());
	}
	
}
