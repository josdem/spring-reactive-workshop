package com.jos.dem.spring.reactive.workshop.service.impl;

import com.jos.dem.spring.reactive.workshop.service.FluxStreamer;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class FluxStreamerImpl implements FluxStreamer {

  @Override
  public Flux<String> stream(String nickname) {
    return Flux.just(nickname.split(""));
  }

}
