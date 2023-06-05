//package com.example.destinationbucketlist.model;
//
//import com.fasterxml.jackson.annotation.JsonInclude;
//import jakarta.persistence.Entity;
//import jakarta.persistence.JoinColumn;
//import jakarta.persistence.ManyToOne;
//
//@Entity
//public class PrivateDestination extends Destination {
//
//    @JsonInclude(JsonInclude.Include.NON_NULL)
//    @ManyToOne(optional = true)
//    @JoinColumn(name = "appUserId")
//    private AppUser appUser;
//
//    private boolean isFavourite; // this marks if the destination is considered favourite or not
//    // to make a destination favourite see below makeFavourite()
//
//    public PrivateDestination() {
//        isFavourite = false;
//    }
//
//    public PrivateDestination(boolean fav) {
//        isFavourite = fav;
//    }
//
//    public boolean isFavourite() {
//        return isFavourite;
//    }
//
//    public void setFavourite(boolean favourite) {
//        isFavourite = favourite;
//    }
//
//    public void makeFavourite() {
//        this.isFavourite = true;
//    }
//
//    public AppUser getAppUser() {
//        return appUser;
//    }
//
//    public void setAppUser(AppUser appUser) {
//        this.appUser = appUser;
//    }
//
//    @Override
//    public String toString() {
//        return "PrivateDestination{" +
//                "appUser=" + appUser +
//                ", isFavourite=" + isFavourite +
//                ", destinationID=" + id +
//                ", geolocation='" + geolocation + '\'' +
//                ", title='" + title + '\'' +
//                ", image='" + image + '\'' +
//                ", description='" + description + '\'' +
//                ", startDate=" + startDate +
//                ", endDate=" + endDate +
//                '}';
//    }
//}
