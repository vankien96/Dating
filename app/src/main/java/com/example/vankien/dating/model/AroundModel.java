package com.example.vankien.dating.model;

import android.widget.Button;

/**
 * Created by TRẦN ĐỨC LONG on 3/2/2018.
 */

public class AroundModel {
    private Button btnDel;
    private Button btnAccept;
    private int Image;


    public AroundModel(Button btnDel, Button btnAccept, int image) {
        this.btnDel = btnDel;
        this.btnAccept = btnAccept;
        Image = image;
    }

    public Button getBtnDel() {
        return btnDel;
    }

    public void setBtnDel(Button btnDel) {
        this.btnDel = btnDel;
    }

    public Button getBtnAccept() {
        return btnAccept;
    }

    public void setBtnAccept(Button btnAccept) {
        this.btnAccept = btnAccept;
    }

    public int getImage() {
        return Image;
    }

    public void setImage(int image) {
        Image = image;
    }
}
