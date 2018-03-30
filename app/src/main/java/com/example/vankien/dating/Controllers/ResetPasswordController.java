package com.example.vankien.dating.Controllers;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by TRẦN ĐỨC LONG on 3/27/2018.
 */

public class ResetPasswordController {
    private static ResetPasswordController shareInstance = new ResetPasswordController();
    public ResetPasswordDelegate delegate;
    public static ResetPasswordController getShareInstance(){
        return shareInstance;
    }

    public void resetPassword(final String email, Activity activity) {
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if (delegate != null){
                                delegate.resetSuccess(email);
                            }
                        } else {
                            if (delegate != null){
                                delegate.resetFailed();
                            }
                        }

                    }
                });
    }
}

