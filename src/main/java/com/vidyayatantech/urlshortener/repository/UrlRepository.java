package com.vidyayatantech.urlshortener.repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vidyayatantech.urlshortener.entity.UrlMapping;

public interface UrlRepository extends JpaRepository<UrlMapping, Long>{

	Optional<UrlMapping> findByShortUrl(String shortUrl);


	List<UrlMapping> findByExpiresAtBefore(LocalDateTime now);

}
