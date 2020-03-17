package com.jos.dem.spring.reactive.workshop.service;


import com.jos.dem.spring.reactive.workshop.model.Person;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface PersonStreamService {
    Mono<Void> showThreads();
    Flux<Person> getPersons();
    Flux<Person> getPerson(String nickname);
    Flux<Person> getHighRanked();
}
