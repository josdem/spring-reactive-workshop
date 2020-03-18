package com.jos.dem.spring.reactive.workshop.service.impl;

import com.jos.dem.spring.reactive.workshop.service.FluxMonoProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class FluxMonoProcessorImpl implements FluxMonoProcessor {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public Mono<String> processName(String name) {
    Function<String, String> upper = (string) -> string.toUpperCase();
    Predicate<String> longName = (string) -> string.length() > 5;
    Consumer<String> success = (string) -> log.info("Successfully processed: {}", string);
    Consumer<Throwable> error = (e) -> log.info("Error: : " + e.getMessage());
    Consumer<String> onNext = (string) -> log.info("Approved: " + string);

    Mono<String> processedName =
        Mono.just(name)
            .filter(longName)
            .map(upper)
            .doOnSuccess(success)
            .doOnError(error)
            .doOnNext(onNext)
            .onErrorReturn("Invalid name");

    return processedName;
  }

  @Override
  public Flux<String> processingNames(List<String> names) {
    Function<String, String> upper = (str) -> str.toUpperCase();
    Comparator<String> ascSort = (str1, str2) -> str1.compareTo(str2);
    Runnable complete =
        () -> {
          log.info("Completed processing");
        };
    Runnable terminate =
        () -> {
          log.info("Terminated due to some problems");
        };
    Consumer<String> onNext = (string) -> log.info("Validated: " + string);
    Flux<String> userNames =
        Flux.fromIterable(names)
            .map(upper)
            .sort(ascSort)
            .defaultIfEmpty("Empty list")
            .doOnNext(onNext)
            .doOnComplete(complete)
            .doOnTerminate(terminate)
            .doOnError(RuntimeException.class, (e) -> log.error("Throws an exception"));
    return userNames;
  }
}
