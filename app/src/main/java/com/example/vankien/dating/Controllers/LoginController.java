package com.example.vankien.dating.Controllers;

import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by vanki on 3/21/2018.
 */

public class LoginController {
    private static LoginController shareInstance = new LoginController();

    public static LoginController getShareInstance(){
        return shareInstance;
    }

    public boolean checkLogin(){
        if (FirebaseAuth.getInstance().getCurrentUser() != null){
            return true;
        }else{
            return false;
        }
    }
}
