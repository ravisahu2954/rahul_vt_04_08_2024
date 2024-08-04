package com.vidyayatantech.urlshortener.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ShortenUrlRequest {

	@NotBlank(message = "Original URL must not be empty")
	@Pattern(regexp = "^https?://.*", message = "Original URL must be a valid URL")
	private String originalUrl;

}
