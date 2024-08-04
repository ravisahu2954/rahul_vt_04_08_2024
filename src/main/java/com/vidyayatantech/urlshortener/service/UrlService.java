package com.vidyayatantech.urlshortener.service;

import com.vidyayatantech.urlshortener.request.ShortenUrlRequest;
import com.vidyayatantech.urlshortener.request.UpdateExpiryRequest;
import com.vidyayatantech.urlshortener.request.UpdateUrlRequest;
import com.vidyayatantech.urlshortener.response.ShortenUrlResponse;

public interface UrlService {

	ShortenUrlResponse shortenUrl(ShortenUrlRequest request);

	boolean updateUrl(UpdateUrlRequest request);

	String getOriginalUrl(String shortUrl);

	boolean updateExpiry(UpdateExpiryRequest request);

}
