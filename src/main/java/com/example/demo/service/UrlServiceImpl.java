package com.example.demo.service;


import com.example.demo.entity.Url;
import com.example.demo.entity.User;
import com.example.demo.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UrlServiceImpl implements UrlService{

  private final UrlRepository urlRepository;

  @Override
  public List<Url> findAllByUser(User user) {
    return urlRepository.findAllByUser(user);
  }

  @Override
  @Transactional
  public void deleteUrlById(Long id) {
    urlRepository.deleteById(id);
  }

  @Override
  @Transactional
  public Url createOrUpdate(Url url) {
     return urlRepository.save(url);
  }

  @Override
  public Optional<Url> findById(Long id) {
    return urlRepository.findById(id);
  }

  @Override
  public Optional<Url> findByShortUrl(String shortUrl) {
    return urlRepository.findByShortUrl(shortUrl);
  }
}
