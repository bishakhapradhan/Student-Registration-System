package com.example.bit6.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class StudentData
        implements Serializable {
    private Integer id;

    private String firstName;

    private String lastName;

    private String gender;

    private String country;

    private String phone;

    public StudentData(String firstName, String lastName, String gender, String country, String phone) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.country = country;
        this.phone = phone;
    }

    public StudentData(Integer id, String firstName, String lastName, String gender, String country, String phone) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.country = country;
        this.phone = phone;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }


    public String getGender() {
        return gender;
    }


    public String getCountry() {
        return country;
    }

    public String getPhone() {
        return phone;
    }
}
