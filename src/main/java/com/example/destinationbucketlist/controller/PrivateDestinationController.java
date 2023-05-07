package com.example.destinationbucketlist.controller;

import com.example.destinationbucketlist.model.AppUser;
import com.example.destinationbucketlist.model.PrivateDestination;
import com.example.destinationbucketlist.service.PrivateDestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PrivateDestinationController {

    @Autowired
    private PrivateDestinationService privateDestinationService;

    @GetMapping("/privateDestinations")
    List<PrivateDestination> getAllPrivateDestinations() {
        return privateDestinationService.getAllPrivateDestinations();
    }

    @GetMapping("/privateDestinations/{privateDestinationId}")
    PrivateDestination getPrivateDestinationById(@PathVariable Integer privateDestinationId) {
        return privateDestinationService.getPrivateDestinationById(privateDestinationId).orElse(null);
    }

    @PostMapping("/privateDestinations")
    void addPrivateDestination(@RequestBody PrivateDestination privateDestination) {
        this.privateDestinationService.addPrivateDestination(privateDestination);
    }

    @PutMapping("/privateDestinations/{privateDestinationId}")
    void updatePrivateDestination(@RequestBody PrivateDestination privateDestination, @PathVariable Integer privateDestinationId) {
        privateDestination.setDestinationID(privateDestinationId);
        privateDestinationService.updatePrivateDestination(privateDestination);
    }

    @DeleteMapping("/privateDestinations/{privateDestinationId}")
    void deletePrivateDestination(@PathVariable Integer privateDestinationId) {
        privateDestinationService.deletePrivateDestination(privateDestinationId);
    }
}
