package io.github.alberes.register.manager.resource.server.controllers.exceptions.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "FieldErro")
public record FieldErroDto (String field, String message){
}