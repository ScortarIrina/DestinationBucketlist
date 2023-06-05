package com.example.destinationbucketlist.model;

import jakarta.persistence.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = "code"))
public class AuthCode {
    private @Id
    @GeneratedValue Long id;

    private String code;

    private String status;

    @Temporal(TemporalType.TIMESTAMP)
    private Date expirationTimestamp;

    @OneToOne(mappedBy = "authCode")
    private AuthUser user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getExpirationTimestamp() {
        return expirationTimestamp;
    }

    public void setExpirationTimestamp(Date expirationTimestamp) {
        this.expirationTimestamp = expirationTimestamp;
    }

    public AuthUser getUser() {
        return user;
    }

    public void setUser(AuthUser user) {
        this.user = user;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public AuthCode() {
    }

    public AuthCode(Long id, String status, Date expirationTimestamp, AuthUser user) {
        this.id = id;
        this.status = status;
        this.expirationTimestamp = expirationTimestamp;
        this.user = user;
    }

    @Override
    public String toString() {
        return "AuthCode{" +
                "id=" + id +
                ", status='" + status + '\'' +
                ", expirationTimestamp='" + new SimpleDateFormat().format(expirationTimestamp) + '\'' +
                ", user='" + user.getUsername() + '\'' +
                '}';
    }
}

