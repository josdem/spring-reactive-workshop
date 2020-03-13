package com.jos.dem.spring.reactive.workshop.procesor;

import com.jos.dem.spring.reactive.workshop.model.Person;
import com.jos.dem.spring.reactive.workshop.repository.PersonRepository;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import reactor.core.publisher.MonoProcessor;

@Component
@RequiredArgsConstructor
public class PersonProcessor {

  private final PersonRepository personRepository;

  private Logger log = LoggerFactory.getLogger(this.getClass());

  public void processPerson(String nickname){
    MonoProcessor<String> future = MonoProcessor.create();

    Consumer<String> checkPerson = (name) -> {
      Mono<Person> person = personRepository.getByNickname(name);
      log.info("person: {}", Mono.just(person));
    };

    Mono<String> engine = future
        .doOnNext(checkPerson)
        .doOnSuccess(name -> {
          log.info("Person with nickname: {} was found", name);
        })
        .doOnError(ex -> System.out.println("Error: " + ex.getMessage()));

    engine.subscribe(System.out::println);

    future.onNext(nickname);
    future.block();
  }

}
