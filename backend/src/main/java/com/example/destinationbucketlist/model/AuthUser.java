package com.example.destinationbucketlist.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "AuthUser", uniqueConstraints = @UniqueConstraint(columnNames = "username"))
public class AuthUser {
    private @Id
    @GeneratedValue Long id;
    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 120)
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    private EProfile profile;

    @NotBlank
    @Size(max = 30)
    private String firstName;

    @NotBlank
    @Size(max = 30)
    private String lastName;
    private String location;
    @Temporal(TemporalType.DATE)
    private Date birthday;
    @Size(max = 6)
    private String gender;
    private String maritalStatus;
    private String status;

    protected String email;

    @OneToOne(optional = false, cascade = {CascadeType.ALL})
    @JoinColumn(name = "authcode_id", referencedColumnName = "id")
    private AuthCode authCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "authUser")
    @JsonIgnoreProperties("authUser")
    List<Destination> destinations;

    public AuthUser() {
    }

    public AuthUser(Long id, String username, String password, String profile, String firstName, String lastName,
                    String location, Date birthday, String gender, String maritalStatus, String status, AuthCode authCode) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.setProfile(profile);
        this.firstName = firstName;
        this.lastName = lastName;
        this.location = location;
        this.birthday = birthday;
        this.gender = gender;
        this.maritalStatus = maritalStatus;
        this.status = status;
        this.authCode = authCode;
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
        return profile.name();
    }

    public void setProfile(String profile) {
        this.profile = EProfile.valueOf(profile);
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
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

    public AuthCode getAuthCode() {
        return authCode;
    }

    public void setAuthCode(AuthCode authCode) {
        this.authCode = authCode;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Destination> getDestinations() {
        return destinations;
    }

    public void setDestinations(List<Destination> destinations) {
        this.destinations = destinations;
    }


    @Override
    public String toString() {
        return "User{" +
                "id=" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", profile='" + profile + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", location=" + location + '\'' +
                ", birthDay=" + new SimpleDateFormat().format(birthday) + '\'' +
                ", gender='" + gender + '\'' +
                ", maritalStatus='" + maritalStatus + '\'' +
                ", status='" + status + '\'' +
                ", authCode='" + authCode.toString() + '\'' +
                '}';
    }
}
