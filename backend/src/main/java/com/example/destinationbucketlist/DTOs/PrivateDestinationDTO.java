package com.example.destinationbucketlist.DTOs;

public class PrivateDestinationDTO {

    private Integer id;
    private String title;
    private String geolocation;
    private String image;
    private String startDate;
    private String endDate;

    public PrivateDestinationDTO(Integer id, String title, String geolocation, String image, String startDate, String endDate) {
        this.id = id;
        this.title = title;
        this.geolocation = geolocation;
        this.image = image;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGeolocation() {
        return geolocation;
    }

    public void setGeolocation(String geolocation) {
        this.geolocation = geolocation;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
