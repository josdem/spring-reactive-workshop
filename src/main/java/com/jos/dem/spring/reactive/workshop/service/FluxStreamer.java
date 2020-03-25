package com.jos.dem.spring.reactive.workshop.service;

import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

public interface FluxStreamer {

  Flux<String> streamText(String nickname);
  Flux<DataBuffer> streamBinary();

}
