package com.jos.dem.spring.reactive.workshop.service;

import reactor.core.publisher.ConnectableFlux;

public interface PersonHotStreamService {
    ConnectableFlux<String> freeFlow();
}
