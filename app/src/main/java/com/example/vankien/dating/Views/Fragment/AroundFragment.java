package com.example.vankien.dating.Views.Fragment;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.vankien.dating.R;
import com.example.vankien.dating.Views.Activity.MainActivity;
import com.example.vankien.dating.Views.Adapter.AroundAdapter;
import com.example.vankien.dating.model.AroundModel;

import java.util.ArrayList;

public class AroundFragment extends Fragment {
    ListView listViewAround ;
    Button btnAccept, btnCancel;
    ArrayList<AroundModel> aroundModelArrayList;
    AroundAdapter aroundAdapter;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView = inflater.inflate(R.layout.fragment_around, container, false);
        addControls();
        fakeData();
        aroundAdapter = new AroundAdapter(getActivity(), R.layout.model_around,aroundModelArrayList);
        listViewAround.setAdapter(aroundAdapter);
        aroundAdapter.notifyDataSetChanged();
        addEvents();
        return rootView;


    }
    private void fakeData(){
        aroundModelArrayList = new ArrayList<>();
        aroundModelArrayList.add(new AroundModel(btnCancel, btnAccept,R.drawable.thuhinh));
        aroundModelArrayList.add(new AroundModel(btnCancel, btnAccept,R.drawable.thuhinh2));
        aroundModelArrayList.add(new AroundModel(btnCancel, btnAccept,R.drawable.thuhinh3));
    }
    private void addControls() {
        listViewAround = rootView.findViewById(R.id.lvAround);
        btnAccept = rootView.findViewById(R.id.btnSee);
        btnCancel = rootView.findViewById(R.id.btnDel);
    }
    private void addEvents() {

    }
}
