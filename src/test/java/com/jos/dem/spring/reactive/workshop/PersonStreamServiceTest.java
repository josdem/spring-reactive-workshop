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

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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
    List<Person> result = new ArrayList<>();

    personStreamService
        .getPersons()
        .subscribe(
            person -> {
              log.info("person {}", person);
              result.add(person);
            },
            error -> log.error("Error {}", error),
            () -> log.info("All persons stream completed"));

    assertEquals(5, result.size(), "should have five persons");
  }

  @Test
  @DisplayName("should get person by nickname")
  void shouldGePersonByNickname() {
    personStreamService
        .getPerson("josdem")
        .subscribe(
            person -> {
              log.info("person {}", person);
              assertEquals(
                  new Person("josdem", "joseluis.delacruz@gmail.com", 5),
                  person,
                  "should find josdem");
            },
            error -> log.error("Error {}", error),
            () -> log.info("Person by nickname completed"));
  }

  @Test
  @DisplayName("")
  void shouldGetHighRankedPersons() {
    List<Person> result = new ArrayList<>();

    personStreamService
        .getHighRanked()
        .subscribe(
            person -> {
              result.add(person);
            },
            error -> log.info("Error: {}", error),
            () -> log.info("completed"));
    assertEquals(3, result.size(), "should have three persons as high ranked");
  }

  @Test
  @DisplayName("get nicknames")
  void shouldGetNicknames() {
    List<String> result = new ArrayList<>();

    personStreamService.getNicknames().subscribe(result::add);
    assertEquals(5, result.size(), "should have five elements");
    assertTrue(result.contains("josdem"));
  }
}
