package com.example.destinationbucketlist.service;

import com.example.destinationbucketlist.model.AuthCode;
import com.example.destinationbucketlist.repository.AuthCodeRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@NoArgsConstructor
@AllArgsConstructor
@Service
public class AuthCodeService {
    @Autowired
    private AuthCodeRepository authCodeRepository;

    public AuthCode findByCodePending(String code) {
        return authCodeRepository.findByCodePending(code).orElse(null);
    }

    public void updateAuthCode(AuthCode ac) {
        authCodeRepository.save(ac);
    }
}

