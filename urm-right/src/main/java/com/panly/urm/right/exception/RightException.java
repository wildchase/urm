package com.panly.urm.right.exception;

public class RightException extends RuntimeException {

	private static final long serialVersionUID = -542138954278348149L;

	private String errorCode;
	
	public RightException() {
		super();
	}
	
	public RightException(String message) {
		super(message);
	}
	
	public RightException(String message,Throwable cause) {
		super(message,cause);
	}
	
	public RightException(String message,String errorCode,Throwable cause) {
		super(message,cause);
		this.errorCode = errorCode;
	}
	
	@Override
	public String getMessage() {
		if(null==errorCode){
			return super.getMessage();
		}else{
			//通过errorCode 获取消息国际化错误消息
			return errorCode;
		}
	}
}
