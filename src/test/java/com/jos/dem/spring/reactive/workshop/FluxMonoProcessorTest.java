package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.service.FluxMonoProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class FluxMonoProcessorTest {

  private Logger log = LoggerFactory.getLogger(this.getClass());

  @Autowired private FluxMonoProcessor processor;

  @Test
  @DisplayName("should process long names using mono")
  void shouldProcessLongNamesUsingMono() {
    String name = "JoseMorales";
    String expectedName = "JOSEMORALES";

    Mono<String> processedName = processor.processName(name);
    processedName.subscribe(
        value -> {
          assertEquals(value, expectedName, "should validate name");
        },
        error -> log.error("Error: {}", error),
        () -> log.info("complete"));
  }

  @Test
  @DisplayName("should process a short name")
  void shouldProcessShortName() {
    String name = "Josh";
    String expectedName = "Invalid name";

    Mono<String> processedName = processor.processName(name);
    processedName.subscribe(
        value -> {
          assertEquals(value, expectedName, "should validate name");
        },
        error -> log.error("Error: {}", error),
        () -> log.info("complete"));
  }

  @Test
  @DisplayName("should process a list of names")
  void shouldProcessListOfNames() {
    List<String> result = new ArrayList<>();
    List<String> names = Arrays.asList("josdem", "skye", "tgrip", "edzero", "jeduan");

    Flux<String> processedNames = processor.processingNames(names);
    processedNames.subscribe(
        value -> {
          result.add(value);
        },
        error -> log.error("Error: {}", error),
        () -> log.info("complete"));

    assertEquals(5, result.size(), "should have five values");
    assertEquals("EDZERO", result.get(0), "should be ordered asc");
  }

    @Test
    @DisplayName("should process an empty list")
    void shouldProcessAnEmptyList() {
        List<String> result = new ArrayList<>();

        Flux<String> processedNames = processor.processingNames(new ArrayList<>());
        processedNames.subscribe(
                value -> {
                    result.add(value);
                },
                error -> log.error("Error: {}", error),
                () -> log.info("complete"));

        assertEquals("Empty list", result.get(0), "should have an empty list value");
    }
}
