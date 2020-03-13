package com.jos.dem.spring.reactive.workshop.procesor;

import com.jos.dem.spring.reactive.workshop.model.Person;
import com.jos.dem.spring.reactive.workshop.repository.PersonRepository;
import java.util.function.Consumer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;
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
      Person person = personRepository.getByNickname(name).block();
      Assert.notNull(person.getNickname(), "should have nickname");
      Assert.notNull(person.getEmail(), "should have an email");
    };

    Mono<String> engine = future
        .doOnNext(checkPerson)
        .doOnSuccess(name -> {
          log.info("Person with nickname: {} was found", name);
        })
        .doOnError(ex -> log.error("Error: " + ex.getMessage()));

    engine.subscribe(x -> log.info("Running"));
    future.onNext(nickname);
  }

}
