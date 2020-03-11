package com.jos.dem.spring.reactive.workshop;

import com.jos.dem.spring.reactive.workshop.service.FluxStreamer;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class SpringReactiveApplication {

  private String silenceFile =
    "file:/Users/moralej3/Temporal/brandon_silence.raw";
  private String outputFile = "/Users/moralej3/Temporal/brandon_output.raw";

  private Logger log = LoggerFactory.getLogger(this.getClass());

  public static void main(String[] args) {
    SpringApplication.run(SpringReactiveApplication.class, args);
  }

  @Bean
  CommandLineRunner run(FluxStreamer streamer) {
    return args -> {
      streamer.streamText("josdem")
          .subscribe(character -> log.info("text: {}", character));

      streamer.streamBinary(silenceFile)
          .subscribe(dataBuffer -> log.info("dataBuffer: {}", dataBuffer));

      Path path = Paths.get(outputFile);
      if(!Files.exists(path)){
        Files.createFile(path);
      }
      AsynchronousFileChannel fileChannel =
          AsynchronousFileChannel.open(path, StandardOpenOption.WRITE);
      copyFile(streamer.streamBinary(silenceFile), fileChannel);
    };
  }

  private void copyFile(Flux<DataBuffer> stream, AsynchronousFileChannel fileChannel){
    stream.subscribe(dataBuffer -> {
      log.info("dataBuffer: {}", dataBuffer);
      long position = 0;
      Future<Integer> operation = fileChannel.write(dataBuffer.asByteBuffer(), position);
      dataBuffer.asByteBuffer().clear();
      while (!operation.isDone());
    });
  }


}
