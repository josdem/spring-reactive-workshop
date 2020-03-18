package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.service.FluxMonoProcessor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class FluxMonoProcessorTest {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private FluxMonoProcessor processor;

    @Test
    @DisplayName("should process long names using mono")
    void shouldProcessLongNamesUsingMono(){
        String name = "JoseMorales";
        String expectedName = "JOSEMORALES";

        Mono<String> processedName = processor.processName(name);
        processedName.subscribe(
                value -> {
                    assertEquals(value, expectedName, "should validate name");
                },
                error -> log.error("Error: {}", error),
                () -> log.info("complete")
        );
    }

    @Test
    @DisplayName("should process a short name")
    void shouldProcessShortName(){
        String name = "Josh";
        String expectedName = "Invalid name";

        Mono<String> processedName = processor.processName(name);
        processedName.subscribe(
                value -> {
                    assertEquals(value, expectedName, "should validate name");
                },
                error -> log.error("Error: {}", error),
                () -> log.info("complete")
        );
    }

}
