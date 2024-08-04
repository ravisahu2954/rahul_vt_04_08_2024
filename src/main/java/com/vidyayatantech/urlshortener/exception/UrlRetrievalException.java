package com.vidyayatantech.urlshortener.exception;

import lombok.Getter;

@Getter
public class UrlRetrievalException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;

	public UrlRetrievalException(String message)
	{
		this.message=message;
	}

}
