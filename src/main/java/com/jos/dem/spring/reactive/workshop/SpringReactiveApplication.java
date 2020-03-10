package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.service.FluxStreamer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringReactiveApplication {

  private String silenceFile =
    "file:/Users/moralej3/Temporal/brandon_silence.raw";

  private Logger log = LoggerFactory.getLogger(this.getClass());

  public static void main(String[] args) {
    SpringApplication.run(SpringReactiveApplication.class, args);
  }

  @Bean
  CommandLineRunner run(FluxStreamer streamer){
    return args -> {
      streamer.streamText("josdem")
          .subscribe(character -> log.info("text: {}", character));

      streamer.streamBinary(silenceFile)
          .subscribe(bytes -> log.info("dataBuffer: {}", bytes));
    };
  }


}
