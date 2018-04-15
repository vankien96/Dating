package com.example.vankien.dating.Models;

import java.io.Serializable;

/**
 * Created by Luu Ngoc Lan on 11-Mar-18.
 */

public class Profile implements Serializable {

    private String mName;
    private int mAge;
    private int mSex;
    private String mDescription;
    private String mImage;
    private String mLatitude;
    private String mLongitude;
    private int mNumOfFriends;
    private String mRegion;
    private String mAddress;

    public Profile() {
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public int getmAge() {
        return mAge;
    }

    public void setmAge(int mAge) {
        this.mAge = mAge;
    }

    public int getmSex() {
        return mSex;
    }

    public void setmSex(int mSex) {
        this.mSex = mSex;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmLatitude() {
        return mLatitude;
    }

    public void setmLatitude(String mLatitude) {
        this.mLatitude = mLatitude;
    }

    public String getmLongitude() {
        return mLongitude;
    }

    public void setmLongitude(String mLongitude) {
        this.mLongitude = mLongitude;
    }

    public int getmNumOfFriends() {
        return mNumOfFriends;
    }

    public void setmNumOfFriends(int mNumOfFriends) {
        this.mNumOfFriends = mNumOfFriends;
    }

    public String getmRegion() {
        return mRegion;
    }

    public void setmRegion(String mRegion) {
        this.mRegion = mRegion;
    }

    public String getmAddress() {
        return mAddress;
    }

    public void setmAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public Profile(String mName, int age, int sex, String mDescription, String mImage, String mLatitude, String mLongitude, int mNumOfFriends, String mRegion, String mAddress) {
        this.mName = mName;
        this.mAge = age;
        this.mSex = sex;
        this.mDescription = mDescription;
        this.mImage = mImage;
        this.mLatitude = mLatitude;
        this.mLongitude = mLongitude;
        this.mNumOfFriends = mNumOfFriends;
        this.mRegion = mRegion;
        this.mAddress = mAddress;
    }
}
