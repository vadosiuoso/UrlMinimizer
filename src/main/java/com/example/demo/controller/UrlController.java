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
        return urlService.createUrl(url, principal);
    }

    @PutMapping("/{id}")
    public Url updateUrl(@PathVariable Long id, @RequestBody Url urlDetails) {
        log.info("Updating URL with id: {}", id);
        urlDetails.setId(id);
        return urlService.createOrUpdate(urlDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUrl(@PathVariable Long id, Principal principal) {
        return urlService.deleteUrl(id, principal);
    }



}
