package com.jos.dem.spring.reactive.workshop.service;

import reactor.core.publisher.Flux;

public interface DataBufferStreamer {

  Flux<String> stream(String nickname);

}
