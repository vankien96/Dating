package com.example.vankien.dating.Interface;

/**
 * Created by TRẦN ĐỨC LONG on 3/22/2018.
 */

public interface SignUpDelegate {
    void signupSuccess(String email,String password);
    void signFailed();
}
