package com.vidyayatantech.urlshortener.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateUrlRequest {

	@NotBlank(message = "Short URL must not be empty")
	@Pattern(regexp = "^http://localhost:8080/.*", message = "Short URL must be valid")
	private String shortUrl;

	@NotBlank(message = "Original URL must not be empty")
	@Pattern(regexp = "^https?://.*", message = "Original URL must be a valid URL")
	private String originalUrl;

}
