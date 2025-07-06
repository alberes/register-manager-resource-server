package io.github.alberes.register.manager.resource.server.constants;

import java.util.Set;

public interface Constants {

    public static final String ADMIN = "ADMIN";
    public static final String HAS_ROLE_ADMIN = "hasRole('ADMIN')";
    public static final String HAS_ROLE_ADMIN_USER = "hasRole('ADMIN') || hasRole('USER')";
    public static final String UNAUTHORIZED_MESSAGE = "The user can only access resources that belong to him.";
    public static final String SLASH = "/";
    public static final String REGISTRATION_WITH_E_MAIL = "Registration with e-mail ";
    public static final String REGISTRATION_WITH_E_CLIENT_ID = "Registration with client-id ";
    public static final String HAS_ALREADY_BEEN_REGISTERED = " has already been registered!";
    public static final String OBJECT_NOT_FOUND_ID = "Object not found! Id: ";
    public static final String TYPE = ", Type: ";
    public static final String X_FORWARDED_FOR = "X-Forwarded-For";
    public static final String USER_AGENT = "User-Agent";
    public static final String APP_NAME = "app-name";
    public static final String API_V1_LOGIN = "/api/v1/login";
    public static final Set<String> SWAGGERS = Set.of("/v2/api-docs/**", "/v3/api-docs/**", "/swagger-resources/**",
            "/swagger-ui.html", "/swagger-ui/**", "/webjars/**", "/actuator/**");
    public static final String BLANK = "";
}
