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
    }

    public class CheckThread extends Thread{
        @Override
        public void run() {
            super.run();
            try {
                sleep(1000);
                Intent itent = new Intent(SplashActivity.this,DangNhapActivity.class);
                startActivity(itent);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
