package com.example.vankien.dating.Views.Activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vankien.dating.Controllers.ProfileController;
import com.example.vankien.dating.Controllers.ProfileDelegate;
import com.example.vankien.dating.Models.FriendChatModel;
import com.example.vankien.dating.Models.Profile;
import com.example.vankien.dating.R;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity implements ProfileDelegate {
    ImageView imgDetail;
    ImageButton btnMessage,btnReject;
    TextView txtName, txtAdress, txtInformation, txtFriend, txtAverage;
    Profile profile;
    String id;

    ProfileController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        addControls();
        addEvents();
        Intent intent = getIntent();
        id = intent.getStringExtra("UserID");

        controller = ProfileController.getsInstance();
        controller.delegate = this;
        requestData();
    }

    private void addEvents() {
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profile != null){
                    Intent intent = new Intent(DetailActivity.this,ChatActivity.class);
                    FriendChatModel model = new FriendChatModel();
                    model.setId(id);
                    model.setUrlAvatar(profile.getmImage());
                    model.setName(profile.getmName());
                    Bundle b = new Bundle();
                    b.putSerializable("FriendData",model);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            }
        });
    }

    private void requestData() {
        controller.requestProfile(id);
    }

    private void addControls() {
        imgDetail = findViewById(R.id.imgDetail);
        txtName = findViewById(R.id.txtName);
        txtAdress = findViewById(R.id.txtAdress);
        txtInformation = findViewById(R.id.txtInfomation);
        txtFriend = findViewById(R.id.txtFriend);
        txtAverage = findViewById(R.id.txtAverage);

        btnReject = findViewById(R.id.btn_reject);
        btnMessage = findViewById(R.id.btn_mesage);
    }
    private void setDataIntoView(){
        txtName.setText(profile.getmName()+" - "+profile.getmAge());
        txtAdress.setText(profile.getmAddress()+" - "+profile.getmRegion());
        txtInformation.setText(profile.getmDescription());
        txtFriend.setText(profile.getmNumOfFriends()+"");

        Uri uri = Uri.parse(profile.getmImage());
        Picasso.with(getBaseContext()).load(uri).into(imgDetail);
    }

    @Override
    public void getProfileSuccess(Profile data) {
        this.profile = data;
        setDataIntoView();
    }
}
