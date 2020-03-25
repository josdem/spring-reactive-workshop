package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.service.FluxStreamer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Flux;

import java.time.Duration;

@SpringBootApplication
public class SpringReactiveApplication {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  public static void main(String[] args) {
    SpringApplication.run(SpringReactiveApplication.class, args);
  }

  @Bean
  CommandLineRunner run(FluxStreamer streamer) {
    return args -> {
      streamer.streamText("josdem").subscribe(character -> log.info("text: {}", character));

      Flux.interval(Duration.ZERO).subscribe(content -> sendAudio(streamer));
    };
  }

  private void sendAudio(FluxStreamer streamer) {
    streamer.streamBinary().subscribe(dataBuffer -> log.info("dataBuffer: {}", dataBuffer));
  }
}
