package com.example.destinationbucketlist.service;

import com.example.destinationbucketlist.model.PublicDestination;
import com.example.destinationbucketlist.repository.PublicDestinationsRepository;
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
public class PublicDestinationService {
    @Autowired
    private PublicDestinationsRepository publicDestinationsRepository;

    public List<PublicDestination> getAllPublicDestinations() {
        List<PublicDestination> destinations = new ArrayList<>();
        publicDestinationsRepository.findAll();
        return destinations;
    }

    public Optional<PublicDestination> getPublicDestinationById(Integer publicDestinationId) {
        return publicDestinationsRepository.findById(publicDestinationId);
    }

    public void addPublicDestination(PublicDestination publicDestination) {
        publicDestinationsRepository.save(publicDestination);
    }

    public void updatePrivateDestination(PublicDestination publicDestination) {
        publicDestinationsRepository.save(publicDestination);
    }

    public void deletePublicDestination(Integer publicDestinationId) {
        getPublicDestinationById(publicDestinationId).ifPresent(appUser -> publicDestinationsRepository.delete(appUser));
    }
}
