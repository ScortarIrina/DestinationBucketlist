package com.example.destinationbucketlist.DTOs;

public class AuthCodeDTO {
    private Long id;
    private String status;

    private String expirationTimestamp;

    private String username;

    public AuthCodeDTO(Long id, String status, String expirationTimestamp, String username) {
        this.id = id;
        this.status = status;
        this.expirationTimestamp = expirationTimestamp;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getExpirationTimestamp() {
        return expirationTimestamp;
    }

    public void setExpirationTimestamp(String expirationTimestamp) {
        this.expirationTimestamp = expirationTimestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}

