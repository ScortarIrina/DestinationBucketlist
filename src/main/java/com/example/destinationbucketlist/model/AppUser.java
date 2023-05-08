package com.example.destinationbucketlist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

import java.util.List;

@Entity
public class AppUser extends AbstractUser {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "appUser")
    @JsonIgnoreProperties("appUser")
    List<PrivateDestination> privateDestinations;

    public AppUser(Integer id, String firstName, String lastName, String username, String email, String password,
                   List<PrivateDestination> privateDestinations) {
        super(id, firstName, lastName, username, email, password);
        this.privateDestinations = privateDestinations;
    }

    public List<PrivateDestination> getPrivateDestinations() {
        return privateDestinations;
    }

    public void setPrivateDestinations(List<PrivateDestination> privateDestinations) {
        this.privateDestinations = privateDestinations;
    }

    public AppUser() {
    }
}
