package com.example.vankien.dating.Views.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.vankien.dating.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        CheckThread checkThread = new CheckThread();
        checkThread.start();
    }

    public class CheckThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                sleep(2000);

                //Check login or not here

                Intent itent = new Intent(SplashActivity.this,MainActivity.class);
                startActivity(itent);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
