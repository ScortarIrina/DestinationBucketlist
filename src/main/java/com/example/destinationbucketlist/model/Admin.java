package com.example.destinationbucketlist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
public class Admin extends AbstractUser {

    @OneToMany
    private List<AppUser> appUsers;

    public Admin() {
    }
}
