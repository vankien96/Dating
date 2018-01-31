package com.example.vankien.dating.controller;

import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vankien.dating.MainActivity;
import com.example.vankien.dating.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class DangNhapActivity extends AppCompatActivity {

    EditText edtEmailLogIn, edtPassLogIn, edtEmailSignUp, edtPassSignUp;
    Button btnAccess, btnLogInWithFB, btnSignUp, btnAccessSignUp, btnCancel, btnRecover;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        // Khoi tao
        mAuth = FirebaseAuth.getInstance();
        anhXa();
        addEvents();
    }

    private void anhXa() {
        edtEmailLogIn = findViewById(R.id.edt_email);
        edtPassLogIn = findViewById(R.id.edt_password);
        btnAccess = findViewById(R.id.btn_login);
        btnLogInWithFB = findViewById(R.id.btn_login_fb);
        btnSignUp = findViewById(R.id.btn_sign_up);
        btnRecover = findViewById(R.id.btn_recover);
    }

    private void addEvents() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_DangKy();
            }
        });

        btnAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmailLogIn.getText().toString();
                String pass =edtPassLogIn.getText().toString();
                if(email.isEmpty()|| pass.isEmpty()) {
                    Toast.makeText(DangNhapActivity.this, "Email or Password wrong ! Please try again !", Toast.LENGTH_SHORT).show();
                }
                else {
                    dangNhap(email,pass);
                }
            }
        });
    }

    private void dialog_DangKy() {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_dang_ki);
        dialog.setCanceledOnTouchOutside(false);
        // Anh Xa
        final EditText edtEmailSignUp= dialog.findViewById(R.id.edt_email_sign_up);
        final EditText edtPassSignUp= dialog.findViewById(R.id.edt_password_sign_up);
        btnAccessSignUp = dialog.findViewById(R.id.btn_sign_up2);
        btnCancel = dialog.findViewById(R.id.btn_cancel);

        // Event Dang Ky
        btnAccessSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = edtEmailSignUp.getText().toString();
                String password = edtPassSignUp.getText().toString();
                if(email.isEmpty()|| password.isEmpty()) {
                    Toast.makeText(DangNhapActivity.this, "Email or Password wrong ! Please try again !", Toast.LENGTH_SHORT).show();
                }
                else {
                    dangKy(email,password);
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    protected void dangKy(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Toast.makeText(DangNhapActivity.this,"SignUp Successful !",Toast.LENGTH_SHORT).show();

                        }
                        else {
                            Toast.makeText(DangNhapActivity.this,"SignUp Error !",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    protected  void dangNhap(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(DangNhapActivity.this, "Email or Password wrong ! Please try again !", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}
