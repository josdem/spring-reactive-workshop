package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.service.PersonStreamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;

@SpringBootTest
public class PersonVerifierTest {

  @Autowired private PersonStreamService personStreamService;

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
}
