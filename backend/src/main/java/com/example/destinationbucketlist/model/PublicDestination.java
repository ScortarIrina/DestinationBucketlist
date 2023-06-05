//package com.example.destinationbucketlist.model;
//
//import jakarta.persistence.Entity;
//
//import java.util.Date;
//
//@Entity
//public class PublicDestination extends Destination{
//
//    public PublicDestination(Integer destinationID, String geolocation, String title, String image, String description, Date startDate, Date endDate) {
//        super(destinationID, geolocation, title, image, description, startDate, endDate);
//    }
//
//    public PublicDestination() {}
//
//    public PrivateDestination makePrivate(AppUser user)
//    {
//        PrivateDestination destination = new PrivateDestination();
//        destination.setId(this.id);
//        destination.setDescription(this.description);
//        destination.setGeolocation(this.geolocation);
//        destination.setImage(this.image);
//        destination.setTitle(this.title);
//        destination.setStartDate(this.startDate);
//        destination.setEndDate(this.endDate);
//        destination.setAppUser(user);
//        return destination;
//    }
//
//    public PrivateDestination markFavourite(AppUser user)
//    {
//        PrivateDestination destination = this.makePrivate(user);
//        destination.makeFavourite();
//        return destination;
//    }
//
//
//}
