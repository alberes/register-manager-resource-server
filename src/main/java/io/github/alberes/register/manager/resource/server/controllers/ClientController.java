package io.github.alberes.register.manager.resource.server.controllers;

import io.github.alberes.register.manager.resource.server.constants.Constants;
import io.github.alberes.register.manager.resource.server.controllers.dto.ClientDto;
import io.github.alberes.register.manager.resource.server.controllers.dto.ClientReportDto;
import io.github.alberes.register.manager.resource.server.controllers.dto.ClientUpdateDto;
import io.github.alberes.register.manager.resource.server.controllers.exceptions.dto.StandardErrorDto;
import io.github.alberes.register.manager.resource.server.controllers.mappers.ClientMapper;
import io.github.alberes.register.manager.resource.server.domains.Client;
import io.github.alberes.register.manager.resource.server.services.ClientService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/clients")
@RequiredArgsConstructor
@Tag(name = "Client")
public class ClientController implements GenericController{

    private final ClientService service;

    private final ClientMapper mapper;

    @PostMapping
    @PreAuthorize(Constants.HAS_ROLE_ADMIN)
    @Operation(summary = "Save client.", description = "Save client in database. Only user with profile ADMIN can create client.",
        operationId = "saveClient")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Saved with success."),
            @ApiResponse(responseCode = "400", description = "Validation error.",
                    content = @Content(schema = @Schema(implementation = StandardErrorDto.class))),
            @ApiResponse(responseCode = "403", description = Constants.UNAUTHORIZED_MESSAGE,
                    content = @Content(schema = @Schema(implementation = StandardErrorDto.class))),
            @ApiResponse(responseCode = "409", description = "There is a Client with clientId.",
                    content = @Content(schema = @Schema(implementation = StandardErrorDto.class)))
    })
    public ResponseEntity<Void> save(@RequestBody @Valid ClientDto dto){
        Client client = this.mapper.toEntity(dto);
        client = this.service.save(client);
        return ResponseEntity
                .created(this.createURI("/{id}", client.getId().toString()))
                .build();
    }

    @GetMapping("/{clientId}")
    @PreAuthorize(Constants.HAS_ROLE_ADMIN_USER)
    @Operation(summary = "Find client.", description = "Find client in database. User with profile ADMIN can access any users and other profiles can only access resources that belong to him.",
        operationId = "findUser")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found client in database."),
            @ApiResponse(responseCode = "403", description = Constants.UNAUTHORIZED_MESSAGE,
                    content = @Content(schema = @Schema(implementation = StandardErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Could not find client in database.",
                    content = @Content(schema = @Schema(implementation = StandardErrorDto.class)))
    })
    public ResponseEntity<ClientDto> find(@PathVariable String clientId){
        Client client = this.service.find(clientId);
        client.setClientSecret(null);
        ClientDto dto = this.mapper.toDto(client);
        return ResponseEntity.ok(dto);
    }

    @PutMapping("/{clientId}")
    @PreAuthorize(Constants.HAS_ROLE_ADMIN_USER)
    @Operation(summary = "Update client.", description = "Update with success. User with profile ADMIN can access any clients and other profiles can only access resources that belong to him.",
        operationId = "updateClient")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Update with success."),
            @ApiResponse(responseCode = "403", description = Constants.UNAUTHORIZED_MESSAGE,
                    content = @Content(schema = @Schema(implementation = StandardErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Could not find clint in database.",
                    content = @Content(schema = @Schema(implementation = StandardErrorDto.class)))
    })
    public ResponseEntity<Void> update(@PathVariable String clientId, @RequestBody @Valid ClientUpdateDto dto){
        Client client = new Client();
        client.setClientId(clientId);
        client.setRedirectURI(dto.redirectURI());
        this.service.update(client);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{clientId}")
    @PreAuthorize(Constants.HAS_ROLE_ADMIN_USER)
    @Operation(summary = "Delete client.", description = "Delete with success. User with profile ADMIN can access any clients and other profiles can only access resources that belong to him.",
        operationId = "deleteClient")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Delete client in database."),
            @ApiResponse(responseCode = "403", description = Constants.UNAUTHORIZED_MESSAGE,
                    content = @Content(schema = @Schema(implementation = StandardErrorDto.class))),
            @ApiResponse(responseCode = "404", description = "Could not find client in database.",
                    content = @Content(schema = @Schema(implementation = StandardErrorDto.class)))
    })
    public ResponseEntity<Void> delete(@PathVariable String clientId){
        this.service.delete(clientId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    @PreAuthorize(Constants.HAS_ROLE_ADMIN)
    @Operation(summary = "Find clients.", description = "Find clients in database. User with profile ADMIN can access any clients and other profiles can only access resources that belong to him.",
        operationId = "findUsers")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Find clients in database."),
            @ApiResponse(responseCode = "403", description = Constants.UNAUTHORIZED_MESSAGE,
                    content = @Content(schema = @Schema(implementation = StandardErrorDto.class))),
            @ApiResponse(responseCode = "204", description = "Could not find clients in database.",
                    content = @Content(schema = @Schema(hidden = true)))
    })
    public ResponseEntity<Page<ClientReportDto>> page(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "clientId") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction,
            @AuthenticationPrincipal Jwt jwt
    ){
        Page<Client> pageClients = this.service.findPage(page, linesPerPage, orderBy, direction);
        if(pageClients.getTotalElements() == 0){
            return ResponseEntity.noContent().build();
        }
        Page<ClientReportDto> pageReport = pageClients
                .map(c -> new ClientReportDto(
                        c.getId().toString(), c.getClientId(), c.getRedirectURI(), c.getScope(),
                        c.getLastModifiedDate(), c.getCreatedDate()));
        return ResponseEntity.ok(pageReport);
    }
/*
    @GetMapping("/authenticated")
    @PreAuthorize(Constants.HAS_ROLE_ADMIN_USER)
    @Operation(summary = "Find profile user.", description = "Find profile user in database. User with profile ADMIN can access any users and other profiles can only access resources that belong to him.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Found user in database."),
            @ApiResponse(responseCode = "403", description = Constants.UNAUTHORIZED_MESSAGE,
                    content = @Content(schema = @Schema(implementation = StandardErrorDto.class)))
    })
    public ResponseEntity<UserAccountProfileDto> userAuthentication(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal userPrincipal = (UserPrincipal)authentication.getPrincipal();
        UserAccountProfileDto userAccountProfileDto = this.mapper.toProfileDto(userPrincipal.getUserAccount());
        return ResponseEntity.ok(userAccountProfileDto);
    }*/

    @GetMapping("/claims")
    @PreAuthorize(Constants.HAS_ROLE_ADMIN_USER)
    public Map<String, Object> getClaims(@AuthenticationPrincipal Jwt jwt) {
        return jwt.getClaims(); // Retorna todas as claims do token JWT
    }

}
