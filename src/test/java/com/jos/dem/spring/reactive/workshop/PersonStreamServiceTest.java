package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.service.PersonStreamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PersonStreamServiceTest {

  @Autowired private PersonStreamService personStreamService;

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Test
  @DisplayName("should get threads")
  void shouldDisplayThreads() {

    Subscriber<Void> subscriber =
        new Subscriber<Void>() {
          @Override
          public void onSubscribe(Subscription s) {
            log.info("Subscription: []", s);
          }

          @Override
          public void onNext(Void aVoid) {
              log.info("On next");
          }

          @Override
          public void onError(Throwable t) {
              log.info("Transmission error");
          }

          @Override
          public void onComplete() {
              log.info("End of stream");
          }
        };

    personStreamService.showThreads()
            .subscribe(subscriber);
  }
}
