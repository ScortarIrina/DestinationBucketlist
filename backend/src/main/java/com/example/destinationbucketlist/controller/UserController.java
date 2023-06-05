package com.example.destinationbucketlist.controller;

import com.example.destinationbucketlist.DTOs.UserDTO;
import com.example.destinationbucketlist.mapper.Mapper;
import com.example.destinationbucketlist.model.AuthUser;
import com.example.destinationbucketlist.service.UserService;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class UserController {
    protected final Log logger = LogFactory.getLog(getClass());

    public static int PAGE_SIZE = 100;

    @Autowired
    private UserService userService;

    @Autowired
    private Mapper mapper;

    @GetMapping("/users")
    ResponseEntity<Map<String, Object>> getAllUsers(@RequestParam Optional<String> page, @RequestParam Optional<String> size) {
        int p = Integer.parseInt(page.orElse("0"));

        int pSize = Integer.parseInt(size.orElse("0"));

        Map<String, Object> ret = userService.getAllUsers(p, pSize);

        List<UserDTO> dtos = ((List<AuthUser>) ret.get("users"))
                .stream()
                .map(mapper::toUserDTO)
                .collect(Collectors.toList());

        ret.put("users", dtos);

        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping("/users/{id}")
    Optional<AuthUser> getUserById(@PathVariable Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/users/username/{username}")
    Optional<UserDTO> getUserByUsername(@PathVariable String username) {
        AuthUser usr = userService.getUserByUsername(username).get();

        Long dId = usr.getId();

//        int cnt = appointmentService.countItemsByUserId(dId) +
//                dogService.countItemsByUserId(dId) +
//                medicalLicenceService.countItemsByUserId(dId) +
//                ownerService.countItemsByUserId(dId) +
//                vetService.countItemsByUserId(dId);

        UserDTO uDto = mapper.toUserDTO(userService.getUserByUsername(username).get());

//        uDto.setCreationCount(cnt + "");

        return Optional.of(uDto);
    }

    @PutMapping("/users/{id}")
    void updateUser(@RequestBody @Valid AuthUser user, @PathVariable Long id) {
        userService.updateUser(user, id);
    }

    @DeleteMapping("/users/{id}")
    void deleteUser(@PathVariable @Valid Long id) {
        userService.deleteUser(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }
}

