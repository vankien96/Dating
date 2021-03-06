package com.example.vankien.dating.views.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.vankien.dating.controllers.MapController;
import com.example.vankien.dating.delegate.MapDelegate;
import com.example.vankien.dating.models.PeopleAround;
import com.example.vankien.dating.R;
import com.example.vankien.dating.views.adapter.AroundAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class AroundFragment extends Fragment implements MapDelegate {
    ListView listViewAround;
    ArrayList<PeopleAround> aroundModelArrayList;
    AroundAdapter aroundAdapter;
    View rootView;
    MapController controller;
    String id;
    View footerView;
    boolean isloading = false;

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
        controller = MapController.getShareInstance();
        controller.context = getContext();
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

    @Override
    public void onResume() {
        super.onResume();
        requestData();
    }

    private void requestData() {
        controller.requestPeopleAround(id,false);
    }

    private void addControls() {
        listViewAround = rootView.findViewById(R.id.lvAround);
        footerView = LayoutInflater.from(getContext()).inflate(R.layout.footer_view,null);
    }

    private void addEvents() {
        listViewAround.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView absListView, int i) {

            }

            @Override
            public void onScroll(AbsListView absListView, int firstItem, int visibleItemCount, int totalItemCount) {
                if (absListView.getLastVisiblePosition() == totalItemCount - 1 && !isloading) {

                }
            }
        });
    }

    @Override
    public void getAroundPeopleSuccess(ArrayList<PeopleAround> peopleArounds) {
        Log.e("Around Screen","delegate");
        this.aroundModelArrayList.clear();
        this.aroundModelArrayList.addAll(peopleArounds);
        aroundAdapter.notifyDataSetChanged();
    }
}
