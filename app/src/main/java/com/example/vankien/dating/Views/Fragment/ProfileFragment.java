package com.example.vankien.dating.Views.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vankien.dating.Controllers.ProfileController;
import com.example.vankien.dating.Controllers.ProfileDelegate;
import com.example.vankien.dating.Models.Profile;
import com.example.vankien.dating.R;
import com.example.vankien.dating.Views.Activity.EditProfileActivity;
import com.example.vankien.dating.Views.Activity.LogInActivity;
import com.example.vankien.dating.Views.Activity.SettingActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

public class ProfileFragment extends Fragment implements View.OnClickListener,ProfileDelegate {
    private TextView tvName,tvAge,tvAbout,tvAddress,tvRegion,tvNumOfFriend;
    private ImageButton imgBtnEdit,imgBtnLogout;
    private ImageView imgAvatar;
    private Button btnDiscoverySetting;
    Profile profile;
    ProfileController controller;
    String id;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        controller = ProfileController.getsInstance();
        controller.callback = this;
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
        profile = ProfileController.getsInstance().getProfile();

    }

    public void addEvents(){
        imgBtnEdit.setOnClickListener(this);
        imgBtnLogout.setOnClickListener(this);
        btnDiscoverySetting.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBtnEdit:
                Intent intentToEdit = new Intent(getActivity(),EditProfileActivity.class);
                startActivityForResult(intentToEdit,1000);
                break;
            case R.id.imgBtnLogout:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setCancelable(false);
                builder.setMessage("Do you want to log out?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
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
        }

    }

    @Override
    public void getProfileSuccess(Profile data) {
        Log.e("Profile Screen","delegate");
        tvAge.setText(data.getmAge()+"");
        tvNumOfFriend.setText(data.getmNumOfFriends()+"");
        tvName.setText(data.getmName());
        tvAbout.setText(data.getmDescription());
        tvRegion.setText(data.getmRegion());
        tvAddress.setText(data.getmAddress());
        Uri uri = Uri.parse(data.getmImage());
        Picasso.with(getContext()).load(uri).into(imgAvatar);
    }
}
