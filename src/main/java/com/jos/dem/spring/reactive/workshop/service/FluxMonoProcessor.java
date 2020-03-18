package com.jos.dem.spring.reactive.workshop.service;

import reactor.core.publisher.Mono;

public interface FluxMonoProcessor {
    Mono<String> processName(String name);
}
