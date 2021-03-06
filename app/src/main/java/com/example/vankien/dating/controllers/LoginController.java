package com.example.vankien.dating.controllers;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.vankien.dating.delegate.LoginDelegate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.UserInfo;

/**
 * Created by vanki on 3/21/2018.
 */

public class LoginController {
    private static LoginController shareInstance = new LoginController();
    public LoginDelegate delegate;
    public static LoginController getShareInstance(){
        return shareInstance;
    }

    public boolean checkLogin() {
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            return true;
        } else {
            return false;
        }
    }

    public void logIn(final String email, final String password, Activity activity) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            if (delegate != null){
                                delegate.loginSuccess(email,password);
                            }
                        }
                        else {
                            if (delegate != null){
                                delegate.loginFailed();
                            }
                        }
                    }
                });
    }
    public boolean isFacebookLogin() {
        boolean isSigninWithFacebook = false;
        for (UserInfo user: FirebaseAuth.getInstance().getCurrentUser().getProviderData()) {
            if (user.getProviderId().equals("facebook.com")) {
                isSigninWithFacebook = true;
            }
        }
        return isSigninWithFacebook;
    }

}
