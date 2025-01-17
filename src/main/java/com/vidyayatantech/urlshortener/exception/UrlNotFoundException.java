package com.vidyayatantech.urlshortener.exception;

import lombok.Getter;

@Getter
public class UrlNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;

	public UrlNotFoundException(String message) {
		this.message = message;
	}

}
