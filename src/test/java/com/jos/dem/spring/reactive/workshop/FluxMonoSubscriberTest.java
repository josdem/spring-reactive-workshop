package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.service.FluxMonoProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FluxMonoSubscriberTest {

  @Autowired private FluxMonoProcessor processor;

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Test
  @DisplayName("should subscribe to a mono")
  void shouldSubscribeToMono() {
    Consumer<String> consumer = value -> log.info("value: {}", value);

    Mono<String> result = processor.processName("JoseMorales");
    result.subscribe(consumer);
  }

  @Test
  @DisplayName("should subscribe to a flux")
  void shouldSubscribeToFlux() {
    List<String> result = new ArrayList<>();
    List<String> names = Arrays.asList("josdem", "skye", "tgrip", "edzero", "jeduan");

    processor.processingNames(names).subscribe(result::add);
    assertEquals(5, result.size(), "should have five values");
    assertEquals("EDZERO", result.get(0), "should be ordered asc");
  }

  @Test
  @DisplayName("should subscribe using subscriber")
  void shouldSubscribeUsingSubscriber() {
    Subscriber<String> subscriber =
        new Subscriber<>() {

          @Override
          public void onComplete() {
            log.info("Mono streams ended successfully.");
          }

          @Override
          public void onError(Throwable e) {
            log.info("Something wrong happened. Exits now.");
          }

          @Override
          public void onNext(String name) {
            log.info("String object: " + name);
          }

          @Override
          public void onSubscribe(Subscription subs) {
            subs.request(Long.MAX_VALUE);
          }
        };

    Mono<String> result = processor.processName("JoseMorales");
    result.subscribe(subscriber);
  }

  @Test
  @DisplayName("should subscribe using lambdas")
  void shouldSubscribeUsingLambdas() {
    Mono<String> result = processor.processName("JoseMorales");
    result.subscribe(
        next -> log.info("next object: {}", next),
        error -> log.error("Error: {}", error),
        () -> log.info("complete"));
  }

}
