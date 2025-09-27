package com.example.bankcards.util.masking;

import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JacksonConfig {

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer maskingCustomizer() {
        return builder -> builder.serializerByType(String.class, new MaskingSerializer());
    }
}
