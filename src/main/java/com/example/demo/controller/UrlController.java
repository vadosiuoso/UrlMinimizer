package com.example.demo.controller;

import com.example.demo.dto.AppError;
import com.example.demo.dto.UrlRequest;
import com.example.demo.service.UrlService;
import com.example.demo.service.UserService;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.util.List;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import com.example.demo.entity.Url;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.UrlShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/urls")
@RequiredArgsConstructor
public class UrlController {

    public static final String URL_NOT_FOUND_WITH_ID = "URL not found with id: ";
    private static final Logger log = LoggerFactory.getLogger(UrlController.class);
    private final UrlService urlService;
    private final UrlShortenerService shortenerService;
    private final UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUrls(Principal principal) {
        User user = userService.findByUsername(principal.getName());
        log.info(String.format("Fetching all urls by %s", user.getUsername()));
        List<Url> urls = urlService.findAllByUser(user);
        return ResponseEntity.ok(urls);
    }


    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUrlById(@PathVariable Long id) {
        log.info("Fetching URL by id: {}", id);
        return ResponseEntity.ok(urlService.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException(URL_NOT_FOUND_WITH_ID + id)));
    }

    @PostMapping
    public ResponseEntity<?> createUrl(@RequestBody UrlRequest url, Principal principal) {
        String originalUrl = url.getUrl();
        log.info(String.format("%s is trying to create a new short URL", principal.getName()));
        User user = userService.findByUsername(principal.getName());
        log.info(String.format("Trying to save URL %s in the database", originalUrl));

        if(isValidURL(originalUrl)){
          String shortUrl = shortenerService.shortenerUrl(originalUrl);
          Url urlClass = new Url();
          urlClass.setId(0L);
          urlClass.setUser(user);
          urlClass.setOriginalUrl(originalUrl);
          urlClass.setShortUrl(shortUrl);
          log.info("Creating new URL for original URL: {}", urlClass.getOriginalUrl());
          Url created = urlService.createOrUpdate(urlClass);
          return ResponseEntity.ok(created);
        } else {
          return new ResponseEntity<>(
              new AppError(HttpStatus.OK.value(), "Current URl is not responding"),
                  HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public Url updateUrl(@PathVariable Long id, @RequestBody Url urlDetails) {
        log.info("Updating URL with id: {}", id);
        urlDetails.setId(id);
        return urlService.createOrUpdate(urlDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUrl(@PathVariable Long id, Principal principal) {
        log.info("Deleting URL with id: {}", id);
        Optional<Url> optUrl = urlService.findById(id);
        if(optUrl.isPresent()){
              Url url = optUrl.get();
              User user = userService.findByUsername(principal.getName());
              if(url.getUser().getId().equals(user.getId())) {
                urlService.deleteUrlById(url.getId());
                return ResponseEntity.ok(url);
              }else{
                return new ResponseEntity<>(HttpStatus.FORBIDDEN);
              }
        }
        return new ResponseEntity<>(
            new AppError(
                HttpStatus.BAD_REQUEST.value(),
                String.format("URL with id: %s not found", id)), HttpStatus.BAD_REQUEST);
    }

    private static boolean isValidURL(String url) {
        try {
          URL obj = new URL(url);
          HttpURLConnection con = (HttpURLConnection) obj.openConnection();
          con.setRequestMethod("GET");
          int responseCode = con.getResponseCode();
          return (responseCode >= 200 && responseCode < 400);
        } catch (IOException e) {
          return false;
        }
      }

}
