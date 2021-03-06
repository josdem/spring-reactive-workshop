package com.jos.dem.spring.reactive.workshop.service;

import com.jos.dem.spring.reactive.workshop.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface BackPressureStreamService {
    Mono<Person> selectOnePerson();
    Flux<Person> selectSomePersons();
    Flux<List<Person>> selectBufferedPersons();
    Flux<String> getTimedNicknames();
}
