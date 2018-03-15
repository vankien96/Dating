package com.example.vankien.dating.Views.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.vankien.dating.Controllers.ProfileController;
import com.example.vankien.dating.Models.Profile;
import com.example.vankien.dating.R;

public class EditProfileActivity extends AppCompatActivity {
    private ImageButton imgBtnBack,imgBtnSave;
    private EditText edtName, edtAge, edtSex, edtAbout;
    Profile profile;

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

        profile = ProfileController.getsInstance().getProfile();

        edtName = (EditText) findViewById(R.id.edtName);
        edtAge = (EditText) findViewById(R.id.edtAge);
        edtSex = (EditText) findViewById(R.id.edtSex);
        edtAbout = (EditText) findViewById(R.id.edtAbout);
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
    }

    public void loadData(){
        edtName.setText(profile.getmName());
        edtAge.setText(String.valueOf(profile.getmAge()));
        edtSex.setText(profile.getmSex()==0?"Nữ":"Nam");
        edtAbout.setText(profile.getmDescription());
    }
    private void saveYourProfile() {
        Toast.makeText(this, "Save successfully!", Toast.LENGTH_SHORT).show();
    }

}
