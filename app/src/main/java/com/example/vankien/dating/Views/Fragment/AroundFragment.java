package com.example.vankien.dating.Views.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.vankien.dating.Controllers.MapController;
import com.example.vankien.dating.Controllers.MapDelegate;
import com.example.vankien.dating.Models.PeopleAround;
import com.example.vankien.dating.R;
import com.example.vankien.dating.Views.Adapter.AroundAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AroundFragment extends Fragment implements MapDelegate {
    ListView listViewAround;
    ArrayList<PeopleAround> aroundModelArrayList;
    AroundAdapter aroundAdapter;
    View rootView;
    MapController controller;
    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_around, container, false);
        addControls();
        aroundModelArrayList = new ArrayList<>();
        aroundAdapter = new AroundAdapter(getActivity(), R.layout.model_around, aroundModelArrayList);
        listViewAround.setAdapter(aroundAdapter);
        aroundAdapter.notifyDataSetChanged();

        id = FirebaseAuth.getInstance().getCurrentUser().getUid();

        addEvents();
        return rootView;
    }

    @Override
    public void setMenuVisibility(final boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        controller = MapController.getShareInstance();
        if (menuVisible){
            controller.delegate = this;
            requestData();
        }else{
            controller.delegate = null;
        }
    }

    private void requestData() {
        controller.requestPeopleAround(id);
    }

    private void addControls() {
        listViewAround = rootView.findViewById(R.id.lvAround);
    }

    private void addEvents() {

    }

    @Override
    public void getAroundPeopleSuccess(ArrayList<PeopleAround> peopleArounds) {
        Log.e("Around Screen","delegate");
        this.aroundModelArrayList.clear();
        this.aroundModelArrayList.addAll(peopleArounds);
        aroundAdapter.notifyDataSetChanged();
    }
}
