package com.example.destinationbucketlist.controller;

import com.example.destinationbucketlist.config.JwtTokenUtil;
import com.example.destinationbucketlist.model.AuthCode;
import com.example.destinationbucketlist.model.AuthUser;
import com.example.destinationbucketlist.model.JwtRequest;
import com.example.destinationbucketlist.model.JwtResponse;
import com.example.destinationbucketlist.service.AuthCodeService;
import com.example.destinationbucketlist.service.UserDetailsImpl;
import com.example.destinationbucketlist.service.UserService;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
public class JwtAuthenticationController implements ErrorController {
    protected final Log logger = LogFactory.getLog(getClass());

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private AuthCodeService authCodeService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error-404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error-500";
            }
        }
        return "error";
    }

    @PostMapping("/api/auth/login")
    public ResponseEntity<?> createAuthenticationToken(@Valid @RequestBody JwtRequest authenticationRequest)
            throws Exception {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String jwt = jwtTokenUtil.generateJwtToken(authentication);

        List<String> profiles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                profiles));
    }

    @PostMapping("/api/auth/register")
    void addUser(@RequestBody @Valid AuthUser user) {
        AuthCode ac = new AuthCode();
        ac.setUser(user);
        ac.setStatus("PENDING");
        ac.setExpirationTimestamp(new Date(System.currentTimeMillis() + 10 * 60 * 1000));
        ac.setCode(UUID.randomUUID().toString());

        user.setAuthCode(ac);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        this.userService.addUser(user);

        logger.info("Created user " + user.getUsername() + " with auth code " + ac.getCode());
    }

    @GetMapping("/api/auth/register/confirm/{code}")
    Optional<AuthUser> confirmCode(@PathVariable String code) {
        AuthCode ac = authCodeService.findByCodePending(code);

        if (ac != null) {
            if (ac.getExpirationTimestamp().getTime() < System.currentTimeMillis()) {
                // expired
                return null;
            } else {
                ac.setStatus("CONFIRMED");
                authCodeService.updateAuthCode(ac);

                return null;
            }
        }

        return null;
    }
}

