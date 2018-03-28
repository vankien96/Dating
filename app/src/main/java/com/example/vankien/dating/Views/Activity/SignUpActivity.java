package com.example.vankien.dating.Views.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vankien.dating.Controllers.SignUpController;
import com.example.vankien.dating.Controllers.SignUpDelegate;
import com.example.vankien.dating.R;
import com.google.firebase.auth.FirebaseAuth;
import com.wang.avi.AVLoadingIndicatorView;

public class SignUpActivity extends AppCompatActivity implements SignUpDelegate {
    EditText edtEmailSignUp, edtPassSignUp;
    Button btnCreateAccount,btnLogInWithFB, btnSignIn;
    private FirebaseAuth mAuth;
    AVLoadingIndicatorView indicatorView;
    SignUpController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Khoi tao
        mAuth = FirebaseAuth.getInstance();
        controller = SignUpController.getShareInstance();
        controller.delegate = this;
        addControls();
        addEvents();
    }

    private void addControls() {
        edtEmailSignUp = findViewById(R.id.edt_email);
        edtPassSignUp = findViewById(R.id.edt_password);
        btnCreateAccount = findViewById(R.id.btn_create_account);
        btnLogInWithFB = findViewById(R.id.btn_login_fb);
        btnSignIn = findViewById(R.id.btn_sign_in);
        indicatorView = findViewById(R.id.avi);
    }
    private void addEvents() {
        btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicatorView.show();
                String email = edtEmailSignUp.getText().toString();
                String password = edtPassSignUp.getText().toString();
                if(email.isEmpty()|| password.isEmpty()) {
                    indicatorView.hide();
                    Toast.makeText(SignUpActivity.this, "Email or Password wrong ! Please try again !", Toast.LENGTH_SHORT).show();
                }
                else {
                    controller.signUp(email,password,SignUpActivity.this);
                }
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void signupSuccess(String email, String password) {
        Intent intent = new Intent(SignUpActivity.this, EditProfileActivity.class);
        indicatorView.hide();
        startActivity(intent);
    }

    @Override
    public void signFailed() {
        indicatorView.hide();
        Toast.makeText(SignUpActivity.this, "Email or Password wrong ! Please try again !", Toast.LENGTH_SHORT).show();

    }




}
