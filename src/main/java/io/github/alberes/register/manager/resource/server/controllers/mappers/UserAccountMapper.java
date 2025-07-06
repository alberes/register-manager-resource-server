package io.github.alberes.register.manager.resource.server.controllers.mappers;

import io.github.alberes.register.manager.resource.server.controllers.dto.UserAccountDto;
import io.github.alberes.register.manager.resource.server.controllers.dto.UserAccountProfileDto;
import io.github.alberes.register.manager.resource.server.controllers.dto.UserAccountReportDto;
import io.github.alberes.register.manager.resource.server.domains.UserAccount;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAccountMapper {

    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "scopes", target = "scopes")
    public UserAccount toEntity(UserAccountDto dto);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "lastModifiedDate", target = "lastModifiedDate")
    @Mapping(source = "createdDate", target = "createdDate")
    public UserAccountReportDto toDto(UserAccount userAccount);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "name", target = "name")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "roles", target = "roles")
    public UserAccountProfileDto toProfileDto(UserAccount userAccount);

}
