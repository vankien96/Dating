package com.example.vankien.dating.Views.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vankien.dating.Controllers.LoginController;
import com.example.vankien.dating.Interface.LoginDelegate;
import com.example.vankien.dating.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class LogInActivity extends AppCompatActivity implements LoginDelegate {

    EditText edtEmailLogIn, edtPassLogIn;
    Button btnAccess, btnSignUp, btnRecover;
    LoginButton loginButton;
    private FirebaseAuth mAuth;
    SharedPreferences sharedPreferences;
    AVLoadingIndicatorView indicatorView;
    CallbackManager callbackManager;
    LoginController controller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        // Khoi tao
        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("dataLogIn",MODE_PRIVATE);
        controller = LoginController.getShareInstance();
        controller.delegate = this;

        addControls();
        addEvents();
        getSharedPreferences();
        setLoginFacebook();
    }

    private void addControls() {
        edtEmailLogIn = findViewById(R.id.edt_email);
        edtPassLogIn = findViewById(R.id.edt_password);
        btnAccess = findViewById(R.id.btn_login);
        loginButton= findViewById(R.id.btn_login_fb);
        btnSignUp = findViewById(R.id.btn_sign_up);
        btnRecover = findViewById(R.id.btn_recover);
        indicatorView = findViewById(R.id.avi);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
    }

    private void setLoginFacebook() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Toast.makeText(LogInActivity.this,"Facebook:OnSuccess: "+loginResult,Toast.LENGTH_LONG).show();
                resultLoginWithFacebook();
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(LogInActivity.this,"Facebook:OnCancel",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(LogInActivity.this,"Facebook:OnError "+error,Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        Toast.makeText(LogInActivity.this,"handleFacebookAccessToken "+accessToken,Toast.LENGTH_LONG).show();
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(LogInActivity.this,"signInWithCredential:success",Toast.LENGTH_LONG).show();
                            FirebaseUser user = mAuth.getCurrentUser();
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(LogInActivity.this,"signInWithCredential:failure",Toast.LENGTH_LONG).show();
                         task.getException();
                        }
                        // ...
                    }
                });
    }

    private void resultLoginWithFacebook() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON",response.getJSONObject().toString());
                Toast.makeText(LogInActivity.this,response.getJSONObject().toString(),Toast.LENGTH_LONG).show();
                try{
                    String name = object.getString("name");
                    String email = object.getString("email");
                    int id = object.getInt("id");
                    String gender = object.getString("gender");
                }
                catch(Exception e) {

                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,first_name,gender,birthday");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
