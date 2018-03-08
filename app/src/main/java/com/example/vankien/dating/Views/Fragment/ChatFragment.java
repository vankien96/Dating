package com.example.vankien.dating.Views.Fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.vankien.dating.Controllers.FriendChatController;
import com.example.vankien.dating.Models.FriendChatModel;
import com.example.vankien.dating.R;
import com.example.vankien.dating.Views.Activity.MainActivity;
import com.example.vankien.dating.Views.Adapter.ChatActivity;
import com.example.vankien.dating.Views.Adapter.FriendChatAdapter;

import java.util.ArrayList;

public class ChatFragment extends Fragment {
    ListView lvChat;
    FriendChatAdapter adapter;
    ArrayList<FriendChatModel> mFriendChat;
    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView =  inflater.inflate(R.layout.fragment_chat, container, false);
        this.addControls();
        addEvents();

        return rootView;
    }

    private void addEvents() {
        this.lvChat.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                
                Intent intent = new Intent(getActivity(), ChatActivity.class);
                intent.putExtra("Name",mFriendChat.get(i).getName());
                startActivity(intent);
            }
        });
    }

    private void addControls() {
        lvChat = rootView.findViewById(R.id.lvChat);

        mFriendChat = FriendChatController.getInstance().getExampleData();
        adapter = new FriendChatAdapter(getActivity(),R.layout.cell_friend_chat,mFriendChat);
        lvChat.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
