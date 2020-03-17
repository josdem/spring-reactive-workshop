package com.jos.dem.spring.reactive.workshop.service.impl;

import com.jos.dem.spring.reactive.workshop.model.Person;
import com.jos.dem.spring.reactive.workshop.repository.PersonRepository;
import com.jos.dem.spring.reactive.workshop.service.PersonStreamService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class PersonStreamServiceImpl implements PersonStreamService {

  private final PersonRepository personRepository;

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public Mono<Void> showThreads() {
    Runnable task = () -> log.info("Thread: {}", Thread.currentThread().getName());

    Mono<Void> threadExecuted = Mono.fromRunnable(task);
    return threadExecuted;
  }

  @Override
  public Flux<Person> getPersons() {
    return personRepository.getAll();
  }

  @Override
  public Mono<Person> getPerson(String nickname) {
    return personRepository.getByNickname(nickname);
  }

  @Override
  public Flux<Person> getHighRanked() {
    return null;
  }
}
