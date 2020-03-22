package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.service.PersonHotStreamService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PersonHotStreamServiceTest {

    @Autowired
    private PersonHotStreamService service;

    @Test
    @DisplayName("should get flowy nicknames")
    void shouldGetFlowyNicknames(){
        List<String> result = new ArrayList<>();
        service.freeFlow().subscribe(result::add);
        assertEquals(5, result.size(), "should have five elements");
    }

}
