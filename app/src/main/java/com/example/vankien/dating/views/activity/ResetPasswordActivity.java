package com.example.vankien.dating.views.activity;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vankien.dating.controllers.ResetPasswordController;
import com.example.vankien.dating.delegate.ResetPasswordDelegate;
import com.example.vankien.dating.R;
import com.google.firebase.auth.FirebaseAuth;
import com.wang.avi.AVLoadingIndicatorView;

public class ResetPasswordActivity extends Activity implements ResetPasswordDelegate{

    private EditText inputEmail;
    private Button btnReset, btnBack;
    private FirebaseAuth auth;
    AVLoadingIndicatorView indicatorView;
    ResetPasswordController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);

        auth = FirebaseAuth.getInstance();
        controller = ResetPasswordController.getShareInstance();
        controller.delegate = this;
        addControls();
        addEvents();
    }

    private void addEvents() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                indicatorView.show();
                String email = inputEmail.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    indicatorView.hide();
                    Toast.makeText(getApplication(), "Enter your registered email id", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    controller.resetPassword(email,ResetPasswordActivity.this);
                }
            }
        });
    }

    private void addControls() {
        inputEmail = (EditText) findViewById(R.id.email);
        btnReset = (Button) findViewById(R.id.btn_reset_password);
        btnBack = (Button) findViewById(R.id.btn_back);
        indicatorView = findViewById(R.id.avi);
    }

    @Override
    public void resetSuccess(String email) {
        indicatorView.hide();
        Toast.makeText(ResetPasswordActivity.this, "We have sent you instructions to reset your password!", Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override
    public void resetFailed() {
        indicatorView.hide();
        Toast.makeText(ResetPasswordActivity.this, "Failed to send reset email!", Toast.LENGTH_SHORT).show();
    }
}
