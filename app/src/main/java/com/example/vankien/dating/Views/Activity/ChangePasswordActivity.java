package com.example.vankien.dating.Views.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.vankien.dating.Controllers.ChangePassWordController;
import com.example.vankien.dating.Interface.ChangePassWordDelegate;
import com.example.vankien.dating.R;
import com.google.firebase.auth.FirebaseAuth;

public class ChangePasswordActivity extends AppCompatActivity implements ChangePassWordDelegate,View.OnClickListener{

    private ImageButton imgBtnBack;
    private TextView edtOldPass, edtNewPass, edtNewPassCompare;
    private Button btnUpdatePass;
    private ToggleButton tgBtnOldPass, tgBtnNewPass;
    private ProgressBar progressBar;
    ChangePassWordController controller;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        init();
        addEvent();
    }

    public void init(){
        imgBtnBack = (ImageButton) findViewById(R.id.imgBtnBack);
        edtOldPass = (TextView) findViewById(R.id.edtOldPass);
        edtNewPass = (TextView) findViewById(R.id.edtNewPass);
        edtNewPassCompare = (TextView) findViewById(R.id.edtNewPassCompare);
        tgBtnOldPass = (ToggleButton) findViewById(R.id.tgBtnOldPass);
        tgBtnNewPass = (ToggleButton) findViewById(R.id.tgBtnNewPass);
        btnUpdatePass = (Button) findViewById(R.id.btnUpdatePass);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        auth = FirebaseAuth.getInstance();
        controller = ChangePassWordController.getsInstance();
        controller.delegate = this;
    }

    public void addEvent(){
            tgBtnOldPass.setOnClickListener(this);
            tgBtnNewPass.setOnClickListener(this);
            btnUpdatePass.setOnClickListener(this);
            imgBtnBack.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tgBtnOldPass:
                changeTypeDisplayOldPassword();
                break;
            case R.id.tgBtnNewPass:
                changeTypeDisplayNewPassword();
                break;
            case R.id.btnUpdatePass:
                changePassword();
                break;
            case R.id.imgBtnBack:
                finish();
                break;
        }
    }

    private void changeTypeDisplayNewPassword() {

    }

    private void changeTypeDisplayOldPassword() {
        if(tgBtnOldPass.getText().toString().equals("Show")){
            edtOldPass.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
        } else edtOldPass.setInputType(InputType.TYPE_CLASS_TEXT);
    }

    public void changePassword(){
        String oldPassword = edtOldPass.getText().toString().trim();
        String newPassword = edtNewPass.getText().toString().trim();
        String newPasswordCompare = edtNewPassCompare.getText().toString().trim();

        System.out.println(oldPassword);
        System.out.println(newPassword);
        System.out.println(newPasswordCompare);

        if(TextUtils.isEmpty(oldPassword)){
            Toast.makeText(getApplicationContext(),"Please enter your old password!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(newPassword)){
            Toast.makeText(getApplicationContext(),"Please enter your new password!",Toast.LENGTH_SHORT).show();
            return;
        }
        if(TextUtils.isEmpty(newPasswordCompare)){
            Toast.makeText(getApplicationContext(),"Please enter again your new password!",Toast.LENGTH_SHORT).show();
            return;
        }
//        if(newPassword!=newPasswordCompare) {
//            Toast.makeText(getApplicationContext(),"You enter again new password not true!",Toast.LENGTH_SHORT).show();
//            return;
//        }
        controller.updatePassword(newPassword,ChangePasswordActivity.this);

    }

    @Override
    public void updateSucess(String password) {
        Toast.makeText(getApplicationContext(), "Password is updated!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void updateFailed() {
        Toast.makeText(getApplicationContext(), "Failed to update password!", Toast.LENGTH_SHORT).show();
        progressBar.setVisibility(View.GONE);
    }
}
