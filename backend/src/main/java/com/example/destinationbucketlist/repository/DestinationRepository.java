package com.example.destinationbucketlist.repository;

import com.example.destinationbucketlist.model.Destination;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DestinationRepository extends JpaRepository<Destination, Integer> {
    @Query(value = "select d.* from Destination d left join auth_user au on d.user_id = au.id where " +
            "au.username = :username or d.published = true", nativeQuery = true)
    Page<Destination> findDestinationsByUsername(Pageable p, @Param("username") String username);

    @Query(value = "select d from Destination d where d.published = true")
    Page<Destination> findPublicDestinations(Pageable p);

    @Query(value = "select d.id, au.username from destination d inner join auth_user au on d.user_id = au.id where d.id in ?1 order by d.id", nativeQuery = true)
    List<Object[]> findUsernamesForDestinations(List<Integer> dIds);
}
