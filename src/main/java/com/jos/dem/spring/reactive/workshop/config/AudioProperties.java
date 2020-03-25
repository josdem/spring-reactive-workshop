package com.jos.dem.spring.reactive.workshop.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "audio")
public class AudioProperties {
    private String silence;
    private int bufferSize;
}
