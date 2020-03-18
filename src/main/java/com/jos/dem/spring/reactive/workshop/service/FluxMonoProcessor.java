package com.jos.dem.spring.reactive.workshop.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface FluxMonoProcessor {
    Mono<String> processName(String name);
    Flux<String> processingNames(List<String> names);
}
