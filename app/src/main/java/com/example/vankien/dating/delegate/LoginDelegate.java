package com.example.vankien.dating.delegate;

/**
 * Created by TRẦN ĐỨC LONG on 3/22/2018.
 */

public interface LoginDelegate {
    void loginSuccess(String email,String password);
    void loginFailed();
}
