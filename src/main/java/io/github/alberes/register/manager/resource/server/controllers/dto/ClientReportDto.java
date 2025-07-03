package io.github.alberes.register.manager.resource.server.controllers.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(name = "ClientReport")
public record ClientReportDto(
        String id,
        String clientId,
        String redirectURI,
        String scope,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime lastModifiedDate,
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        LocalDateTime createdDate) {
}
