package com.example.destinationbucketlist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OrderBy;

import java.util.List;

@Entity
public class Admin extends AbstractUser {

    @OneToMany
    @OrderBy("id")
    private List<AppUser> appUsers;

    public Admin() {
    }
}
