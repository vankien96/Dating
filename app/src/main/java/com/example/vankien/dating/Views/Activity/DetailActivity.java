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
        fakeData();

    }

    private void addControls() {
        imgDetail = findViewById(R.id.imgDetail);
        txtName = findViewById(R.id.txtName);
        txtAdress = findViewById(R.id.txtAdress);
        txtInformation = findViewById(R.id.txtInfomation);
        txtFriend = findViewById(R.id.txtFriend);
        txtAverage = findViewById(R.id.txtAverage);
    }
    private void fakeData() {
        imgDetail.setImageResource(R.drawable.testimage4);
        txtName.setText("An Vy, 20");
        txtAdress.setText("Thanh Pho Ho Chi Minh");
        txtInformation.setText("Xinh đẹp, dễ thương. Đang kiếm chồng, đặc biệt phải giàu cơ sáu múi. An Vy cuc ky de thuong.");
        txtFriend.setText("5000");
        txtAverage.setText("9.0");
    }
}
