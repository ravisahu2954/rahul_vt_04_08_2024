package com.vidyayatantech.urlshortener.controller;

import java.net.URI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.vidyayatantech.urlshortener.constant.Constant;
import com.vidyayatantech.urlshortener.request.ShortenUrlRequest;
import com.vidyayatantech.urlshortener.request.UpdateExpiryRequest;
import com.vidyayatantech.urlshortener.request.UpdateUrlRequest;
import com.vidyayatantech.urlshortener.response.ShortenUrlResponse;
import com.vidyayatantech.urlshortener.service.UrlService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RequestMapping(Constant.API_V1)
@RestController
@CrossOrigin(origins = "*")
public class UrlController {

	private final UrlService urlService;

	@PostMapping("/shorten")
	public ResponseEntity<ShortenUrlResponse> shortenUrl(@Valid @RequestBody ShortenUrlRequest request) {
		ShortenUrlResponse response = urlService.shortenUrl(request);
		return ResponseEntity.ok(response);
	}

	@PostMapping("/update")
	public ResponseEntity<Boolean> updateUrl(@Valid @RequestBody UpdateUrlRequest request) {
		boolean success = urlService.updateUrl(request);
		return ResponseEntity.ok(success);
	}

	@GetMapping("/{shortUrl}")
	public ResponseEntity<String> getOriginalUrl(@PathVariable String shortUrl) {
		String originalUrl = urlService.getOriginalUrl(shortUrl);
		return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
	}

	@PostMapping("/updateExpiry")
	public ResponseEntity<Boolean> updateExpiry(@Valid @RequestBody UpdateExpiryRequest request) {
		boolean success = urlService.updateExpiry(request);
		return ResponseEntity.ok(success);
	}

}
