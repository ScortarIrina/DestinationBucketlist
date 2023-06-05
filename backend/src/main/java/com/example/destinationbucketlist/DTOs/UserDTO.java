package com.example.destinationbucketlist.DTOs;

public class UserDTO {
    private Long id;
    private String username;
    private String password;
    private String profile;
    private String firstName;
    private String lastName;
    private String location;
    private String birthday;
    private String gender;
    private String maritalStatus;
    private String status;
    private Long authCode_id;

    private String creationCount;

    public UserDTO(Long id, String username, String password, String profile, String firstName, String lastName, String location, String birthday, String gender, String maritalStatus, String status, Long authCode_id) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.profile = profile;
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.birthday = birthday;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.status = status;
        this.authCode_id = authCode_id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Long getAuthCode_id() {
        return authCode_id;
    }

    public void setAuthCode_id(Long authCode_id) {
        this.authCode_id = authCode_id;
    }

    public String getCreationCount() {
        return creationCount;
    }

    public void setCreationCount(String creationCount) {
        this.creationCount = creationCount;
    }
}

