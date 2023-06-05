package com.example.destinationbucketlist.repository;

import com.example.destinationbucketlist.model.AuthCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AuthCodeRepository extends JpaRepository<AuthCode, Long> {
    @Query(value = "select ac from AuthCode ac where ac.status = 'PENDING' and ac.code = ?1")
    Optional<AuthCode> findByCodePending(String code);
}
