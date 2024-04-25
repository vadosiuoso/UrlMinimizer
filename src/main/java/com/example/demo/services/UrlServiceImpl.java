package com.example.demo.services;


import com.example.demo.entities.UrlClass;
import com.example.demo.entities.UserClass;
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
  public List<UrlClass> findAllByUser(UserClass userClass) {
    return urlRepository.findAllByUser(userClass);
  }

  @Override
  @Transactional
  public Optional<UrlClass> deleteUrlById(Long id) {
    return urlRepository.deleteByLinkId(id);
  }

  @Override
  @Transactional
  public UrlClass createOrUpdate(UrlClass url) {
     return urlRepository.save(url);
  }

  @Override
  public Optional<UrlClass> findById(Long id) {
    return urlRepository.findById(id);
  }

  @Override
  public Optional<UrlClass> findByShortUrl(String shortUrl) {
    return urlRepository.findByShortUrl(shortUrl);
  }
}
