package com.vidyayatantech.urlshortener.exception;

import lombok.Getter;

@Getter
public class UrlUpdateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;

	public UrlUpdateException(String message)
	{
		this.message=message;
	}

}
