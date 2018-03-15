package com.example.vankien.dating.Controllers;

import com.example.vankien.dating.Models.Profile;

/**
 * Created by Luu Ngoc Lan on 11-Mar-18.
 */

public class ProfileController {
    private static ProfileController sInstance = new ProfileController();

    public static ProfileController getsInstance (){ return sInstance;}

    public Profile getProfile(){
        Profile profile = new Profile("KHANH LINH",17,0,"Love pink color, I'm a real fan of SON TUNG MTP!","testImage4.PNG");
        return profile;
    }


}
