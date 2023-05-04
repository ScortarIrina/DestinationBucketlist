package com.example.destinationbucketlist.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@NoArgsConstructor
@AllArgsConstructor
// A vacation destination is a place with a geolocation, title, image, description, stay dates.
abstract public class Destination {
    private @Id @GeneratedValue Integer destinationID;
    private String geolocation;
    private String title;
    private String image; // this is a link or a path
    private String description;
    private Date start;
    private Date end;

    // getters
    public Integer getDestinationID() {
        return destinationID;
    }

    public Date getEnd() {
        return end;
    }

    public Date getStart() {
        return start;
    }

    public String getDescription() {
        return description;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }



    // setters

    public void setDestinationID(Integer destinationID) {
        this.destinationID = destinationID;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public void setTitle(String title) {
        this.title = title;
    }
    @Override
    public String toString() {
        return "Destination{" +
                "id=" + destinationID +
                ", geolocation='" + geolocation + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", description='" + description + '\'' +
                ", start='" + start + '\'' +
                ", end='" + end + '\'' +
                '}';
    }
}

