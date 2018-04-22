package com.example.vankien.dating.Views.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.vankien.dating.Controllers.FriendChatController;
import com.example.vankien.dating.Interface.FriendChatDelegate;
import com.example.vankien.dating.Models.Constant;
import com.example.vankien.dating.Models.FriendChatModel;
import com.example.vankien.dating.R;
import com.example.vankien.dating.Views.Activity.ChatActivity;
import com.example.vankien.dating.Views.Adapter.FriendChatAdapter;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class ChatFragment extends Fragment implements FriendChatDelegate {
    ListView lvChat;
    FriendChatAdapter adapter;
    ArrayList<FriendChatModel> mFriendChat;
    View rootView;
    String id;
    FriendChatController controller;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_chat, container, false);
        id = FirebaseAuth.getInstance().getCurrentUser().getUid();
        controller = FriendChatController.getInstance();
        controller.delegate = this;
        this.addControls();
        addEvents();
        requestData();
        return rootView;
    }

    private void requestData() {
        controller.getAllFriend(id);
    }

    private void addEvents() {
        this.lvChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                FriendChatModel model = mFriendChat.get(i);
                if(Constant.blocked.equals(model.getType())) {
                    Toast.makeText(getActivity(),"This user blocked you",Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(getActivity(), ChatActivity.class);
                    Bundle b = new Bundle();
                    if (Constant.block.equals(model.getType())) {
                        b.putBoolean("isBlock",true);
                    }
                    b.putSerializable("FriendData",model);
                    intent.putExtras(b);
                    lvChat.setAdapter(adapter);
                    startActivity(intent);
                }
            }
        });
    }

    private void addControls() {
        lvChat = rootView.findViewById(R.id.lvChat);

        mFriendChat = new ArrayList<>();
        adapter = new FriendChatAdapter(getActivity(),R.layout.cell_friend_chat,mFriendChat);
        lvChat.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getAllFriendSuccess(ArrayList<FriendChatModel> dataFriends) {
        this.mFriendChat.clear();
        this.mFriendChat.addAll(dataFriends);
        Log.e("FriendChat Screen",""+mFriendChat.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void getFullInformationSuccess(FriendChatModel friendChatModel) {
        for (FriendChatModel model: mFriendChat){
            if (model.getId().equals(friendChatModel.getId())){
                model.setName(friendChatModel.getName());
                model.setUrlAvatar(friendChatModel.getUrlAvatar());
                break;
            }
        }
        Log.e("FriendChat Screen",""+mFriendChat.size());
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        controller.delegate = null;
        controller.removeListener();
    }
}