package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.model.Person;
import com.jos.dem.spring.reactive.workshop.service.PersonScheduledStreamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class PersonScheduledStreamServiceTest {

    @Autowired
    private PersonScheduledStreamService service;

    @Test
    @DisplayName("should execute a publisher in a executor")
    void shouldExecutePublisherInExecutor(){
        List<Person> result = new ArrayList<>();
        service.createPublisherThread();
        assertEquals(3, result.size(), "should be three persons");
    }

    @Test
    @DisplayName("should execute a publisher subscriber in a executor")
    void shouldExecutePublisherSubscriberExecutor() throws Exception {
        List<Person> result = new ArrayList<>();
        service.createPublisherSubscriberWorkers();
        Thread.sleep(5000);
        assertEquals(3, result.size(), "should be three persons");
    }

}
