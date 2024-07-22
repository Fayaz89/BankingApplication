package com.pack.card.exception;

public class CardNotFoundException extends Exception {

	String msg;
	public CardNotFoundException(String msg)
	{
		this.msg=msg;
	}
}
