package com.example.destinationbucketlist.repository;

import com.example.destinationbucketlist.model.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<AuthUser, Long> {
    @Query(value = "select u from AuthUser u inner join u.authCode ac where ac.status <> 'PENDING' and u.username = ?1")
    Optional<AuthUser> findByUsernameConfirmed(String username);
}

