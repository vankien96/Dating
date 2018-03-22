package com.example.vankien.dating.Views.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vankien.dating.R;

public class DetailActivity extends AppCompatActivity {
    ImageView imgDetail;
    TextView txtName, txtAdress, txtInformation, txtFriend, txtAverage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        addControls();

    }

    private void addControls() {
        imgDetail = findViewById(R.id.imgDetail);
        txtName = findViewById(R.id.txtName);
        txtAdress = findViewById(R.id.txtAdress);
        txtInformation = findViewById(R.id.txtInfomation);
        txtFriend = findViewById(R.id.txtFriend);
        txtAverage = findViewById(R.id.txtAverage);
    }

}
