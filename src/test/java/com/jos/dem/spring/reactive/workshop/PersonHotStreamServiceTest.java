package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.service.PersonHotStreamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.MonoProcessor;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonHotStreamServiceTest {

  @Autowired private PersonHotStreamService service;

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Test
  @DisplayName("should get flowy nicknames")
  void shouldGetFlowyNicknames() {
    service.freeFlow().connect();
  }

  @Test
  @DisplayName("should get mono processor")
  void shouldGetMonoProcessor() {
    String nickname = "josdem";
    MonoProcessor<String> result = service.monoProcessorGetPerson("josdem");
    result.onNext(nickname);
    result.subscribe(
        content -> assertEquals(nickname, content, "should be josdem"),
        error -> log.info("error: {}", error),
        () -> log.info(""));
  }
  
}
