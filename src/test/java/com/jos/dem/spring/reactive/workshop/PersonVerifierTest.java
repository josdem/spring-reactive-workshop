package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.service.PersonStreamService;
import com.jos.dem.spring.reactive.workshop.service.PersonTransformStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class PersonVerifierTest {

  @Autowired private PersonStreamService personStreamService;
  @Autowired private PersonTransformStream personTransformStream;

  @Test
  @DisplayName("get ordered nicknames")
  void shouldGetOrderedNicknames() {
    StepVerifier.create(personStreamService.getOrderedNicknames())
        .expectSubscription()
        .expectNext("edzero")
        .expectNext("jeduan")
        .expectNext("josdem")
        .expectNext("skuarch")
        .expectNext("tgrip")
        .thenCancel()
        .log()
        .verify();
  }

  @Test
  @DisplayName("get ordered nicknames with await time")
  void shouldWaitTwoSecondsBeforeVerifyOrderedNicknames() {
    StepVerifier.create(personStreamService.getOrderedNicknames())
        .expectSubscription()
        .thenAwait(Duration.ofSeconds(2))
        .expectNext("edzero")
        .expectNext("jeduan")
        .expectNext("josdem")
        .expectNext("skuarch")
        .expectNext("tgrip")
        .thenCancel()
        .log()
        .verify();
  }

  @Test
  @DisplayName("concat with nicknames")
  public void testOnComplete() {
    List<String> names = Arrays.asList("john", "mark", "bob", "spencer", "skye");
    StepVerifier.create(personTransformStream.concatWithNames(names))
        .expectNext(
            "BOB", "EDZERO", "JEDUAN", "JOHN", "JOSDEM", "MARK", "SKUARCH", "SKYE", "SPENCER",
            "TGRIP")
        .expectComplete()
        .verify();
  }

  @Test
  @DisplayName("group nicknames")
  void shouldGroupNicknames() {
    StepVerifier.create(personTransformStream.groupNicknames().blockFirst())
        .expectSubscription()
        .expectNext("SKUARCH")
        .expectComplete()
        .verify();
  }
}
