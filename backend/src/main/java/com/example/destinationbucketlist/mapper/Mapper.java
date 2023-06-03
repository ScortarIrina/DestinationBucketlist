package com.example.destinationbucketlist.mapper;

import com.example.destinationbucketlist.DTOs.AppUserDTO;
import com.example.destinationbucketlist.DTOs.PrivateDestinationDTO;
import com.example.destinationbucketlist.model.AppUser;
import com.example.destinationbucketlist.model.PrivateDestination;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class Mapper {

    public AppUserDTO toAppUserDTO(AppUser appUser) {
        List<String> destinationNames = appUser.getPrivateDestinations()
                .stream()
                .map(PrivateDestination::getTitle)
                .collect(toList());

        return new AppUserDTO(appUser.getId(),
                appUser.getFirstName(),
                appUser.getLastName(),
                appUser.getEmail(),
                destinationNames);
    }

    public PrivateDestinationDTO toPrivateDestinationDTO(PrivateDestination privateDestination) {
        return new PrivateDestinationDTO(privateDestination.getDestinationID(),
                privateDestination.getTitle(),
                privateDestination.getGeolocation(),
                privateDestination.getImage(),
                privateDestination.getStartDate().toString(),
                privateDestination.getEndDate().toString());
    }
}
