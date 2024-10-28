package com.example.Yieldstreeet.YieldstreetTask.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String userId;

    @OneToMany
    private List<Accreditation> accreditations = new ArrayList<>();

    public User() {

    }

    public User(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<Accreditation> getAccreditations() {
        return accreditations;
    }

    public void setAccreditations(List<Accreditation> accreditations) {
        this.accreditations = accreditations;
    }
}
