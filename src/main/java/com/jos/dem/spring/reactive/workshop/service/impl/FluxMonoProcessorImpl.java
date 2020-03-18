package com.jos.dem.spring.reactive.workshop.service.impl;

import com.jos.dem.spring.reactive.workshop.service.FluxMonoProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

@Service
public class FluxMonoProcessorImpl implements FluxMonoProcessor {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Override
    public Mono<String> processName(String name) {
        Function<String,String> upper = (string) -> string.toUpperCase();
        Predicate<String> longName = (string) -> string.length() > 5;
        Consumer<String> success = (string) -> log.info("Successfully processed: {}",  string);
        Consumer<Throwable> error = (e) -> log.info("Error: : " + e.getMessage());
        Consumer<String> onNext = (string) -> log.info("Approved: " + string);

        Mono<String> processedName = Mono.just(name)
                .filter(longName)
                .map(upper)
                .doOnSuccess(success)
                .doOnError(error)
                .doOnNext(onNext)
                .onErrorReturn("Invalid");

        return processedName;
    }

}
