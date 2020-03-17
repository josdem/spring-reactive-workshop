package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.model.Person;
import com.jos.dem.spring.reactive.workshop.service.PersonStreamService;
import org.apache.commons.lang3.builder.ToStringBuilder;
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
            log.info("Subscription: {}", ToStringBuilder.reflectionToString(s));
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

    personStreamService.showThreads().subscribe(subscriber);
  }

  @Test
  @DisplayName("should get all persons")
  void shouldGetAllPersons() {
    Subscriber<Person> subscriber =
        new Subscriber<Person>() {
          @Override
          public void onSubscribe(Subscription s) {
            log.info("Subscription: {}", ToStringBuilder.reflectionToString(s));
          }

          @Override
          public void onNext(Person person) {
            log.info("Person: {}", person);
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

    personStreamService.getPersons().subscribe(
            person -> log.info("person {}", person),
            error -> log.error("Error {}", error),
            () -> log.info("completed")
    );
  }
}
