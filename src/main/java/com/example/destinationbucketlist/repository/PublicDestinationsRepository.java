package com.example.destinationbucketlist.repository;

import com.example.destinationbucketlist.model.PublicDestination;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PublicDestinationsRepository extends JpaRepository<PublicDestination, Integer> {
}
