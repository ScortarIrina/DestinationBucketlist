package com.example.destinationbucketlist.controller;

import com.example.destinationbucketlist.DTOs.DestinationDTO;
import com.example.destinationbucketlist.mapper.Mapper;
import com.example.destinationbucketlist.model.Destination;
import com.example.destinationbucketlist.service.DestinationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
public class DestinationController {

    public static int PAGE_SIZE = 100;

    @Autowired
    private DestinationService destinationService;

    @Autowired
    private Mapper mapper;

    // localhost:8080/destinations
    @GetMapping("/destinations")
    ResponseEntity<Map<String, Object>> getAllDestinations(HttpServletRequest request,
                                                           @RequestParam Optional<String> page,
                                                           @RequestParam Optional<String> size) {
        Principal principal = request.getUserPrincipal();
        String username =  principal != null ? principal.getName() : null;

        int p = Integer.parseInt(page.orElse("0"));

        int pSize = Integer.parseInt(size.orElse("0"));

        // call getAll... method from service
        Map<String, Object> ret = destinationService.getDestinations(p, pSize, username);

        Map<Integer, String> dIdToUsername = destinationService.findUsernamesForDestinations(
                ((List<Destination>) ret.get("destinations")).stream().map(d -> d.getId()).collect(Collectors.toList()).stream().toList()
        ).stream().collect(Collectors.toMap(
                o -> (Integer) o[0], o -> (String) o[1]
        ));

        // map resulting Appointment entities to AppointmentDTO using the instance of the Mapper class
        Collection<DestinationDTO> dtos = ((Collection<Destination>) ret.get("destinations"))
                .stream()
                .map(d -> {
                    DestinationDTO retD = mapper.toDestinationDTO(d);
                    retD.setUsername(dIdToUsername.get(d.getId()));
                    return retD;
                })
                .collect(Collectors.toList());

        ret.put("destinations", dtos);

        // return a ResponseEntity object with a JSON response body containing a map of the results
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    @GetMapping("/publicDestinations")
    ResponseEntity<Map<String, Object>> getAllPublicDestinations(@RequestParam Optional<String> page,
                                                           @RequestParam Optional<String> size) {
        int p = Integer.parseInt(page.orElse("0"));

        int pSize = Integer.parseInt(size.orElse("0"));

        // call getAll... method from service
        Map<String, Object> ret = destinationService.getDestinations(p, pSize, null);

        Map<Integer, String> dIdToUsername = destinationService.findUsernamesForDestinations(
                ((List<Destination>) ret.get("destinations")).stream().map(d -> d.getId()).collect(Collectors.toList()).stream().toList()
        ).stream().collect(Collectors.toMap(
                o -> (Integer) o[0], o -> (String) o[1]
        ));

        // map resulting Appointment entities to AppointmentDTO using the instance of the Mapper class
        Collection<DestinationDTO> dtos = ((Collection<Destination>) ret.get("destinations"))
                .stream()
                .map(d -> {
                    DestinationDTO retD = mapper.toDestinationDTO(d);
                    retD.setUsername(dIdToUsername.get(d.getId()));
                    return retD;
                })
                .collect(Collectors.toList());

        ret.put("destinations", dtos);

        // return a ResponseEntity object with a JSON response body containing a map of the results
        return new ResponseEntity<>(ret, HttpStatus.OK);
    }

    // localhost:8080/destinations/{id}
    @GetMapping("/destinations/{destinationId}")
    Optional<DestinationDTO> getDestinationById(@PathVariable Integer destinationId) {
        return destinationService.getDestinationById(destinationId)
                .stream()
                .map(mapper::toDestinationDTO)
                .findFirst();
    }

    // localhost:8080/destinations
    // the JSON body
    // {
    //    "geolocation": "geo2",
    //    "title": "Paris",
    //    "image": "image Paris",
    //    "description": "rainy",
    //    "startDate": "2023-06-19",
    //    "endDate": "2023-06-24"
    //}
    @PostMapping("/destinations")
    void addDestination(HttpServletRequest request,
                        HttpServletResponse response,
                        @RequestBody Destination destination) throws IOException {
        Principal principal = request.getUserPrincipal();
        String username =  principal != null ? principal.getName() : null;

        if (username == null) {
            // not allowed
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
            return;
        }

        this.destinationService.addDestination(destination, username);
    }

    // localhost:8080/destinations/{id}
    // {
    //    "geolocation": "geo2",
    //    "title": "Paris",
    //    "image": "image Paris",
    //    "description": "rainy",
    //    "startDate": "2023-06-19",
    //    "endDate": "2023-06-24"
    //}
    @PutMapping("/destinations/{destinationId}")
    void updateDestination(@RequestBody Destination destination, @PathVariable Integer destinationId) {
        Destination origD = destinationService.getDestinationById(destinationId).orElse(null);

        if (origD != null) {
            destination.setAuthUser(origD.getAuthUser());
            destination.setPublished(!origD.isPublished());
        }

        destination.setId(destinationId);
        destinationService.updateDestination(destination);
    }

    // localhost:8080/destinations/{id}
    @DeleteMapping("/destinations/{destinationId}")
    void deleteDestination(@PathVariable Integer destinationId) {
        destinationService.deleteDestination(destinationId);
    }

    @GetMapping("/destinations/destinations-with-given-title/{title}")
    List<DestinationDTO> getDestinationsWithTitle(@PathVariable String title) {
        return destinationService.getDestinationsByTitle(title).stream().map(mapper::toDestinationDTO).collect(Collectors.toList());
    }
}
