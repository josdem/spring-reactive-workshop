package com.jos.dem.spring.reactive.workshop.service.impl;

import com.jos.dem.spring.reactive.workshop.model.Person;
import com.jos.dem.spring.reactive.workshop.repository.PersonRepository;
import com.jos.dem.spring.reactive.workshop.service.PersonTransformStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;

import java.util.List;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PersonTransformStreamImpl implements PersonTransformStream {

  private final PersonRepository personRepository;

  @Override
  public Flux<String> concatWithNames(List<String> others) {
    Function<Person, String> nicknames = person -> person.getNickname();
    Flux<String> concatNames =
        personRepository
            .getAll()
            .map(nicknames)
            .concatWith(Flux.fromIterable(others))
            .map(String::toUpperCase)
            .sort((s1, s2) -> s1.compareTo(s2));
    return concatNames;
  }

  @Override
  public Flux<GroupedFlux<String, String>> groupNicknames() {
    Function<Person, String> nicknames = person -> person.getNickname().toUpperCase();
    Flux<GroupedFlux<String, String>> groupedNicknamwes =
        personRepository.getAll().map(nicknames).groupBy(key -> key.charAt(0) + "");
    return groupedNicknamwes;
  }
}
