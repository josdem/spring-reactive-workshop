package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.service.BackPressureStreamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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

  @Test
  @DisplayName("should get timed nicknames")
  void shouldGetTimedNicknames() {
    List<String> result = new ArrayList<>();

    Flux<String> nicknames = backPressureStreamService.getTimedNicknames();
    nicknames.subscribe(result::add);

    assertEquals(Arrays.asList("josdem", "tgrip", "edzero", "skuarch", "jeduan"), nicknames);
  }
}
