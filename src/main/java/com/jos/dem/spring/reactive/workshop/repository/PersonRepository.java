package com.jos.dem.spring.reactive.workshop.repository;

import com.jos.dem.spring.reactive.workshop.model.Person;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class PersonRepository {

  private final Map<String, Person> persons = new HashMap<>();

  @PostConstruct
  public void setup(){
    Stream.of(new Person("josdem", "joseluis.delacruz@gmail.com",5),
        new Person("tgrip", "tgrip@email.com", 4),
        new Person("edzero", "edzero@email.com", 3),
        new Person("skuarch", "skuarch@email.com", 3),
        new Person("jeduan", "jeduan@email.com", 5))
        .forEach(person -> persons.put(person.getNickname(), person));
  }

  public Flux<Person> getAll(){
    return Flux.fromIterable(persons.values());
  }

  public Mono<Person> getByNickname(String nickname){
    return Mono.just(persons.get(nickname));
  }

}
