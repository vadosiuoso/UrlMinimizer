package com.example.demo.services;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class UrlShortenerService {

  private static final Logger log = LoggerFactory.getLogger(UrlShortenerService.class);

  public String shortenerUrl(String originalUrl) {
    try {
      String sha256 = DigestUtils.sha256Hex(originalUrl);
      String shortUrl = sha256.substring(0, 8);
      log.info("Shortened URL from {} to {}", originalUrl, shortUrl);
      return shortUrl;
    } catch (Exception e) {
      log.error("Error shortening URL: {}", originalUrl, e);
      throw new RuntimeException("Error shortening URL", e);
    }
  }
}
