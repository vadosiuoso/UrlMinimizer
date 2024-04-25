package com.example.demo;

import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.security.Principal;
import java.util.Optional;

import com.example.demo.controller.UrlController;
import com.example.demo.entities.UrlClass;
import com.example.demo.entities.UserClass;
import com.example.demo.repository.UrlRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.services.UrlShortenerService;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@ExtendWith(MockitoExtension.class)
public class UrlControllerTest {

  @Mock
  private UrlRepository urlRepository;

  @Mock
  private UrlShortenerService urlShortenerService;

  @Mock
  private UserRepository userRepository;

  @InjectMocks
  private UrlController urlController;

  private MockMvc mockMvc;

  @MockBean
  private UserService userService;

  @MockBean
  private UrlShortenerService shortenerService;

//@Test
//public void testCreateUrl_ValidUrl() throws Exception {
//  // Arrange
//  String originalUrl = "https://example.com";
//  String shortUrl = "https://short.url/abc123";
//  String username = "testUser";
//  Principal principal = () -> username;
//  UserClass user = new UserClass();
//  user.setUsername(username);
//
//  when(userService.findByUsername(username)).thenReturn(Optional.of(user));
//  when(shortenerService.shortenerUrl(originalUrl)).thenReturn(shortUrl);
//
//  // Act
//  ResultActions result = mockMvc.perform(post("/")
//      .contentType(MediaType.APPLICATION_JSON)
//      .content("{\"url\":\"" + originalUrl + "\"}")
//      .principal(principal));
//
//  // Assert
//  result.andExpect(status().isOk())
//      .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//      .andExpect(jsonPath("$.originalUrl").value(originalUrl))
//      .andExpect(jsonPath("$.shortUrl").value(shortUrl));
//}

//  @Test
//  public void testUpdateUrl() {
//    Long urlId = 1L;
//    UrlClass existingUrl = new UrlClass();
//    existingUrl.setLinkId(urlId);
//    existingUrl.setOriginalUrl("https://www.example.com");
//    existingUrl.setShortUrl("https://short.ly/abc123");
//
//    UrlClass updatedUrl = new UrlClass();
//    updatedUrl.setOriginalUrl("https://www.updated-example.com");
//    updatedUrl.setShortUrl("https://short.ly/def456");
//
//    when(urlRepository.findById(urlId)).thenReturn(Optional.of(existingUrl));
//    when(urlShortenerService.shortenerUrl("https://www.updated-example.com")).thenReturn("https://short.ly/def456");
//    when(urlRepository.save(existingUrl)).thenReturn(updatedUrl);
//
////    UrlClass result = urlController.updateUrl(urlId, updatedUrl);
//
//    Assertions.assertEquals(updatedUrl, result);
  }
//
//  @Test
//  public void testDeleteUrl() {
//    Long urlId = 1L;
//    UrlClass existingUrl = new UrlClass();
//    existingUrl.setLinkId(urlId);
//    existingUrl.setOriginalUrl("https://www.example.com");
//    existingUrl.setShortUrl("https://short.ly/abc123");
//
//    when(urlRepository.findById(urlId)).thenReturn(Optional.of(existingUrl));
//
//    urlController.deleteUrl(urlId);
//
//    verify(urlRepository, times(1)).deleteById(urlId);
//  }
//}
