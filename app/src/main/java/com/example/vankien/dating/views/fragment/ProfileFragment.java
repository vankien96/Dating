package com.example.vankien.dating.views.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.vankien.dating.controllers.LoginController;
import com.example.vankien.dating.controllers.ProfileController;
import com.example.vankien.dating.delegate.ProfileDelegate;
import com.example.vankien.dating.models.Profile;
import com.example.vankien.dating.R;
import com.example.vankien.dating.views.activity.ChangePasswordActivity;
import com.example.vankien.dating.views.activity.EditProfileActivity;
import com.example.vankien.dating.views.activity.FullScreenImageActivity;
import com.example.vankien.dating.views.activity.LogInActivity;
import com.example.vankien.dating.views.activity.SettingActivity;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements View.OnClickListener,ProfileDelegate {
    private static final int RESULT_OK = -1;
    private TextView tvName,tvAge,tvAbout,tvAddress,tvRegion,tvNumOfFriend;
    private ImageButton imgBtnEdit,imgBtnLogout;
    private ImageView imgAvatar;
    private Button btnDiscoverySetting, btnChangePassword;
    Profile profile;
    ProfileController controller;
    String id;
    String uriImage;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        initControls(view);
        addEvents();
        loadData();
        return view;
    }


    public void loadData(){
        controller.requestProfile(id);
    }
    public void initControls(View view){
        imgBtnEdit = view.findViewById(R.id.imgBtnEdit);
        imgBtnLogout = view.findViewById(R.id.imgBtnLogout);
        imgAvatar = view.findViewById(R.id.imgAvatar);
        tvName = view.findViewById(R.id.tvName);
        tvAge = view.findViewById(R.id.tvAge);
        tvAbout = (TextView) view.findViewById(R.id.tvAbout);
        tvAddress = view.findViewById(R.id.tvAddress);
        tvRegion = view.findViewById(R.id.tvRegion);
        tvNumOfFriend = view.findViewById(R.id.tvNumOfFriend);
        btnDiscoverySetting = (Button) view.findViewById(R.id.btnDiscoverySetting);
        btnChangePassword = (Button) view.findViewById(R.id.btnChangePassword);

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        controller = ProfileController.getsInstance();
        controller.delegate = this;

    }

    public void addEvents(){
        imgBtnEdit.setOnClickListener(this);
        imgBtnLogout.setOnClickListener(this);
        btnDiscoverySetting.setOnClickListener(this);
        btnChangePassword.setOnClickListener(this);
        imgAvatar.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBtnEdit:
                Intent intentToEdit = new Intent(getActivity(),EditProfileActivity.class);
                intentToEdit.putExtra("isEdit",true);
                if (profile != null) {
                    intentToEdit.putExtra("Profile",profile);
                }
                startActivityForResult(intentToEdit,200);
                break;
            case R.id.imgBtnLogout:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setMessage("Do you want to log out?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (LoginController.getShareInstance().isFacebookLogin()) {
                            LoginManager.getInstance().logOut();
                        }
                        FirebaseAuth.getInstance().signOut();
                        Intent intent = new Intent(getActivity(), LogInActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                builder.create().show();

                break;
            case R.id.btnDiscoverySetting:
                Intent intentToSetting = new Intent(getActivity(),SettingActivity.class);
                startActivity(intentToSetting);
                break;

            case R.id.btnChangePassword:
                if (LoginController.getShareInstance().isFacebookLogin()) {
                    Toast.makeText(getContext(),"Signing with facebook account can't change password",Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(getActivity(), ChangePasswordActivity.class));
                }
                break;

            case R.id.imgAvatar:
                Intent intent = new Intent(getActivity(), FullScreenImageActivity.class);
                intent.putExtra("uriImage",uriImage);
                startActivity(intent);
                break;
        }

    }

    @Override
    public void getProfileSuccess(Profile data) {
        profile = data;
        tvAge.setText(data.getmAge()+"");
        tvNumOfFriend.setText(data.getmNumOfFriends()+"");
        tvName.setText(data.getmName());
        tvAbout.setText(data.getmDescription());
        tvRegion.setText(data.getmRegion());
        tvAddress.setText(data.getmAddress());
        uriImage = data.getmImage();
        Uri uri = Uri.parse(data.getmImage());
        Picasso.with(getContext()).load(uri).into(imgAvatar);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == RESULT_OK){
            controller.requestProfile(id);
        }
    }
}
