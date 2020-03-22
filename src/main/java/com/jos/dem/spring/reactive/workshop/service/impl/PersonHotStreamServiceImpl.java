package com.jos.dem.spring.reactive.workshop.service.impl;

import com.jos.dem.spring.reactive.workshop.model.Person;
import com.jos.dem.spring.reactive.workshop.repository.PersonRepository;
import com.jos.dem.spring.reactive.workshop.service.PersonHotStreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.ConnectableFlux;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * TODO: seems like is not returning cache elements
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class PersonHotStreamServiceImpl implements PersonHotStreamService {

    private final PersonRepository personRepository;

    @Override
    public ConnectableFlux<String> freeFlow() {
        List<String> rosterNames = new ArrayList<>();
        Function<Person, String> nicknames = nickname -> nickname.getNickname().toUpperCase();
        ConnectableFlux<String> flowyNames = personRepository.getAll().map(nicknames).cache().publish();
        flowyNames.subscribe(rosterNames::add);
        rosterNames.forEach(n -> log.info("n: {}", n));
        return flowyNames;
    }
}
