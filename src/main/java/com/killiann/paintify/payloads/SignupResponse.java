package com.killiann.paintify.payloads;

import java.io.Serializable;
import java.util.List;

public class SignupResponse implements Serializable {

    public String token;
    public Long id;
    public String username;
    public String email;
    public List<String> roles;

    public String imageUrl;


    public SignupResponse(String token, Long id, String username, String email, List<String> roles, String imageUrl) {
        this.token = token;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
        this.imageUrl = imageUrl;
    }
}