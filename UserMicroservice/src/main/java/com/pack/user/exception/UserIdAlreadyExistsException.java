package com.pack.user.exception;

public class UserIdAlreadyExistsException extends Exception {

	private String msg;
	public UserIdAlreadyExistsException(String msg)
	{
		this.msg=msg;
		
	}
}
