package com.jos.dem.spring.reactive.workshop.service.impl;

import com.jos.dem.spring.reactive.workshop.model.Person;
import com.jos.dem.spring.reactive.workshop.repository.PersonRepository;
import com.jos.dem.spring.reactive.workshop.service.PersonStreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonStreamServiceImpl implements PersonStreamService {

  private final int HIGH_RANK_VALUE = 4;
  private final PersonRepository personRepository;

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
    return Flux.fromIterable(
        personRepository
            .getAll()
            .toStream()
            .filter(person -> person.getRank() >= HIGH_RANK_VALUE)
            .collect(Collectors.toList()));
  }

  @Override
  public Flux<String> getNicknames() {
    Function<Person, String> nicknames = person -> person.getNickname();
    Flux<String> stream = personRepository.getAll().map(nicknames);
    return stream;
  }
}
