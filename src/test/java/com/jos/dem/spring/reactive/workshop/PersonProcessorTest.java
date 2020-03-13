package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.procesor.PersonProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PersonProcessorTest {

  @Autowired
  private PersonProcessor processor;

  @Test
  void shouldCallMonoProcessorEmployee() {
    String nickname = "josdem";
    processor.processPerson(nickname);
  }

}
