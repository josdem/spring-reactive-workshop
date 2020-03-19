package com.jos.dem.spring.reactive.workshop.service;

import com.jos.dem.spring.reactive.workshop.model.Person;
import reactor.core.publisher.Mono;

public interface BackPressureStreamService {
    Mono<Person> selectOnePerson();
}
