package com.jos.dem.spring.reactive.workshop.service.impl;

import com.jos.dem.spring.reactive.workshop.model.Person;
import com.jos.dem.spring.reactive.workshop.repository.PersonRepository;
import com.jos.dem.spring.reactive.workshop.service.PersonScheduledStreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

import java.util.function.Predicate;
import java.util.function.Supplier;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonScheduledStreamServiceImpl implements PersonScheduledStreamService {

    private final PersonRepository personRepository;

    @Override
    public Flux<Person> createPublisherThread() {
        Scheduler scheduler = Schedulers.newSingle("thread-1");
        Predicate<Person> highRanked = person -> {
            log.info("Thread: {}", Thread.currentThread().getName());
            return person.getRank() >= 4;
        };
        Supplier<Flux<Person>> deferredTask = () -> {
            log.info("Thread: {}", Thread.currentThread().getName());
            return personRepository.getAll();
        };
        Flux<Person> deferred = Flux.defer(deferredTask).filter(highRanked).subscribeOn(scheduler);
        return deferred;
    }
}
