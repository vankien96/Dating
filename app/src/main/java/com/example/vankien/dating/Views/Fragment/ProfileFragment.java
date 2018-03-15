package com.example.vankien.dating.Views.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vankien.dating.Controllers.ProfileController;
import com.example.vankien.dating.Models.Profile;
import com.example.vankien.dating.R;
import com.example.vankien.dating.Views.Activity.EditProfileActivity;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    private TextView tvName,tvAge,tvDescription;
    private ImageButton imgBtnEdit,imgBtnSetting;
    private ImageView imgAvatar;
    Profile profile;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        initControls(view);
        loadData();
        addEvents();
        return view;
    }


    public void loadData(){
        tvName.setText(profile.getmName());
        tvAge.setText(String.valueOf(profile.getmAge()));
        tvDescription.setText(profile.getmDescription());
    }
    public void initControls(View view){
        imgBtnEdit = (ImageButton) view.findViewById(R.id.imgBtnEdit);
        imgBtnSetting = (ImageButton) view.findViewById(R.id.imgBtnSetting);
        imgAvatar = (ImageView) view.findViewById(R.id.imgAvatar);
        tvName = (TextView) view.findViewById(R.id.tvName);
        tvAge = (TextView) view.findViewById(R.id.tvAge);
        tvDescription = (TextView) view.findViewById(R.id.tvDescription);
        profile = ProfileController.getsInstance().getProfile();
    }

    public void addEvents(){
        imgBtnEdit.setOnClickListener(this);
        imgBtnSetting.setOnClickListener(this);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.imgBtnEdit:
                Intent intentToEdit = new Intent(getActivity(),EditProfileActivity.class);
                startActivity(intentToEdit);
                break;
            case R.id.imgBtnSetting:
                Intent intentToSetting = new Intent(getActivity(),EditProfileActivity.class);
                startActivity(intentToSetting);
                break;
        }

    }
}
