package com.vidyayatantech.urlshortener.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "url_mappings")
public class UrlMapping implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "short_url", unique = true, nullable = false, length = 30)
    @NotBlank(message = "Short URL must not be empty")
    private String shortUrl;

    @Column(name = "original_url", nullable = false,length = 2048)
    @NotBlank(message = "Original URL must not be empty")
    @Pattern(regexp = "^https?://.*", message = "Original URL must be a valid URL")
    private String originalUrl;

    @Column(name = "created_at", nullable = false)
    @NotNull(message = "Creation time must not be null")
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    @NotNull(message = "Expiration time must not be null")
    @FutureOrPresent(message = "Expiration time must be in the future or present")
    private LocalDateTime expiresAt;


}
