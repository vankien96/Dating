package com.example.vankien.dating.Views.Activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vankien.dating.Controllers.LoginController;
import com.example.vankien.dating.R;
import com.facebook.share.Share;

public class SplashActivity extends AppCompatActivity {

    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        if (checkLocationPermission()) {
            CheckThread checkThread = new CheckThread();
            checkThread.start();
        }
        checkSharePreference();
    }

    private void checkSharePreference() {
        SharedPreferences sharedPrefs = getSharedPreferences("MySetting", MODE_PRIVATE);
        SharedPreferences.Editor editor;
        if(!sharedPrefs.contains("initialized")){
            editor = sharedPrefs.edit();
            editor.putBoolean("isPrivacy",true);
            editor.putBoolean("isLookingMen",true);
            editor.putBoolean("isLookingWomen",true);
            editor.putInt("distance",1);
            editor.putInt("age_from",0);
            editor.putInt("age_to",100);
            editor.commit();
        }
    }

    public class CheckThread extends Thread {
        @Override
        public void run() {
            super.run();
            try {
                // NOTE: Sleep 2s to take slow down checkLogin for show Splash
                sleep(2000);
                if (LoginController.getShareInstance().checkLogin()) {
                    Intent itent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(itent);
                } else {
                    Intent itent = new Intent(SplashActivity.this, LogInActivity.class);
                    startActivity(itent);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean checkLocationPermission() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                new AlertDialog.Builder(this)
                        .setTitle("Access your Location")
                        .setMessage("Dating want to know your location. Are you agree?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(SplashActivity.this,
                                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
        CheckThread checkThread = new CheckThread();
        checkThread.start();
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.ACCESS_FINE_LOCATION)
                            == PackageManager.PERMISSION_GRANTED) {

                    }
                } else {

                }
                return;
            }

        }
    }
}
