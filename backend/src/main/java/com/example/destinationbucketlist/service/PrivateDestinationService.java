package com.example.destinationbucketlist.service;

import com.example.destinationbucketlist.controller.PrivateDestinationController;
import com.example.destinationbucketlist.model.AppUser;
import com.example.destinationbucketlist.model.PrivateDestination;
import com.example.destinationbucketlist.repository.PrivateDestinationRepository;
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
public class PrivateDestinationService {

    @Autowired
    private PrivateDestinationRepository privateDestinationRepository;

    public Map<String, Object> getAllPrivateDestinations(int page, int pSize) {

        // create an empty ArrayList of PrivateDestinations objects and a new HashMap object.
        List<PrivateDestination> privateDestinations = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        if (page > -1) {
            // create a Pageable object using page and pSize as parameters
            // Pageable object is then used to retrieve a page of destinations from the PrivateDestinationRepository using the findAll() method
            Pageable p = PageRequest.of(page, pSize > 0 ? pSize : PrivateDestinationController.PAGE_SIZE, Sort.by("id"));

            Page<PrivateDestination> pagePrivateDestinations = privateDestinationRepository.findAll(p);

            // put the retrieved privateDestinations, current page number, total number of items,
            // and total number of pages in the response map
            response.put("privateDestinations", pagePrivateDestinations.getContent());
            response.put("currentPage", pagePrivateDestinations.getNumber());
            response.put("totalItems", pagePrivateDestinations.getTotalElements());
            response.put("totalPages", pagePrivateDestinations.getTotalPages());

            // return the response
            return response;
        } else {
            // retrieve all privateDestinations from the appointmentRepository using the findAll() method and adds them to the privateDestinations list
            privateDestinationRepository.findAll().forEach(privateDestinations::add);

            // put the retrieved privateDestinations, current page number, total number of items,
            // and total number of pages in the response map
            response.put("privateDestinations", privateDestinations);
            response.put("currentPage", 0);
            response.put("totalItems", privateDestinations.size());
            response.put("totalPages", 1);
        }

        return response;
    }

    public Optional<PrivateDestination> getPrivateDestinationById(Integer id) {
        return privateDestinationRepository.findById(id);
    }

    public void addPrivateDestination(PrivateDestination privateDestination) {
        privateDestinationRepository.save(privateDestination);
    }

    public void updatePrivateDestination(PrivateDestination privateDestination) {
        privateDestinationRepository.save(privateDestination);
    }

    public void deletePrivateDestination(Integer id) {
        privateDestinationRepository.deleteById(id);
    }
}
