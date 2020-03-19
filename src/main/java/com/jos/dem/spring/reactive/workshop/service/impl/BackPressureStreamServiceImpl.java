package com.jos.dem.spring.reactive.workshop.service.impl;

import com.jos.dem.spring.reactive.workshop.model.Person;
import com.jos.dem.spring.reactive.workshop.repository.PersonRepository;
import com.jos.dem.spring.reactive.workshop.service.BackPressureStreamService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class BackPressureStreamServiceImpl implements BackPressureStreamService {

  private final PersonRepository personRepository;

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Override
  public Mono<Person> selectOnePerson() {
    Runnable cancel = () -> log.info("Stream canceled");

    Mono<Person> person =
        personRepository.getAll().doOnCancel(cancel).log().take(1).singleOrEmpty();
    return person;
  }

  @Override
  public Flux<Person> selectSomePersons() {
    Flux<Person> persons = personRepository.getAll().log().skip(2).take(Duration.ofMillis(4));
    return persons;
  }

  @Override
  public Flux<List<Person>> selectBufferedPersons() {
    Flux<List<Person>> bufferedPersons = personRepository.getAll().log().buffer(2);
    return bufferedPersons;
  }

  @Override
  public Flux<String> getTimedNicknames() {
    Function<Person, String> nicknames = person -> person.getNickname();
    Supplier<Flux<String>> deferredTask = () -> personRepository.getAll().map(nicknames);
    Flux<String> timedDefer = Flux.defer(deferredTask).log().timeout(Duration.ofMillis(320));
    return timedDefer;
  }
}
