package com.vidyayatantech.urlshortener.exception;

import lombok.Getter;

@Getter
public class UrlExpiryUpdateException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;

	public UrlExpiryUpdateException(String message) {
		this.message = message;
	}

}
