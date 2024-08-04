package com.vidyayatantech.urlshortener.util;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public class UrlShortenerUtility {

	public String generateShortUrl() {

		final String CHARSET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		final int BASE = 62;
		final int SHORT_URL_LENGTH = 8; 

		SecureRandom random = new SecureRandom();
		StringBuilder shortUrl = new StringBuilder();

		for (int i = 0; i < SHORT_URL_LENGTH; i++) {
			shortUrl.append(CHARSET.charAt(random.nextInt(BASE)));
		}

		return shortUrl.toString();
	}

}
