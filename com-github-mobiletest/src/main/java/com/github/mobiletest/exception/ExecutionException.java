package com.github.mobiletest.exception;

import com.github.mobiletest.logs.MyLogger;

public class ExecutionException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	private MyLogger log = new MyLogger("ExecutionException");
	
	public ExecutionException(String step, String message)
	{
		super(message);
		
		log.error(step, message, this);
	}
}
