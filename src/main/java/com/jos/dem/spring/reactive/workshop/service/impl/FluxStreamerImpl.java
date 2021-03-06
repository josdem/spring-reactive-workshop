package com.jos.dem.spring.reactive.workshop.service.impl;

import com.jos.dem.spring.reactive.workshop.config.AudioProperties;
import com.jos.dem.spring.reactive.workshop.service.FluxStreamer;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.StreamUtils;
import reactor.core.publisher.Flux;

@Service
@RequiredArgsConstructor
public class FluxStreamerImpl implements FluxStreamer {

  private final DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
  private final ResourceLoader resourceLoader;
  private final AudioProperties audioProperties;

  @Override
  public Flux<String> streamText(String nickname) {
    return Flux.just(nickname.split(""));
  }

  @Override
  public Flux<DataBuffer> streamBinary() {
    return
        DataBufferUtils
            .read(resourceLoader.getResource(audioProperties.getSilence()), dataBufferFactory, audioProperties.getBufferSize());
  }

}
