package com.jos.dem.spring.reactive.workshop.service;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.MonoProcessor;

public interface PersonHotStreamService {
    ConnectableFlux<String> freeFlow();
    MonoProcessor<String> monoProcessorGetPerson(String nickname);
}
