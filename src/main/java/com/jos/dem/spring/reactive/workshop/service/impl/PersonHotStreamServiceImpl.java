package com.jos.dem.spring.reactive.workshop.service.impl;

import com.jos.dem.spring.reactive.workshop.model.Person;
import com.jos.dem.spring.reactive.workshop.repository.PersonRepository;
import com.jos.dem.spring.reactive.workshop.service.PersonHotStreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonHotStreamServiceImpl implements PersonHotStreamService {

  private final PersonRepository personRepository;

  /** TODO: seems like is not returning cache elements */
  @Override
  public ConnectableFlux<String> freeFlow() {
    List<String> rosterNames = new ArrayList<>();
    Function<Person, String> nicknames = nickname -> nickname.getNickname().toUpperCase();
    ConnectableFlux<String> flowyNames = personRepository.getAll().map(nicknames).cache().publish();
    flowyNames.subscribe(rosterNames::add);
    rosterNames.forEach(n -> log.info("n: {}", n));
    return flowyNames;
  }

  @Override
  public MonoProcessor<String> monoProcessorGetPerson(String nickname) {
    MonoProcessor<String> future = MonoProcessor.create();

    Consumer<String> checkEmp =
        (n) -> {
          if (personRepository.getByNickname(n).block() == null) {
            log.info("Person w/nickname: " + nickname + " does not exists.");
          } else {
            log.info("Person w/nickname: " + nickname + " exists.");
          }
        };

    Mono<String> engine =
        future
            .doOnNext(checkEmp)
            .doOnSuccess(
                n -> {
                  log.info(
                      "Person's email is " + personRepository.getByNickname(n).block().getEmail());
                  log.info(
                      "Person's rank is: " + personRepository.getByNickname(n).block().getRank());
                })
            .doOnTerminate(() -> log.info("Transaction finished"))
            .doOnError(ex -> log.info("Error: " + ex.getMessage()));

    engine.subscribe(System.out::println);
    return future;
  }
}
