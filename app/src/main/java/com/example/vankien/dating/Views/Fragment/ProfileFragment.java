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

import com.example.vankien.dating.R;
import com.example.vankien.dating.Views.Activity.EditProfileActivity;

public class ProfileFragment extends Fragment implements View.OnClickListener {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile,container,false);
        ImageButton imgBtnEdit = (ImageButton) view.findViewById(R.id.imgBtnEdit);
        ImageButton imgBtnSetting = (ImageButton) view.findViewById(R.id.imgBtnSetting);
        imgBtnEdit.setOnClickListener(this);
        imgBtnSetting.setOnClickListener(this);
        return view;
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
