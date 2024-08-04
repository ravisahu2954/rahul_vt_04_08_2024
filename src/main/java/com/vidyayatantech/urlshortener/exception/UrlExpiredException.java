package com.vidyayatantech.urlshortener.exception;

import lombok.Getter;

@Getter
public class UrlExpiredException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;
	
	public UrlExpiredException(String message)
	{
		this.message=message;
	}

}
