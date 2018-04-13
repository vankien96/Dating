package com.example.vankien.dating.Interface;

/**
 * Created by TRẦN ĐỨC LONG on 3/27/2018.
 */

public interface ResetPasswordDelegate {
    void resetSuccess(String email);
    void resetFailed();
}
