package com.killiann.paintify.payloads;

import java.io.Serializable;
import java.util.List;

public class LoginResponse implements Serializable {

    public String token;
    public Long id;


    public LoginResponse(String token, Long id) {
        this.token = token;
        this.id = id;
    }
}