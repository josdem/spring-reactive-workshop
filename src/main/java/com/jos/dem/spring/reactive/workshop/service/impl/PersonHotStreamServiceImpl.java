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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonHotStreamServiceImpl implements PersonHotStreamService {

  private final PersonRepository personRepository;
  private final Map<String, Person> persons = new HashMap<>();

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
    persons.put("josdem", createNewPerson());

    Consumer<String> checkEmp =
        (n) -> {
          if (persons.get(nickname) == null) {
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
                      "Person's email is {}", persons.get(n).getEmail());
                  log.info(
                      "Person's rank is: {}", persons.get(n).getRank());
                })
            .doOnTerminate(() -> log.info("Transaction finished"))
            .doOnError(ex -> log.info("Error: " + ex.getMessage()));

    engine.subscribe();
    return future;
  }

  private Person createNewPerson(){
      return new Person("josdem", "josdem@email.com", 5);
  }
}
