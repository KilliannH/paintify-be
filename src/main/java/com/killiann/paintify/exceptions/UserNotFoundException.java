package com.killiann.paintify.exceptions;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Long id) {
        super("Could not find user with id: " + id);
    }

    public UserNotFoundException(String facebookId) { super("Could not find user with facebookId: " + facebookId); }
}