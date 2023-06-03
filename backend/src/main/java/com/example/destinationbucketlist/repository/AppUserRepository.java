package com.example.destinationbucketlist.repository;

import com.example.destinationbucketlist.model.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser, Integer> {
}
