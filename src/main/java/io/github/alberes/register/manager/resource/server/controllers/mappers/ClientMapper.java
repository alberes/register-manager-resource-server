package io.github.alberes.register.manager.resource.server.controllers.mappers;

import io.github.alberes.register.manager.resource.server.controllers.dto.ClientDto;
import io.github.alberes.register.manager.resource.server.controllers.dto.ClientReportDto;
import io.github.alberes.register.manager.resource.server.domains.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    @Mapping(source = "clientId", target = "clientId")
    @Mapping(source = "clientSecret", target = "clientSecret")
    @Mapping(source = "redirectURI", target = "redirectURI")
    @Mapping(source = "scope", target = "scope")
    public Client toEntity(ClientDto dto);

    @Mapping(source = "clientId", target = "clientId")
    @Mapping(source = "clientSecret", target = "clientSecret")
    @Mapping(source = "redirectURI", target = "redirectURI")
    @Mapping(source = "scope", target = "scope")
    public ClientDto toDto(Client client);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "clientId", target = "clientId")
    @Mapping(source = "redirectURI", target = "redirectURI")
    @Mapping(source = "scope", target = "scope")
    @Mapping(source = "lastModifiedDate", target = "lastModifiedDate")
    @Mapping(source = "createdDate", target = "createdDate")
    public ClientReportDto toReportDto(Client client);

}
