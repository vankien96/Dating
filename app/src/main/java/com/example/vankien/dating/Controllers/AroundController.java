package com.example.vankien.dating.Controllers;

import com.example.vankien.dating.R;
import com.example.vankien.dating.Models.AroundModel;

import java.util.ArrayList;

/**
 * Created by TRẦN ĐỨC LONG on 3/10/2018.
 */

public class AroundController {
    private static AroundController sInstance = null;
    private ArrayList<AroundModel> mAroundModel;

    private AroundController() {
        this.mAroundModel = new ArrayList<>();
    }

    public static AroundController getsInstance() {
        if(sInstance== null) {
            sInstance = new AroundController();
        }
        return  sInstance;
    }

    public ArrayList<AroundModel> getmAroundModel() {
        if (mAroundModel.isEmpty()) {
            load();
        }
        return mAroundModel;
    }

    public void setmAroundModel(ArrayList<AroundModel> mAroundModel) {
        this.mAroundModel = mAroundModel;
    }

    private void load() {
        mAroundModel.clear();
        mAroundModel.add(new AroundModel(R.drawable.yes, R.drawable.no,R.drawable.testimage1));
        mAroundModel.add(new AroundModel(R.drawable.yes, R.drawable.no,R.drawable.testimage2));
        mAroundModel.add(new AroundModel(R.drawable.yes, R.drawable.no,R.drawable.testimage3));
        mAroundModel.add(new AroundModel(R.drawable.yes, R.drawable.no,R.drawable.testimage4));
        mAroundModel.add(new AroundModel(R.drawable.yes, R.drawable.no,R.drawable.testimage5));
    }
}
