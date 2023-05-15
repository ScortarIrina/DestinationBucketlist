package com.example.destinationbucketlist.controller;

import com.example.destinationbucketlist.model.PublicDestination;
import com.example.destinationbucketlist.service.PublicDestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class PublicDestinationsController {
    @Autowired
    private PublicDestinationService publicDestinationService;

    // localhost:8080/publicDestinations
    @GetMapping("/publicDestinations")
    List<PublicDestination> getAllPrivateDestinations() {
        return publicDestinationService.getAllPublicDestinations().stream().toList();
    }

    // localhost:8080/publicDestinations/{id}
    @GetMapping("/publicDestinations/{publicDestinationId}")
    Optional<PublicDestination> getPublicDestinationById(@PathVariable Integer publicDestinationId) {
        return publicDestinationService.getPublicDestinationById(publicDestinationId)
                .stream()
                .findFirst();
    }

    @PostMapping("/publicDestinations")
    void addPublicDestination(@RequestBody PublicDestination publicDestination) {
        this.publicDestinationService.addPublicDestination(publicDestination);
    }

    // localhost:8080/publicDestinations/{id}
    @PutMapping("/publicDestinations/{publicDestinationId}")
    void updatePublicDestination(@RequestBody PublicDestination publicDestination, @PathVariable Integer publicDestinationId) {
        publicDestination.setDestinationID(publicDestinationId);
        publicDestinationService.updatePrivateDestination(publicDestination);
    }

    // localhost:8080/publicDestinations/{id}
    @DeleteMapping("/publicDestinations/{publicDestinationId}")
    void deletePublicDestination(@PathVariable Integer publicDestinationId) {
        publicDestinationService.deletePublicDestination(publicDestinationId);
    }
}
