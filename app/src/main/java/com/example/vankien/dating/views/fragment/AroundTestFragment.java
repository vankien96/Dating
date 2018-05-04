package com.example.vankien.dating.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import com.example.vankien.dating.controllers.MapController;
import com.example.vankien.dating.delegate.MapDelegate;
import com.example.vankien.dating.models.PeopleAround;
import com.example.vankien.dating.R;
import com.example.vankien.dating.views.activity.DetailActivity;
import com.example.vankien.dating.views.adapter.AroundAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.yuyakaido.android.cardstackview.CardStackView;
import com.yuyakaido.android.cardstackview.SwipeDirection;

import java.util.ArrayList;

public class AroundTestFragment extends Fragment implements MapDelegate{
    private ProgressBar progressBar;
    private CardStackView cardStackView;
    ArrayList<PeopleAround> aroundModelArrayList;
    MapController controller;
    private AroundAdapter adapter;
    View rootView;
    ImageButton btnReject, btnAccept;
    String id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView =  inflater.inflate(R.layout.fragment_around_test, container, false);
        controller = MapController.getShareInstance();
        controller.delegate = this;
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        addControls();
        addEvents();
        return rootView;
    }

    private void addEvents() {
        btnAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (cardStackView.getTopIndex() < aroundModelArrayList.size()) {
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    PeopleAround people = aroundModelArrayList.get(cardStackView.getTopIndex());
                    intent.putExtra("UserID",people.getId());
                    getActivity().startActivity(intent);
                }
            }
        });
    }

    @Override
    public void setMenuVisibility(final boolean menuVisible) {
        super.setMenuVisibility(menuVisible);
        controller = MapController.getShareInstance();
        Log.e("TAGG",menuVisible+"  ;");
        if (menuVisible){
            controller.delegate = this;
            requestData();
        }else{
            controller.delegate = null;
        }
    }

    private void requestData() {
        controller.requestPeopleAround(id,false);
    }

    private void addControls() {
        progressBar = rootView.findViewById(R.id.activity_main_progress_bar);
        cardStackView = rootView.findViewById(R.id.cardView);
        btnAccept = rootView.findViewById(R.id.btn_accept);
        btnReject = rootView.findViewById(R.id.btn_reject);
        aroundModelArrayList = new ArrayList<>();
        adapter = new AroundAdapter(getActivity(),R.layout.model_around,aroundModelArrayList);
        cardStackView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        cardStackView.setCardEventListener(new CardStackView.CardEventListener() {
            @Override
            public void onCardDragging(float percentX, float percentY) {
            }

            @Override
            public void onCardSwiped(SwipeDirection direction) {
                if (cardStackView.getTopIndex() == adapter.getCount() - 5) {
                    paginate();
                }
            }

            @Override
            public void onCardReversed() {
            }

            @Override
            public void onCardMovedToOrigin() {
            }

            @Override
            public void onCardClicked(int index) {
                Intent intent = new Intent(getActivity(), DetailActivity.class);
                PeopleAround people = aroundModelArrayList.get(index);
                intent.putExtra("UserID",people.getId());
                getActivity().startActivity(intent);
            }
        });
    }

    private void paginate() {
        cardStackView.setPaginationReserved();
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getAroundPeopleSuccess(ArrayList<PeopleAround> peopleArounds) {
        aroundModelArrayList.clear();
        aroundModelArrayList.addAll(peopleArounds);
        reload();
    }

    private void reload() {
        if (aroundModelArrayList.size() > 3) {
            cardStackView.setVisibleCount(3);
        } else {
            cardStackView.setVisibleCount(aroundModelArrayList.size() - 1);
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter = new AroundAdapter(getActivity(),R.layout.model_around,aroundModelArrayList);
                cardStackView.setAdapter(adapter);
                cardStackView.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.GONE);
            }
        }, 1000);
    }
}
