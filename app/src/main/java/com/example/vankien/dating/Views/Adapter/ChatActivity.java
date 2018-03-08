package com.example.vankien.dating.Views.Adapter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.vankien.dating.R;

public class ChatActivity extends AppCompatActivity {
    TextView txtName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Intent intent = getIntent();
        String name = intent.getStringExtra("Name");

        txtName = findViewById(R.id.txtName);
        txtName.setText(name);
    }
}
