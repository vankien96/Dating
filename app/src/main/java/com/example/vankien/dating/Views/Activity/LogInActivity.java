package com.example.vankien.dating.Views.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vankien.dating.Controllers.LoginController;
import com.example.vankien.dating.Controllers.LoginDelegate;
import com.example.vankien.dating.R;
import com.google.firebase.auth.FirebaseAuth;
import com.wang.avi.AVLoadingIndicatorView;

public class LogInActivity extends AppCompatActivity implements LoginDelegate {

    EditText edtEmailLogIn, edtPassLogIn;
    Button btnAccess, btnLogInWithFB, btnSignUp, btnRecover;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    AVLoadingIndicatorView indicatorView;

    LoginController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        //
        // Khoi tao
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("dataLogIn",MODE_PRIVATE);
        controller = LoginController.getShareInstance();
        controller.delegate = this;

        addControls();
        addEvents();
        getSharedPreferences();
    }

    private void addControls() {
        edtEmailLogIn = findViewById(R.id.edt_email);
        edtPassLogIn = findViewById(R.id.edt_password);
        btnAccess = findViewById(R.id.btn_login);
        btnLogInWithFB = findViewById(R.id.btn_login_fb);
        btnSignUp = findViewById(R.id.btn_sign_up);
        btnRecover = findViewById(R.id.btn_recover);
        indicatorView = findViewById(R.id.avi);
    }

    private void addEvents() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this,SignUpActivity.class);
                startActivity(intent);
            }
        });

        btnAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicatorView.show();
                String email = edtEmailLogIn.getText().toString();
                String pass =edtPassLogIn.getText().toString();
                if(email.isEmpty()|| pass.isEmpty()) {
                    indicatorView.hide();
                    Toast.makeText(LogInActivity.this, "Email or Password wrong ! Please try again !", Toast.LENGTH_SHORT).show();
                }
                else {
                    controller.logIn(email,pass,LogInActivity.this);
                }
            }
        });

        btnRecover.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicatorView.show();
                startActivity(new Intent(LogInActivity.this,ResetPasswordActivity.class));
            }
        });

    }

    protected void saveStatusLogIn(String email, String pass) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("emailLogIn", email);
        editor.putString("passwordLogIn",pass);
        editor.commit();
    }

    public void getSharedPreferences() {
        edtEmailLogIn.setText(sharedPreferences.getString("emailLogIn",""));
        edtPassLogIn.setText(sharedPreferences.getString("passwordLogIn",""));

    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public void loginSuccess(String email, String password) {
        saveStatusLogIn(email,password);
        Intent intent = new Intent(LogInActivity.this, MainActivity.class);
        indicatorView.hide();
        startActivity(intent);
    }

    @Override
    public void loginFailed() {
        Toast.makeText(LogInActivity.this, "Email or Password wrong ! Please try again !", Toast.LENGTH_SHORT).show();
    }
}
