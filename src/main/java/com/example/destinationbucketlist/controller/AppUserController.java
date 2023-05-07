package com.example.destinationbucketlist.controller;

import com.example.destinationbucketlist.model.AppUser;
import com.example.destinationbucketlist.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AppUserController {

    @Autowired
    private AppUserService appUserService;

    @GetMapping("/appusers")
    List<AppUser> getAllUsers() {
        return appUserService.getAllUsers();
    }

    @GetMapping("/appusers/{userId}")
    AppUser getUserById(@PathVariable Integer userId) {
        return appUserService.getUserById(userId).orElse(null);
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
}
