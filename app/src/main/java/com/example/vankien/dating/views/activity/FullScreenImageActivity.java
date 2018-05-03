package com.example.vankien.dating.views.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.example.vankien.dating.R;
import com.example.vankien.dating.helpers.TouchImageView;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.ByteArrayOutputStream;

public class FullScreenImageActivity extends AppCompatActivity {
    public static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;

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
                //Share image not ok
                if(checkWriteExternal()) {
                    if (globalBitMap != null) {
                        shareImage();
                    }
                }
            }
        });
    }

//Cach 1
    public void shareImage() {
        Intent shareIntent = new Intent ();
        shareIntent.setAction(Intent.ACTION_SEND );
        shareIntent.putExtra(Intent.EXTRA_STREAM,getImageUri(this,globalBitMap) );
        shareIntent.putExtra(Intent.EXTRA_TEXT,"Welcome to dating");
        shareIntent.putExtra(Intent.ACTION_APPLICATION_PREFERENCES,"Dating");
        shareIntent.setType("image/*");
        startActivity(Intent.createChooser(shareIntent,""));

    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "avatar", null);
        return Uri.parse(path);
    }

    public void loadImage(){
        Intent intent = getIntent();
        uriImage = intent.getStringExtra("uriImage");
        uri = Uri.parse(uriImage);
        Picasso.with(getBaseContext()).load(uri).into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                globalBitMap = bitmap;
                image.setImageBitmap(bitmap);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {

            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (ContextCompat.checkSelfPermission(this,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_GRANTED) {
                        if (globalBitMap != null) {
                            shareImage();
                        }
                    }
                } else {

                }
                return;
            }

        }
    }

    public boolean checkWriteExternal() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                new AlertDialog.Builder(this)
                        .setTitle("Write external storage")
                        .setMessage("Dating want to user your storage to save photo. Are you agree?")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                //Prompt the user once explanation has been shown
                                ActivityCompat.requestPermissions(FullScreenImageActivity.this,
                                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                                        MY_PERMISSIONS_REQUEST_LOCATION);
                            }
                        })
                        .create()
                        .show();
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_LOCATION);
            }
            return false;
        } else {
            return true;
        }
    }
}
