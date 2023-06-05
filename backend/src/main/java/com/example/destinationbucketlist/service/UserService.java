package com.example.destinationbucketlist.service;

import com.example.destinationbucketlist.controller.UserController;
import com.example.destinationbucketlist.model.AuthUser;
import com.example.destinationbucketlist.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getAllUsers(int page, int pSize) {
        List<AuthUser> users = new ArrayList<>();

        Map<String, Object> response = new HashMap<>();

        if (page > -1) {
            Pageable p = PageRequest.of(page, pSize > 0 ? pSize : UserController.PAGE_SIZE, Sort.by("id"));

            Page<AuthUser> pageUsers = userRepository.findAll(p);

            response.put("users", pageUsers.getContent());
            response.put("currentPage", pageUsers.getNumber());
            response.put("totalItems", pageUsers.getTotalElements());
            response.put("totalPages", pageUsers.getTotalPages());

            return response;
        } else {
            userRepository.findAll().forEach(users::add);

            response.put("users", users);
            response.put("currentPage", 0);
            response.put("totalItems", users.size());
            response.put("totalPages", 1);
        }

        return response;
    }

    public Optional<AuthUser> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<AuthUser> getUserByUsername(String username) {
        return userRepository.findByUsernameConfirmed(username);
    }

    public void addUser(AuthUser user) {
        userRepository.save(user);
    }

    public void updateUser(AuthUser user, Long id) {
        userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}

