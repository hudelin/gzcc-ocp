// Decompiled by Jad v1.5.8e2. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://kpdus.tripod.com/jad.html
// Decompiler options: packimports(3) fieldsfirst ansi space
// Source File Name:   BaseException.java

package com.hdl.gzccocpweb.support;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class BaseException extends RuntimeException {
	private static final long serialVersionUID = 5705170943155980911L;
	private int status = 200;

	public int getStatus() {
		return this.status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public BaseException() {
	}

	public BaseException(int status, String message) {
		super(message);
		this.status = status;
	}

	public BaseException(String message) {
		super(message);
	}

	public BaseException(String message, Throwable cause) {
		super(message, cause);
	}

	public BaseException(Throwable cause) {
		super(cause);
	}

	public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	@ResponseBody
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler({Exception.class})
	public ObjectRestResponse allExceptionHandler(Exception exception) throws Exception {
		ObjectRestResponse response = new ObjectRestResponse();
		try {
			if (exception instanceof BaseException) {
				BaseException baseException = (BaseException)exception;
				response.setCode(baseException.getStatus());
				response.setMsg(baseException.getMessage());
			} else {
				response.setCode(500);
				response.setMsg(exception.getMessage());
			}
		} finally {
			exception.printStackTrace();
		}
		return response;
	}
}
