package com.vidyayatantech.urlshortener.serviceimpl;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.vidyayatantech.urlshortener.constant.Constant;
import com.vidyayatantech.urlshortener.entity.UrlMapping;
import com.vidyayatantech.urlshortener.exception.UrlExpiredException;
import com.vidyayatantech.urlshortener.exception.UrlExpiryUpdateException;
import com.vidyayatantech.urlshortener.exception.UrlNotFoundException;
import com.vidyayatantech.urlshortener.exception.UrlRetrievalException;
import com.vidyayatantech.urlshortener.exception.UrlShorteningException;
import com.vidyayatantech.urlshortener.exception.UrlUpdateException;
import com.vidyayatantech.urlshortener.repository.UrlRepository;
import com.vidyayatantech.urlshortener.request.ShortenUrlRequest;
import com.vidyayatantech.urlshortener.request.UpdateExpiryRequest;
import com.vidyayatantech.urlshortener.request.UpdateUrlRequest;
import com.vidyayatantech.urlshortener.response.ShortenUrlResponse;
import com.vidyayatantech.urlshortener.service.UrlService;
import com.vidyayatantech.urlshortener.util.UrlShortenerUtility;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService {

	private final UrlRepository urlRepository;

	private final UrlShortenerUtility urlShortenerUtility;
	
	//private final RedisTemplate<String, UrlMapping> redisTemplate;
	
	@Value("${baseUrl}")
	private String baseUrl;

	@Override
	@Transactional
	public ShortenUrlResponse shortenUrl(ShortenUrlRequest request) {
		log.info("Request received to shorten URL: {}", request.getOriginalUrl());
		ShortenUrlResponse response = new ShortenUrlResponse();
		try {
			String shortUrl = urlShortenerUtility.generateShortUrl();
			LocalDateTime expiresAt = LocalDateTime.now().plusMonths(10);
			UrlMapping urlMapping = UrlMapping.builder().shortUrl(shortUrl).originalUrl(request.getOriginalUrl())
					.createdAt(LocalDateTime.now()).expiresAt(expiresAt).build();
			urlRepository.save(urlMapping);

			response.setShortUrl(baseUrl+shortUrl);
			response.setId(urlMapping.getId());
			log.info("Successfully shortened URL to: {}", shortUrl);
		} catch (Exception e) {
			log.error("Error shortening URL: {}", request.getOriginalUrl(), e);
			throw new UrlShorteningException("Failed to shorten URL");
		}
		return response;
	}

	@Override
	@Transactional
	public boolean updateUrl(UpdateUrlRequest request) {
		log.info("Request received to update short URL: {}", request.getShortUrl());
		try {
			String extractShortUrl = request.getShortUrl().substring(baseUrl.length());
			log.info("**********Request {}",extractShortUrl);
			Optional<UrlMapping> urlMappingOptional = urlRepository.findByShortUrl(extractShortUrl);
			if (!urlMappingOptional.isPresent())
				throw new UrlNotFoundException(Constant.URL_NOT_FOUND);
			UrlMapping urlMapping = urlMappingOptional.get();
			urlMapping.setOriginalUrl(request.getOriginalUrl());
			urlRepository.save(urlMapping);
			log.info("Successfully updated short URL: {}", request.getShortUrl());
			return true;
		} catch (UrlNotFoundException e) {
			log.warn("URL not found: {}", request.getShortUrl());
			throw e;
		} catch (Exception e) {
			log.error("Error updating short URL: {}", request.getShortUrl(), e);
			throw new UrlUpdateException("Failed to update short URL");
		}
	}

	@Override
	@Cacheable(value="originalUrls",key="#shortUrl")
	public String getOriginalUrl(String shortUrl) {
		log.info("Request received to get original URL for short URL: {}", shortUrl);
		try {
			Optional<UrlMapping> urlMappingOptional = urlRepository.findByShortUrl(shortUrl);
			if (!urlMappingOptional.isPresent())
				throw new UrlNotFoundException(Constant.URL_NOT_FOUND);

			UrlMapping urlMapping = urlMappingOptional.get();

			if (urlMapping.getExpiresAt().isBefore(LocalDateTime.now())) {
				log.warn("Short URL has expired: {}", shortUrl);
				throw new UrlExpiredException("Short URL has expired");
			}

			log.info("Successfully retrieved original URL for short URL: {}", shortUrl);
			return urlMapping.getOriginalUrl();
		} catch (UrlNotFoundException | UrlExpiredException e) {
			log.warn("Error retrieving original URL: {}", shortUrl, e);
			throw e;
		} catch (Exception e) {
			log.error("Error retrieving original URL: {}", shortUrl, e);
			throw new UrlRetrievalException("Failed to retrieve original URL");
		}
	}

	@Override
	@Transactional
	public boolean updateExpiry(UpdateExpiryRequest request) {
		log.info("Request received to update expiry for short URL: {}", request.getShortUrl());
		try {
			Optional<UrlMapping> urlMappingOptional = urlRepository.findByShortUrl(request.getShortUrl());

			if (!urlMappingOptional.isPresent())
				throw new UrlNotFoundException(Constant.URL_NOT_FOUND);

			UrlMapping urlMapping = urlMappingOptional.get();

			LocalDateTime newExpiry = urlMapping.getExpiresAt().plusDays(request.getDaysToAdd());
			urlMapping.setExpiresAt(newExpiry);
			urlRepository.save(urlMapping);

			log.info("Successfully updated expiry for short URL: {}", request.getShortUrl());
			return true;
		} catch (UrlNotFoundException e) {
			log.warn("URL not found: {}", request.getShortUrl());
			throw e;
		} catch (Exception e) {
			log.error("Error updating expiry for short URL: {}", request.getShortUrl(), e);
			throw new UrlExpiryUpdateException("Failed to update expiry for short URL");
		}
	}
}
