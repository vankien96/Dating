package com.example.vankien.dating.Models;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by vankien on 3/1/18.
 */

public class PeopleAround {
    private String name;
    private int age;
    private int gender;
    private String avatarUrl;
    private String address;
    private LatLng addressLatLng;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public LatLng getAddressLatLng() {
        return addressLatLng;
    }

    public void setAddressLatLng(LatLng addressLatLng) {
        this.addressLatLng = addressLatLng;
    }

    public PeopleAround(String name, int age, int gender, String avatarUrl, String address, LatLng addressLatLng) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.avatarUrl = avatarUrl;
        this.address = address;
        this.addressLatLng = addressLatLng;
    }
}
