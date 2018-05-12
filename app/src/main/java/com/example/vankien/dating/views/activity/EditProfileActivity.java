package com.example.vankien.dating.views.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.vankien.dating.R;
import com.example.vankien.dating.controllers.ProfileController;
import com.example.vankien.dating.delegate.UploadImageDelegate;
import com.example.vankien.dating.models.Profile;
import com.example.vankien.dating.utils.FirebaseUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class EditProfileActivity extends AppCompatActivity implements UploadImageDelegate {
    private static final int REQUEST_IMAGE = 200;
    private ImageButton imgBtnBack,imgBtnSave;
    private EditText edtName, edtAge, edtAbout, edtCountry,edtAddress;
    private RadioButton radioMale,radioFemale;
    private ImageView imvAvatar;
    Profile profile;
    Bitmap avatarBitmap;
    String id;
    FirebaseUtils utils;
    boolean isEdit = false;
    boolean isFacebook = false;
    LinearLayout viewProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        initControls();
        addEvents();
        Intent intent = getIntent();
        isEdit = intent.getBooleanExtra("isEdit",false);
        isFacebook = intent.getBooleanExtra("Facebook",false);
        profile = (Profile) intent.getSerializableExtra("Profile");
        if (profile != null) {
            loadData();
        }
        if(!isEdit || isFacebook) {
            imgBtnBack.setVisibility(View.INVISIBLE);
        }
    }

    public void initControls(){
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        imgBtnSave = (ImageButton) findViewById(R.id.imgBtnSave);
        edtName = (EditText) findViewById(R.id.edtName);
        edtAge = (EditText) findViewById(R.id.edtAge);
        edtAbout = (EditText) findViewById(R.id.edtAbout);
        imvAvatar = (ImageView) findViewById(R.id.imvAvatar);
        edtAddress = findViewById(R.id.edtAdress);
        edtCountry = findViewById(R.id.edtCountry);
        radioMale = findViewById(R.id.radioMale);
        radioFemale = findViewById(R.id.radioFemale);
        viewProgress = findViewById(R.id.viewProgress);

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        utils = FirebaseUtils.getShareInstance();
        utils.delegate = this;
    }

    private boolean validateTextField() {
        String name = edtName.getText().toString().trim();
        String age = edtAge.getText().toString().trim();
        String about = edtAbout.getText().toString().trim();
        String region = edtCountry.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();
        if(TextUtils.isEmpty(name)){
            Toast.makeText(getApplicationContext(), "Enter your name!", Toast.LENGTH_SHORT).show();
            imgBtnSave.setActivated(true);
            return false;
        }

        if(TextUtils.isEmpty(age)){
            Toast.makeText(getApplicationContext(), "Enter your age!", Toast.LENGTH_SHORT).show();
            imgBtnSave.setActivated(true);
            return false;
        }
        if (TextUtils.isEmpty(region)) {
            Toast.makeText(getApplicationContext(), "Enter your region!", Toast.LENGTH_SHORT).show();
            imgBtnSave.setActivated(true);
            return false;
        }
        if (TextUtils.isEmpty(about)) {
            Toast.makeText(getApplicationContext(), "Enter your description!", Toast.LENGTH_SHORT).show();
            imgBtnSave.setActivated(true);
            return false;
        }
        if (TextUtils.isEmpty(address)) {
            Toast.makeText(getApplicationContext(), "Enter your address!", Toast.LENGTH_SHORT).show();
            imgBtnSave.setActivated(true);
            return false;
        }
        return true;
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
                if (validateTextField()) {
                    if (isEdit) {
                        if (avatarBitmap != null) {
                            imgBtnSave.setActivated(false);
                            viewProgress.setVisibility(View.VISIBLE);
                            utils.uploadAvatar(avatarBitmap, id);
                        } else {
                            viewProgress.setVisibility(View.VISIBLE);
                            uploadProfileToFirebase();
                        }
                    } else {
                        profile = new Profile();
                        profile.setmNumOfFriends(0);
                        if (avatarBitmap != null) {
                            imgBtnSave.setActivated(false);
                            viewProgress.setVisibility(View.VISIBLE);
                            utils.uploadAvatar(avatarBitmap, id);
                        } else {
                            String defaultAvatar = getResources().getString(R.string.default_avatar);
                            defaultAvatar = defaultAvatar.replaceAll("^^^","&");
                            profile.setmImage(defaultAvatar);
                            viewProgress.setVisibility(View.VISIBLE);
                            uploadProfileToFirebase();
                        }
                    }
                }
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

        edtCountry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCountry();
            }
        });
    }
    private void showDialogCountry() {
        final String [] countries = getResources().getStringArray(R.array.countries);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose your country");
        builder.setItems(countries, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                edtCountry.setText(countries[which]);
            }
        });
        builder.create().show();
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
        edtAge.setText(profile.getmAge()+"");
        edtAbout.setText(profile.getmDescription());
        if (profile.getmSex() == 1) {
            radioMale.setChecked(true);
        } else {
            radioFemale.setChecked(true);
        }
        edtCountry.setText(profile.getmRegion());
        edtAddress.setText(profile.getmAddress());
        Uri uri = Uri.parse(profile.getmImage());
        if (isFacebook) {
            Picasso.with(getBaseContext()).load(uri).into(new Target() {
                @Override
                public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                    imvAvatar.setImageBitmap(bitmap);
                    avatarBitmap = bitmap;
                }

                @Override
                public void onBitmapFailed(Drawable errorDrawable) {

                }

                @Override
                public void onPrepareLoad(Drawable placeHolderDrawable) {

                }
            });
        } else {
            Picasso.with(getBaseContext()).load(uri).into(imvAvatar);
        }
    }
    private void uploadProfileToFirebase() {
        String name = edtName.getText().toString().trim();
        String age = edtAge.getText().toString().trim();
        String about = edtAbout.getText().toString().trim();
        String region = edtCountry.getText().toString().trim();
        String address = edtAddress.getText().toString().trim();

        profile.setmName(name);
        profile.setmAge(Integer.parseInt(age));
        profile.setmDescription(about);
        profile.setmAddress(address);
        profile.setmRegion(region);
        if (radioMale.isChecked()) {
            profile.setmSex(1);
        } else {
            profile.setmSex(0);
        }
        ProfileController.getsInstance().uploadProfile(profile,id);
        viewProgress.setVisibility(View.GONE);
        Toast.makeText(this,"Upload successfully...",Toast.LENGTH_SHORT).show();
        setResult(RESULT_OK);
        if (isEdit && !isFacebook) {
            finish();
        } else {
            Intent intent = new Intent(EditProfileActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    public void uploadImageSuccess(String avatarUrl) {
        if (profile != null) {
            profile.setmImage(avatarUrl);
            uploadProfileToFirebase();
        }
    }

    @Override
    public void uploadImageFailed() {
        viewProgress.setVisibility(View.GONE);
        imgBtnSave.setActivated(true);
    }

    @Override
    public void onBackPressed() {
        if (isEdit && isFacebook) {

        } else if (!isEdit) {

        } else {
            finish();
        }
    }
}
