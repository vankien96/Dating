package com.example.vankien.dating.Controllers;

/**
 * Created by TRẦN ĐỨC LONG on 3/27/2018.
 */

public interface ResetPasswordDelegate {
    void resetSuccess(String email);
    void resetFailed();
}
