package com.example.vankien.dating.Views.Activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.vankien.dating.R;
import com.example.vankien.dating.helpers.TouchImageView;
import com.squareup.picasso.Picasso;

public class FullScreenImageActivity extends AppCompatActivity {

    private TouchImageView image;
    private RelativeLayout rlTitleBar;
    private ImageButton imgBtnBack, imgBtnShare;

    String uriImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image);
        init();
        loadImage();
        addEvent();
    }

    public void init(){
        image = (TouchImageView) findViewById(R.id.imvAvatar);
        rlTitleBar =(RelativeLayout) findViewById(R.id.rlTitleBar);
        rlTitleBar.setVisibility(View.GONE);
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        imgBtnShare = (ImageButton) findViewById(R.id.imgBtnShare);
    }

    public void addEvent(){
        image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlTitleBar.setVisibility(View.VISIBLE);
            }
        });
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


    }

    public void loadImage(){
        Intent intent = getIntent();
        uriImage = intent.getStringExtra("uriImage");
        Uri uri = Uri.parse(uriImage);
        Picasso.with(getBaseContext()).load(uri).into(image);
    }
}
