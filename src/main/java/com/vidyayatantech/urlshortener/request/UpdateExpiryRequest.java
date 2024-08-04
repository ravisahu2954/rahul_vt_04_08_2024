package com.vidyayatantech.urlshortener.request;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UpdateExpiryRequest {

	@NotBlank(message = "Short URL must not be empty")
    @Pattern(regexp = "^http://localhost:8080/.*", message = "Short URL must be valid")
    private String shortUrl;

    @Positive(message = "Days to add must be a positive number")
    private int daysToAdd;
	
}
