package com.example.destinationbucketlist.service;

import com.example.destinationbucketlist.model.AppUser;
import com.example.destinationbucketlist.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public List<AppUser> getAllUsers() {
        List<AppUser> appUsers = new ArrayList<>();
        appUserRepository.findAll().forEach(appUsers::add);
        return appUsers;
    }

    public void addUser(AppUser appUser) {
        appUserRepository.save(appUser);
    }

    public Optional<AppUser> getUserById(Integer userId) {
        return appUserRepository.findById(userId);
    }
}
