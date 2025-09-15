package com.campus1.config;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.codecentric.boot.admin.server.domain.values.Registration;

public abstract class RegistrationMixin {
    @JsonCreator
    public RegistrationMixin(
        @JsonProperty("name") String name,
        @JsonProperty("managementUrl") String managementUrl,
        @JsonProperty("healthUrl") String healthUrl,
        @JsonProperty("serviceUrl") String serviceUrl,
        @JsonProperty("metadata") java.util.Map<String, String> metadata
    ) {}
}