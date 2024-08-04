package com.vidyayatantech.urlshortener.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.vidyayatantech.urlshortener.entity.UrlMapping;
import com.vidyayatantech.urlshortener.repository.UrlRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class UrlCleanupService {

    private final UrlRepository urlRepository;

    @Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
    public void cleanUpExpiredUrls() {
        LocalDateTime now = LocalDateTime.now();
        List<UrlMapping> expiredUrls = urlRepository.findByExpiresAtBefore(now);
        urlRepository.deleteAll(expiredUrls);
        log.info("Cleaned up {} expired URLs", expiredUrls.size());
    }
}