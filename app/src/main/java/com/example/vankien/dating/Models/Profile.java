package com.example.vankien.dating.Models;

/**
 * Created by Luu Ngoc Lan on 11-Mar-18.
 */

public class Profile {

    private String mName;
    private int mAge;
    private int mSex;
    private String mDescription;
    private String mImage;

    public Profile(String mName, int mAge, int mSex, String mDescription, String mImage) {
        this.mName = mName;
        this.mAge = mAge;
        this.mSex = mSex;
        this.mDescription = mDescription;
        this.mImage = mImage;
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

    @Override
    public String toString() {
        return "Profile{" + "mName='" + mName + '\'' + ", mAge=" + mAge + ", mSex=" + mSex + ", mDescription='" + mDescription + '\'' + ", mImage='" + mImage + '\'' + '}';
    }
}
