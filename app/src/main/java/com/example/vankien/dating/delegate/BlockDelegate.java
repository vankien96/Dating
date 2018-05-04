package com.example.vankien.dating.delegate;

import com.example.vankien.dating.models.PeopleAround;

import java.util.ArrayList;

/**
 * Created by vanki on 4/21/2018.
 */

public interface BlockDelegate {
    void getRejectListSuccess(ArrayList<String> rejectedPeople);
    void checkBlock(String block);
}
