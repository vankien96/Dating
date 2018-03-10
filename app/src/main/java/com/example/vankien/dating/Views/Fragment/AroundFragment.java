package com.example.vankien.dating.Views.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vankien.dating.Controllers.AroundController;
import com.example.vankien.dating.Controllers.PeopleAroundController;
import com.example.vankien.dating.R;
import com.example.vankien.dating.Views.Activity.DetailActivity;
import com.example.vankien.dating.Views.Activity.MainActivity;
import com.example.vankien.dating.Views.Adapter.AroundAdapter;
import com.example.vankien.dating.model.AroundModel;

import java.util.ArrayList;

public class AroundFragment extends Fragment {
    ListView listViewAround;
    ImageButton btnYes, btnNo;
    ArrayList<AroundModel> aroundModelArrayList;
    AroundAdapter aroundAdapter;
    View rootView;
    AroundController aroundController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_around, container, false);
        addControls();
        aroundAdapter = new AroundAdapter(getActivity(), R.layout.model_around, aroundModelArrayList);
        listViewAround.setAdapter(aroundAdapter);
        aroundAdapter.notifyDataSetChanged();
        addEvents();
        return rootView;


    }

    private void addControls() {
        listViewAround = rootView.findViewById(R.id.lvAround);
        btnYes = rootView.findViewById(R.id.btnYes);
        btnNo = rootView.findViewById(R.id.btnNo);
        aroundController = AroundController.getsInstance();
        aroundModelArrayList = aroundController.getmAroundModel();
    }

    private void addEvents() {

    }
}
