package com.example.vankien.dating.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.vankien.dating.controllers.SignUpController;
import com.example.vankien.dating.delegate.SignUpDelegate;
import com.example.vankien.dating.models.Profile;
import com.example.vankien.dating.R;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
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
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONObject;

import java.util.Arrays;

public class SignUpActivity extends AppCompatActivity implements SignUpDelegate {
    EditText edtEmailSignUp, edtPassSignUp;
    Button btnCreateAccount,btnLogInWithFB, btnSignIn;
    private FirebaseAuth mAuth;
    LoginButton loginButton;
    AVLoadingIndicatorView indicatorView;
    SignUpController controller;
    CallbackManager callbackManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //Khoi tao
        callbackManager = CallbackManager.Factory.create();
        mAuth = FirebaseAuth.getInstance();
        controller = SignUpController.getShareInstance();
        controller.delegate = this;
        addControls();
        addEvents();
        setLoginFacebook();
    }

    private void addControls() {
        edtEmailSignUp = findViewById(R.id.edt_email);
        edtPassSignUp = findViewById(R.id.edt_password);
        btnCreateAccount = findViewById(R.id.btn_create_account);
        loginButton= findViewById(R.id.btn_login_fb);
        btnSignIn = findViewById(R.id.btn_sign_in);
        indicatorView = findViewById(R.id.avi);
        loginButton.setReadPermissions(Arrays.asList("public_profile","email"));
    }

    private void setLoginFacebook() {
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(SignUpActivity.this,"Cancel login",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(SignUpActivity.this,"Error",Toast.LENGTH_LONG).show();
            }
        });
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        indicatorView.show();
        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            final String id = user.getUid();
                            final FirebaseDatabase database = FirebaseDatabase.getInstance();
                            database.getReference().child("Profile").addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.hasChild(id)) {
                                        Intent intent = new Intent(SignUpActivity.this,MainActivity.class);
                                        startActivity(intent);
                                        indicatorView.hide();
                                    } else {
                                        resultLoginWithFacebook();
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        } else {
                            try {
                                throw task.getException();
                            } catch(FirebaseAuthUserCollisionException ex) {
                                Toast.makeText(SignUpActivity.this,"Your email already exist. Please login with email and pass",Toast.LENGTH_LONG).show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
    }

    private void resultLoginWithFacebook() {
        GraphRequest graphRequest = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                try{
                    String name = object.getString("name");
                    String email = object.getString("email");
                    int id = object.getInt("id");
                    String gender = object.getString("gender");
                    String profilePicUrl = (String) response.getJSONObject().getJSONObject("picture").getJSONObject("data").get("url");
                    Log.e("Login",response.getJSONObject().toString());
                    Profile profile = new Profile();
                    Log.e("Login",id+"");
                    profile.setmImage(profilePicUrl);
                    profile.setmName(name);
                    if ("male".equals(gender)) {
                        profile.setmSex(1);
                    } else {
                        profile.setmSex(0);
                    }
                    profile.setmAddress("");
                    profile.setmDescription("");
                    Intent intent = new Intent(SignUpActivity.this,EditProfileActivity.class);
                    intent.putExtra("Profile",profile);
                    intent.putExtra("isEdit",true);
                    intent.putExtra("Facebook",true);
                    startActivity(intent);
                    finish();
                }
                catch(Exception e) {
                    indicatorView.hide();
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,email,first_name,gender,birthday,picture.type(large)");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
