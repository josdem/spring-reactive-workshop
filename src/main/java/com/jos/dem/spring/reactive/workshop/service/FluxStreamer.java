package com.jos.dem.spring.reactive.workshop.service;

import reactor.core.publisher.Flux;

public interface FluxStreamer {

  Flux<String> stream(String nickname);

}
