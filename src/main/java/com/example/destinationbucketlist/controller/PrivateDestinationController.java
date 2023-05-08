package com.example.destinationbucketlist.controller;

import com.example.destinationbucketlist.DTOs.PrivateDestinationDTO;
import com.example.destinationbucketlist.mapper.Mapper;
import com.example.destinationbucketlist.model.AppUser;
import com.example.destinationbucketlist.model.PrivateDestination;
import com.example.destinationbucketlist.service.PrivateDestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class PrivateDestinationController {

    @Autowired
    private PrivateDestinationService privateDestinationService;

    @Autowired
    private Mapper mapper;

    @GetMapping("/privateDestinations")
    List<PrivateDestinationDTO> getAllPrivateDestinations() {
        return privateDestinationService.getAllPrivateDestinations()
                .stream()
                .map(mapper::toPrivateDestinationDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/privateDestinations/{privateDestinationId}")
    Optional<PrivateDestinationDTO> getPrivateDestinationById(@PathVariable Integer privateDestinationId) {
        return privateDestinationService.getPrivateDestinationById(privateDestinationId)
                .stream()
                .map(mapper::toPrivateDestinationDTO)
                .findFirst();
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
