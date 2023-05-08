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

    // localhost:8080/privateDestinations
    @GetMapping("/privateDestinations")
    List<PrivateDestinationDTO> getAllPrivateDestinations() {
        return privateDestinationService.getAllPrivateDestinations()
                .stream()
                .map(mapper::toPrivateDestinationDTO)
                .collect(Collectors.toList());
    }

    // localhost:8080/privateDestinations/{id}
    @GetMapping("/privateDestinations/{privateDestinationId}")
    Optional<PrivateDestinationDTO> getPrivateDestinationById(@PathVariable Integer privateDestinationId) {
        return privateDestinationService.getPrivateDestinationById(privateDestinationId)
                .stream()
                .map(mapper::toPrivateDestinationDTO)
                .findFirst();
    }

    // localhost:8080/privateDestinations
    // the JSON body
    // {
    //    "geolocation": "geo2",
    //    "title": "Paris",
    //    "image": "image Paris",
    //    "description": "rainy",
    //    "startDate": "2023-06-19",
    //    "endDate": "2023-06-24"
    //}
    @PostMapping("/privateDestinations")
    void addPrivateDestination(@RequestBody PrivateDestination privateDestination) {
        this.privateDestinationService.addPrivateDestination(privateDestination);
    }

    // localhost:8080/privateDestinations/{id}
    // {
    //    "geolocation": "geo2",
    //    "title": "Paris",
    //    "image": "image Paris",
    //    "description": "rainy",
    //    "startDate": "2023-06-19",
    //    "endDate": "2023-06-24"
    //}
    @PutMapping("/privateDestinations/{privateDestinationId}")
    void updatePrivateDestination(@RequestBody PrivateDestination privateDestination, @PathVariable Integer privateDestinationId) {
        privateDestination.setDestinationID(privateDestinationId);
        privateDestinationService.updatePrivateDestination(privateDestination);
    }

    // localhost:8080/privateDestinations/{id}
    @DeleteMapping("/privateDestinations/{privateDestinationId}")
    void deletePrivateDestination(@PathVariable Integer privateDestinationId) {
        privateDestinationService.deletePrivateDestination(privateDestinationId);
    }
}
