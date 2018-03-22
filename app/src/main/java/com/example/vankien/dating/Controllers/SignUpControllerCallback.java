package com.example.vankien.dating.Controllers;

/**
 * Created by TRẦN ĐỨC LONG on 3/22/2018.
 */

public interface SignUpControllerCallback {
    void signupSuccess(String email,String password);
    void signFailed();
}
