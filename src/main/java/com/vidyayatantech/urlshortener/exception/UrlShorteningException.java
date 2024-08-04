package com.vidyayatantech.urlshortener.exception;

import lombok.Getter;

@Getter
public class UrlShorteningException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final String message;

	public UrlShorteningException(String message) {
		this.message = message;
	}

}
