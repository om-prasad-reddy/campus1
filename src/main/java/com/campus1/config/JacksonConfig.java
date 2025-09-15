package com.campus1.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import de.codecentric.boot.admin.server.domain.values.Registration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.SimpleDateFormat;

@Configuration
public class JacksonConfig {

    // Define a mixin for Registration to handle Spring Boot Admin deserialization
    public abstract class RegistrationMixin {
        @com.fasterxml.jackson.annotation.JsonCreator
        public RegistrationMixin(
            @com.fasterxml.jackson.annotation.JsonProperty("name") String name,
            @com.fasterxml.jackson.annotation.JsonProperty("managementUrl") String managementUrl,
            @com.fasterxml.jackson.annotation.JsonProperty("healthUrl") String healthUrl,
            @com.fasterxml.jackson.annotation.JsonProperty("serviceUrl") String serviceUrl,
            @com.fasterxml.jackson.annotation.JsonProperty("metadata") java.util.Map<String, String> metadata
        ) {}
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // Register Jackson modules from original JacksonConfig
        mapper.registerModule(new ParameterNamesModule());
        mapper.registerModule(new Jdk8Module());
        mapper.registerModule(new JavaTimeModule());
        // Add Registration mixin for Spring Boot Admin
        mapper.addMixIn(Registration.class, RegistrationMixin.class);
        // Apply User_JacksonConfig settings
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        mapper.setDateFormat(dateFormat);
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return mapper;
    }
}