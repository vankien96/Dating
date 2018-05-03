package com.example.vankien.dating.delegate;

/**
 * Created by TRẦN ĐỨC LONG on 3/27/2018.
 */

public interface ResetPasswordDelegate {
    void resetSuccess(String email);
    void resetFailed();
}
