package com.example.destinationbucketlist.service;

import com.example.destinationbucketlist.model.AppUser;
import com.example.destinationbucketlist.model.PrivateDestination;
import com.example.destinationbucketlist.repository.PrivateDestinationRepository;
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
public class PrivateDestinationService {

    @Autowired
    private PrivateDestinationRepository privateDestinationRepository;

    public List<PrivateDestination> getAllPrivateDestinations() {
        List<PrivateDestination> privateDestinations = new ArrayList<>();
        privateDestinationRepository.findAll().forEach(privateDestinations::add);
        return privateDestinations;
    }

    public Optional<PrivateDestination> getPrivateDestinationById(Integer privateDestinationId) {
        return privateDestinationRepository.findById(privateDestinationId);
    }

    public void addPrivateDestination(PrivateDestination privateDestination) {
        privateDestinationRepository.save(privateDestination);
    }

    public void updatePrivateDestination(PrivateDestination privateDestination) {
        privateDestinationRepository.save(privateDestination);
    }

    public void deletePrivateDestination(Integer privateDestinationId) {
        PrivateDestination appUser = getPrivateDestinationById(privateDestinationId).orElse(null);
        privateDestinationRepository.delete(appUser);
    }
}
