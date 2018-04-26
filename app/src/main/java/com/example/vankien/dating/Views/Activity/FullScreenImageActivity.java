package com.example.vankien.dating.Views.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.vankien.dating.R;
import com.example.vankien.dating.helpers.TouchImageView;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

public class FullScreenImageActivity extends AppCompatActivity {

    private TouchImageView image;
    private RelativeLayout rlTitleBar;
    private ImageButton imgBtnBack, imgBtnShare;
    private  Uri uri;
    private ShareDialog shareDialog;
    private ShareLinkContent shareLinkContent;
    Bitmap globalBitMap;

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
        shareDialog = new ShareDialog(FullScreenImageActivity.this);
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
        imgBtnShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Share link
//                if(ShareDialog.canShow(ShareLinkContent.class )) {
//                    shareLinkContent = new ShareLinkContent.Builder()
//                                            .setContentUrl(uri)
//                                            .build();
//                }
//                shareDialog.show(FullScreenImageActivity.this,shareLinkContent);
                //Share image not ok
                shareImage();
                //Cach 2
//                SharePhoto photo = new SharePhoto.Builder()
//                        .setBitmap(globalBitMap)
//                        .build();
//                SharePhotoContent content = new SharePhotoContent.Builder()
//                        .addPhoto(photo)
//                        .build();
            }
        });
    }

//Cach 1
    public void shareImage() {
        Intent shareIntent = new Intent ();
        shareIntent . setAction ( Intent . ACTION_SEND );
        shareIntent . putExtra ( Intent . EXTRA_STREAM , uri );
        shareIntent . setType ( "image / jpeg" );
        startActivity (Intent. createChooser ( shareIntent , "Share to") );

    }

    public void loadImage(){
        Intent intent = getIntent();
        uriImage = intent.getStringExtra("uriImage");
        uri = Uri.parse(uriImage);
        Picasso.with(getBaseContext()).load(uri).into(image);
//        Picasso.with(getBaseContext()).load(uri).into(new Target() {
//            @Override
//            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
//                globalBitMap = bitmap;
//            }
//
//            @Override
//            public void onBitmapFailed(Drawable errorDrawable) {
//
//            }
//
//            @Override
//            public void onPrepareLoad(Drawable placeHolderDrawable) {
//
//            }
//        });
    }

}
