package com.jos.dem.spring.reactive.workshop.service;

import com.jos.dem.spring.reactive.workshop.model.Person;
import reactor.core.publisher.Flux;

public interface PersonScheduledStreamService {
    Flux<Person> createPublisherThread();
    Flux<Person> createPublisherSubscriberWorkers();
}
