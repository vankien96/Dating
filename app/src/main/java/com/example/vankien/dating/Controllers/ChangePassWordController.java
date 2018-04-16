package com.example.vankien.dating.Controllers;

import android.app.Activity;
import android.support.annotation.NonNull;

import com.example.vankien.dating.Interface.ChangePassWordDelegate;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

/**
 * Created by Luu Ngoc Lan on 16-Apr-18.
 */

public class ChangePassWordController {
    private static ChangePassWordController sInstance = new ChangePassWordController();
    public ChangePassWordDelegate delegate;

    public  static ChangePassWordController getsInstance(){
        return sInstance;
    }

    public void updatePassword(final String password, Activity activity){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.updatePassword(password)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            if(delegate!=null)
                                delegate.updateSucess(password);
                        } else {
                            if(delegate!=null){
                                delegate.updateFailed();
                            }
                        }
                    }
                });

    }
}
