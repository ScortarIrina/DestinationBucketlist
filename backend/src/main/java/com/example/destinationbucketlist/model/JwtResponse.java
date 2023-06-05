package com.example.destinationbucketlist.model;

import java.io.Serializable;
import java.util.List;

public class JwtResponse implements Serializable {

    private static final long serialVersionUID = -8091879091924046844L;
    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private List<String> profiles;

    public JwtResponse(String accessToken, Long id, String username, List<String> profiles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.profiles = profiles;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getProfiles() {
        return profiles;
    }
}
