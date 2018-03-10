package com.example.vankien.dating.Models;

import android.widget.Button;

/**
 * Created by TRẦN ĐỨC LONG on 3/2/2018.
 */

public class AroundModel {
    private int imgYes;
    private int imgNo;
    private int imagePeople;

    public AroundModel(int imgYes, int imgNo, int imagePeople) {
        this.imgYes = imgYes;
        this.imgNo = imgNo;
        this.imagePeople = imagePeople;
    }

    public int getImgYes() {
        return imgYes;
    }

    public void setImgYes(int imgYes) {
        this.imgYes = imgYes;
    }

    public int getImgNo() {
        return imgNo;
    }

    public void setImgNo(int imgNo) {
        this.imgNo = imgNo;
    }

    public int getImagePeople() {
        return imagePeople;
    }

    public void setImagePeople(int imagePeople) {
        this.imagePeople = imagePeople;
    }
}

