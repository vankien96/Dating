package com.example.vankien.dating.Views.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vankien.dating.Models.PeopleAround;
import com.example.vankien.dating.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    ImageView imgDetail;
    TextView txtName, txtAdress, txtInformation, txtFriend, txtAverage;
    PeopleAround peopleAround;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        addControls();
        peopleAround = new PeopleAround();
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        peopleAround = (PeopleAround) b.getSerializable("Detail");
        setData();
    }

    private void setData() {
        Uri uri = Uri.parse(peopleAround.getAvatarUrl());
        Picasso.with(getApplicationContext()).load(uri).into(imgDetail);
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
