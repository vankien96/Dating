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
import com.example.vankien.dating.Controllers.LoginControllerCallback;
import com.example.vankien.dating.R;
import com.google.firebase.auth.FirebaseAuth;
import com.wang.avi.AVLoadingIndicatorView;

public class LogInActivity extends AppCompatActivity implements LoginControllerCallback{

    EditText edtEmailLogIn, edtPassLogIn;
    Button btnAccess, btnLogInWithFB, btnSignUp, btnAccessSignUp, btnCancel, btnRecover;
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
        controller.callback = this;

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
                    Toast.makeText(LogInActivity.this, "Email or Password wrong ! Please try again !", Toast.LENGTH_SHORT).show();
                }
                else {
                    controller.logIn(email,pass,LogInActivity.this);
                }
            }
        });

    }

//    private void dialog_DangKy() {
//        final Dialog dialog = new Dialog(this);
//        dialog.setContentView(R.layout.dialog_dang_ki);
//        dialog.setCanceledOnTouchOutside(false);
//        // Anh Xa
//        final EditText edtEmailSignUp= dialog.findViewById(R.id.edt_email_sign_up);
//        final EditText edtPassSignUp= dialog.findViewById(R.id.edt_password_sign_up);
//        btnAccessSignUp = dialog.findViewById(R.id.btn_sign_up2);
//        btnCancel = dialog.findViewById(R.id.btn_cancel);
//
//        // Event Dang Ky
//        btnAccessSignUp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String email = edtEmailSignUp.getText().toString();
//                String password = edtPassSignUp.getText().toString();
//                if(email.isEmpty()|| password.isEmpty()) {
//                    Toast.makeText(LogInActivity.this, "Email or Password wrong ! Please try again !", Toast.LENGTH_SHORT).show();
//                }
//                else {
//                    dangKy(email,password);
//                }
//            }
//        });
//
//        btnCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//        dialog.show();
//    }

//    protected void dangKy(String email, String password) {
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if(task.isSuccessful()) {
//                            Toast.makeText(LogInActivity.this,"SignUp Successful !",Toast.LENGTH_SHORT).show();
//
//                        }
//                        else {
//                            Toast.makeText(LogInActivity.this,"SignUp Error !",Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
//    }

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
