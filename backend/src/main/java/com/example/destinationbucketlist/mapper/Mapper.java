package com.example.destinationbucketlist.mapper;

import com.example.destinationbucketlist.DTOs.DestinationDTO;
import com.example.destinationbucketlist.DTOs.UserDTO;
import com.example.destinationbucketlist.model.AuthUser;
import com.example.destinationbucketlist.model.Destination;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;

@Component
public class Mapper {

//    public UserDTO toUserDTO(AuthUser authUser) {
//        List<String> destinationNames = authUser.getDestinations()
//                .stream()
//                .map(Destination::getTitle)
//                .collect(toList());
//
//        return new UserDTO(authUser.getId(),
//                authUser.getFirstName(),
//                authUser.getLastName(),
//                authUser.getEmail(),
//                destinationNames);
//    }

    public DestinationDTO toDestinationDTO(Destination destination) {
        return new DestinationDTO(destination.getId(),
                destination.getTitle(),
                destination.getGeolocation(),
                destination.getImage(),
                destination.getDescription(),
                destination.getStartDate().toString(),
                destination.getEndDate().toString(),
                destination.isPublished());
    }

    public UserDTO toUserDTO(AuthUser user) {
        return new UserDTO(user.getId(), user.getUsername(), user.getPassword(), user.getProfile(), user.getFirstName(),
                user.getLastName(), user.getLocation(), new SimpleDateFormat("dd/MM/YYYY").format(user.getBirthday()), user.getGender(),
                user.getMaritalStatus(), user.getStatus(), user.getAuthCode().getId());
    }
}
