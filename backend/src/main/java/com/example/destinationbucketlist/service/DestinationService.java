package com.example.destinationbucketlist.service;

import com.example.destinationbucketlist.controller.DestinationController;
import com.example.destinationbucketlist.model.AuthUser;
import com.example.destinationbucketlist.model.Destination;
import com.example.destinationbucketlist.repository.DestinationRepository;
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
public class DestinationService {

    @Autowired
    private DestinationRepository destinationRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<String, Object> getDestinations(int page, int pSize, String username) {

        // create an empty ArrayList of PrivateDestinations objects and a new HashMap object.
        List<Destination> destinations = new ArrayList<>();
        Map<String, Object> response = new HashMap<>();

        if (page > -1) {
            // create a Pageable object using page and pSize as parameters
            // Pageable object is then used to retrieve a page of destinations from the DestinationRepository using the findAll() method
            Pageable p = PageRequest.of(page, pSize > 0 ? pSize : DestinationController.PAGE_SIZE, Sort.by("id"));

//            if (username == null) {
//                Page<Destination> pageDestinations = destinationRepository.findPublicDestinations(p);
//
//                response.put("destinations", pageDestinations.getContent());
//                response.put("currentPage", pageDestinations.getNumber());
//                response.put("totalItems", pageDestinations.getTotalElements());
//                response.put("totalPages", pageDestinations.getTotalPages());
//            } else {
//                Page<Destination> pageDestinations = destinationRepository.findPublicDestinations(p);
                Page<Destination> pageDestinations = destinationRepository.findDestinationsByUsername(p, username);
//
//                Set<Destination> s = new HashSet<>();
//
//                s.addAll(pageDestinations1.getContent());
//                s.addAll(pageDestinations2.getContent());

                response.put("destinations", pageDestinations.getContent());
                response.put("currentPage", pageDestinations.getNumber());
                response.put("totalItems", pageDestinations.getTotalElements());
                response.put("totalPages", pageDestinations.getTotalPages());
//            }

            // put the retrieved privateDestinations, current page number, total number of items,
            // and total number of pages in the response map


            // return the response
            return response;
        } else {
            // retrieve all privateDestinations from the appointmentRepository using the findAll() method and adds them to the privateDestinations list
            destinationRepository.findAll().forEach(destinations::add);

            // put the retrieved privateDestinations, current page number, total number of items,
            // and total number of pages in the response map
            response.put("destinations", destinations);
            response.put("currentPage", 0);
            response.put("totalItems", destinations.size());
            response.put("totalPages", 1);
        }

        return response;
    }

    public Optional<Destination> getDestinationById(Integer id) {
        return destinationRepository.findById(id);
    }

    public void addDestination(Destination destination, String username) {
        AuthUser user = userRepository.findByUsernameConfirmed(username).orElse(null);
        destination.setAuthUser(user);
        destination.setPublished(false);
        destinationRepository.save(destination);
    }

    public void updateDestination(Destination destination) {
        destinationRepository.save(destination);
    }

    public void deleteDestination(Integer id) {
        destinationRepository.deleteById(id);
    }

    public List<Destination> getDestinationsByTitle(String title) {
        List<Destination> filteredDestinations = new ArrayList<>();
        for (Destination d : destinationRepository.findAll()) {
            if (Objects.equals(d.getTitle(), title)) {
                filteredDestinations.add(d);
            }
        }
        return filteredDestinations;
    }

    public List<Object[]> findUsernamesForDestinations(List<Integer> dIds) {
        return destinationRepository.findUsernamesForDestinations(dIds);
    }
}
