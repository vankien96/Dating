package com.example.vankien.dating.views.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vankien.dating.controllers.BlockController;
import com.example.vankien.dating.controllers.ProfileController;
import com.example.vankien.dating.delegate.BlockDelegate;
import com.example.vankien.dating.delegate.ProfileDelegate;
import com.example.vankien.dating.models.Constant;
import com.example.vankien.dating.models.FriendChatModel;
import com.example.vankien.dating.models.Profile;
import com.example.vankien.dating.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class DetailActivity extends AppCompatActivity implements ProfileDelegate {
    ImageView imgDetail;
    ImageButton btnMessage,btnReject;
    TextView txtName, txtAdress, txtInformation, txtFriend, txtSex;
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
        boolean fromChat = intent.getBooleanExtra("FromChat",false);
        if (fromChat) {
          btnReject.setVisibility(View.GONE);
          btnMessage.setVisibility(View.GONE);
        }

        controller = ProfileController.getsInstance();
        controller.delegate = this;
        requestData();
    }

    private void addEvents() {
        btnMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (profile != null){
                    BlockController.getShareInstance().checkBlock(id, new BlockDelegate() {
                        @Override
                        public void getRejectListSuccess(ArrayList<String> rejectedPeople) {

                        }

                        @Override
                        public void checkBlock(String block) {
                            if (Constant.blocked.equals(block)) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(DetailActivity.this,"This user blocked you.",Toast.LENGTH_LONG).show();
                                    }
                                });
                            } else {
                                Intent intent = new Intent(DetailActivity.this,ChatActivity.class);
                                FriendChatModel model = new FriendChatModel();
                                model.setId(id);
                                model.setUrlAvatar(profile.getmImage());
                                model.setName(profile.getmName());
                                Bundle b = new Bundle();
                                b.putSerializable("FriendData",model);
                                if (Constant.block.equals(block)) {
                                    b.putBoolean("isBlock",true);
                                }
                                intent.putExtras(b);
                                startActivity(intent);
                            }
                        }
                    });
                }
            }
        });
        btnReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BlockController.getShareInstance().rejectPeople(id);
                finish();
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
        txtInformation.setMovementMethod(new ScrollingMovementMethod());
        txtFriend = findViewById(R.id.txtFriend);
        txtSex = findViewById(R.id.txtSex);

        btnReject = findViewById(R.id.btn_reject);
        btnMessage = findViewById(R.id.btn_mesage);
    }
    private void setDataIntoView(){
        txtName.setText(profile.getmName());
        txtAdress.setText(profile.getmAddress()+" - "+profile.getmRegion());
        txtInformation.setText(profile.getmDescription());
        txtFriend.setText(profile.getmNumOfFriends()+"");
        if(profile.getmSex()==1) {
            txtSex.setText("Man" +" - "+profile.getmAge());;
        }
        if(profile.getmSex()==0) {
            txtSex.setText("Woman" +" - "+profile.getmAge());;
        }
        Uri uri = Uri.parse(profile.getmImage());
        Picasso.with(getBaseContext()).load(uri).into(imgDetail);
    }

    @Override
    public void getProfileSuccess(Profile data) {
        this.profile = data;
        setDataIntoView();
    }
}
