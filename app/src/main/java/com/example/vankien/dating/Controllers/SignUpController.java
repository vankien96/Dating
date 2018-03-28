package com.example.vankien.dating.Controllers;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by TRẦN ĐỨC LONG on 3/22/2018.
 */

public class SignUpController {
    private static SignUpController shareInstance = new SignUpController();
    public SignUpDelegate delegate;
    public static SignUpController getShareInstance(){
        return shareInstance;
    }


    public void signUp(final String email, final String password, Activity activity) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            delegate.signupSuccess(email,password);
                        }
                        else {
                           delegate.signFailed();
                        }
                    }
                });
    }
}
