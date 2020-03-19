package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.service.BackPressureStreamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class BackPressureStreamServiceTest {

  @Autowired private BackPressureStreamService backPressureStreamService;

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Test
  @DisplayName("should get timed nicknames")
  void shouldGetTimedNicknames() {
    List<String> result = new ArrayList<>();

    Flux<String> nicknames = backPressureStreamService.getTimedNicknames();
    nicknames.subscribe(
        nickname -> result.add(nickname),
        error -> log.info("Timeout reached: {}", error),
        () -> log.info("Completed"));

    assertEquals(5, result.size());
  }
}
