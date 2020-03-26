package com.jos.dem.spring.reactive.workshop.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;

import java.util.List;

public interface PersonTransformStream {
    Flux<String> concatWithNames(List<String> others);
    Flux<GroupedFlux<String, String>> groupNicknames();
}
