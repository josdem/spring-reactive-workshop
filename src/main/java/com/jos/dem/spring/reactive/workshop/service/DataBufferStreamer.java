package com.jos.dem.spring.reactive.workshop.service;

import org.springframework.core.io.buffer.DataBuffer;
import reactor.core.publisher.Flux;

public interface DataBufferStreamer {

  Flux<DataBuffer> stream(String nickname);

}
