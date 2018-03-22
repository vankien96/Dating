package com.example.vankien.dating.Controllers;

import com.example.vankien.dating.Models.PeopleAround;

import java.util.ArrayList;

/**
 * Created by vanki on 3/21/2018.
 */

public interface MapControllerCallback {
    void getAroundPeopleSuccess(ArrayList<PeopleAround> peopleArounds);
}
