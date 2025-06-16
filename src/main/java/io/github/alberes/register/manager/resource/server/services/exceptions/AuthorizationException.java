package io.github.alberes.register.manager.resource.server.services.exceptions;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class AuthorizationException extends RuntimeException{

    private int status;

    public AuthorizationException(String message) {
        super(message);
    }

    public AuthorizationException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuthorizationException(int status, String message) {
        super(message);
        this.status = status;
    }

    public AuthorizationException(int status, String message, Throwable cause) {
        super(message, cause);
        this.status = status;
    }

}