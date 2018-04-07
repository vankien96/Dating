package com.example.vankien.dating.Views.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vankien.dating.Controllers.ProfileController;
import com.example.vankien.dating.Controllers.ProfileDelegate;
import com.example.vankien.dating.Controllers.UploadImageDelegate;
import com.example.vankien.dating.Models.Profile;
import com.example.vankien.dating.R;
import com.example.vankien.dating.Utils.FirebaseUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity implements UploadImageDelegate,ProfileDelegate {
    private static final int REQUEST_IMAGE = 200;
    private ImageButton imgBtnBack,imgBtnSave;
    private EditText edtName, edtAge, edtSex, edtAbout;
    private ImageView imvAvatar;
    Profile profile;
    ProfileController controller;
    Bitmap avatarBitmap;
    String id;
    FirebaseUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initControls();
        loadData();
        addEvents();
    }

    public void initControls(){
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        imgBtnSave = (ImageButton) findViewById(R.id.imgBtnSave);
        edtName = (EditText) findViewById(R.id.edtName);
        edtAge = (EditText) findViewById(R.id.edtAge);
        edtSex = (EditText) findViewById(R.id.edtSex);
        edtAbout = (EditText) findViewById(R.id.edtAbout);
        imvAvatar = (ImageView) findViewById(R.id.imvAvatar);

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        utils = FirebaseUtils.getShareInstance();
        utils.delegate = this;
        controller = ProfileController.getsInstance();
        controller.delegate = this;
    }

    public void addEvents(){
        imgBtnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        imgBtnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfileToFirebase();

            }
        });
        imvAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
                File pictureDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String pictureDirectoryPath = pictureDirectory.getPath();
                Uri data = Uri.parse(pictureDirectoryPath);
                photoPickerIntent.setDataAndType(data,"image/*");
                startActivityForResult(photoPickerIntent,REQUEST_IMAGE);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode==RESULT_OK){
            if(requestCode == REQUEST_IMAGE){

                Uri imageUri = data.getData();
                InputStream inputStream;
                try {
                    inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap imageAvatar = BitmapFactory.decodeStream(inputStream);
                    imvAvatar.setImageBitmap(imageAvatar);
                    avatarBitmap = imageAvatar;
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),"Image not found",Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void loadData(){
        controller.requestProfile(id);
    }
    private void uploadProfileToFirebase() {
        String name = edtName.getText().toString().trim();
        String age = edtAge.getText().toString().trim();
        String about = edtAbout.getText().toString().trim();

        if(TextUtils.isEmpty(name)){
            Toast.makeText(getApplicationContext(), "Enter your name!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(TextUtils.isEmpty(age)){
            Toast.makeText(getApplicationContext(), "Enter your age!", Toast.LENGTH_SHORT).show();
            return;
        }

        if(avatarBitmap != null){
            utils.uploadImage(avatarBitmap,id);
        }
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference();
        reference.child("Profile").child(id).child("name").setValue(name);
        reference.child("Profile").child(id).child("age").setValue(age);
        reference.child("Profile").child(id).child("about").setValue(about);
        Toast.makeText(this,"Upload successfully...",Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void uploadImageSuccess(String avatarUrl) {
        
    }

    @Override
    public void uploadImageFailed() {

    }

    @Override
    public void getProfileSuccess(Profile data) {
        edtName.setText(data.getmName());
        edtAge.setText(data.getmAge()+"");
        edtAbout.setText(data.getmDescription());
        edtSex.setText(data.getmSex()=="1"?"Nam":"Nữ");
        Uri uri = Uri.parse(data.getmImage());
        Picasso.with(getBaseContext()).load(uri).into(imvAvatar);
    }

}
