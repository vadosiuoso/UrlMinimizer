package com.example.demo.controller;

import com.example.demo.dto.AppError;
import com.example.demo.dto.UrlRequest;
import com.example.demo.services.UrlService;
import com.example.demo.services.UrlServiceImpl;
import com.example.demo.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

import lombok.RequiredArgsConstructor;
import com.example.demo.entities.UrlClass;
import com.example.demo.entities.UserClass;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.UrlRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UrlShortenerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
        UserClass userClass = userService.findByUsername(principal.getName());
        log.info(String.format("Fetching all urls by %s", userClass.getUsername()));
        List<UrlClass> urls = urlService.findAllByUser(userClass);
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
        UserClass user = userService.findByUsername(principal.getName());
        log.info(String.format("Trying to save URL %s in the database", originalUrl));

        if(isValidURL(originalUrl)){
          String shortUrl = shortenerService.shortenerUrl(originalUrl);
          UrlClass urlClass = new UrlClass();
          urlClass.setLinkId(0L);
          urlClass.setUser(user);
          urlClass.setOriginalUrl(originalUrl);
          urlClass.setShortUrl(shortUrl);
          log.info("Creating new URL for original URL: {}", urlClass.getOriginalUrl());
          UrlClass created = urlService.createOrUpdate(urlClass);
          return ResponseEntity.ok(created);
        } else {
          return new ResponseEntity<>(
              new AppError(HttpStatus.OK.value(), "Current URl is not responding"),
                  HttpStatus.OK);
        }
    }

    @PutMapping("/{id}")
    public UrlClass updateUrl(@PathVariable Long id, @RequestBody UrlClass urlDetails) {
        log.info("Updating URL with id: {}", id);
        urlDetails.setLinkId(id);
        return urlService.createOrUpdate(urlDetails);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUrl(@PathVariable Long id, Principal principal) {
        log.info("Deleting URL with id: {}", id);
        Optional<UrlClass> optUrl = urlService.findById(id);
        if(optUrl.isPresent()){
              UrlClass url = optUrl.get();
              UserClass user = userService.findByUsername(principal.getName());
              if(url.getUser().getUserId().equals(user.getUserId())) {
                urlService.deleteUrlById(url.getLinkId());
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
