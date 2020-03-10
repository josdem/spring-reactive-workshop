package com.jos.dem.spring.reactive.workshop.service.impl;

import com.jos.dem.spring.reactive.workshop.service.DataBufferStreamer;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

@Service
public class DataBufferStreamerImpl implements DataBufferStreamer {

  private final DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();

  @Override
  public Flux<DataBuffer> stream(String nickname) {
    DataBuffer buffer = dataBufferFactory.wrap(nickname.getBytes());
    return Flux.just(buffer);
  }

}
