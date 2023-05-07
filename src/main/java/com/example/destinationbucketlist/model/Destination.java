package com.example.destinationbucketlist.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.util.Date;

@MappedSuperclass
// A vacation destination is a place with a geolocation, title, image, description, stay dates.
abstract public class Destination {

    private @Id @GeneratedValue Integer destinationID;
    private String geolocation;
    private String title;
    private String image; // this is a link or a path
    private String description;
    private Date startDate;
    private Date endDate;

    public Destination() {
    }

    public Destination(Integer destinationID, String geolocation, String title, String image, String description, Date startDate, Date endDate) {
        this.destinationID = destinationID;
        this.geolocation = geolocation;
        this.title = title;
        this.image = image;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getDestinationID() {
        return destinationID;
    }

    public void setDestinationID(Integer destinationID) {
        this.destinationID = destinationID;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}

