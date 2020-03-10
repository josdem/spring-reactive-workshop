package com.jos.dem.spring.reactive.workshop.service.impl;

import com.jos.dem.spring.reactive.workshop.service.DataBufferStreamer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class DataBufferStreamerImpl implements DataBufferStreamer {

  @Override
  public Flux<String> stream(String nickname) {
    return Flux.just(nickname.split(""));
  }

}
