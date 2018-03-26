package com.example.vankien.dating.Views.Activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.vankien.dating.Controllers.ProfileController;
import com.example.vankien.dating.Controllers.UploadImageDelegate;
import com.example.vankien.dating.Models.Profile;
import com.example.vankien.dating.R;
import com.example.vankien.dating.Utils.FirebaseUtils;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity implements UploadImageDelegate {
    private static final int REQUEST_IMAGE = 200;
    private ImageButton imgBtnBack,imgBtnSave;
    private EditText edtName, edtAge, edtSex, edtAbout;
    private ImageView imvAvatar;
    Profile profile;
    Bitmap avatarBitmap;
    String id;
    FirebaseUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        utils = FirebaseUtils.getShareInstance();
        utils.callback = this;


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
        profile = ProfileController.getsInstance().getProfile();
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
                saveYourProfile();
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
        edtName.setText(profile.getmName());
        edtAge.setText(String.valueOf(profile.getmAge()));
        edtSex.setText(profile.getmSex()==0?"Nữ":"Nam");
        edtAbout.setText(profile.getmDescription());
    }
    private void saveYourProfile() {
        //Toast.makeText(this, "Save successfully!", Toast.LENGTH_SHORT).show();
        if(avatarBitmap != null){
            utils.uploadImage(avatarBitmap,id);
        }
    }

    @Override
    public void uploadImageSuccess(String avatarUrl) {
        
    }

    @Override
    public void uploadImageFailed() {

    }
}
