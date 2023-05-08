package com.example.destinationbucketlist.controller;

import com.example.destinationbucketlist.DTOs.AppUserDTO;
import com.example.destinationbucketlist.mapper.Mapper;
import com.example.destinationbucketlist.model.AppUser;
import com.example.destinationbucketlist.model.PrivateDestination;
import com.example.destinationbucketlist.service.AppUserService;
import com.example.destinationbucketlist.service.PrivateDestinationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private PrivateDestinationService privateDestinationService;

    @Autowired
    private Mapper mapper;

    @GetMapping("/appusers")
    List<AppUserDTO> getAllUsers() {
        return appUserService.getAllUsers()
                .stream()
                .map(mapper::toAppUserDTO)
                .collect(Collectors.toList());
    }

    @GetMapping("/appusers/{userId}")
    Optional<AppUserDTO> getUserById(@PathVariable Integer userId) {
        return appUserService.getUserById(userId)
                .stream()
                .map(mapper::toAppUserDTO)
                .findFirst();
    }

    @PostMapping("/appusers")
    void addUser(@RequestBody AppUser appUser) {
        this.appUserService.addUser(appUser);
    }

    @PutMapping("/appusers/{userId}")
    void updateUser(@RequestBody AppUser appUser, @PathVariable Integer userId) {
        appUser.setId(userId);
        appUserService.updateUser(appUser);
    }

    @DeleteMapping("/appusers/{userId}")
    void deleteUser(@PathVariable Integer userId) {
        appUserService.deleteUser(userId);
    }

    @PostMapping("/appusers/{userId}/destination")
    void addPrivateDestToUser(@PathVariable Integer userId, @RequestBody PrivateDestination privateDestination) {
        AppUser appUser = appUserService.getUserById(userId).orElse(null);
        privateDestination.setAppUser(appUser);
        privateDestinationService.addPrivateDestination(privateDestination);
        appUser.getPrivateDestinations().add(privateDestination);
        appUserService.addUser(appUser);
    }
}
