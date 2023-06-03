package com.example.destinationbucketlist.DTOs;

import java.util.List;

public class AppUserDTO {

    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> privateDestinations;

    public AppUserDTO(Integer id, String firstName, String lastName, String email, List<String> privateDestinations) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.privateDestinations = privateDestinations;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getPrivateDestinations() {
        return privateDestinations;
    }

    public void setPrivateDestinations(List<String> privateDestinations) {
        this.privateDestinations = privateDestinations;
    }
}
