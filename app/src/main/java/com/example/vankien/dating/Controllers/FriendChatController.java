package com.example.vankien.dating.Controllers;

import com.example.vankien.dating.Interface.CheckFriendDelegate;
import com.example.vankien.dating.Interface.FriendChatDelegate;
import com.example.vankien.dating.Models.FriendChatModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vanki on 3/6/2018.
 */

public class FriendChatController {
    private static FriendChatController shareInstance = new FriendChatController();
    public FriendChatDelegate delegate;
    private DatabaseReference mRef;
    private ValueEventListener listener;


    public static FriendChatController getInstance( ) {
        return shareInstance;
    }

    public void removeListener(){
        if(mRef != null && listener != null){
            mRef.removeEventListener(listener);
        }
    }

    public void getAllFriend(String id){
        final ArrayList<FriendChatModel> arrayList = new ArrayList<>();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Friend").child(id);
        listener = mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    FriendChatModel friendChatModel = new FriendChatModel();
                    friendChatModel.setId(snapshot.getKey());
                    friendChatModel.setName("");
                    friendChatModel.setUrlAvatar("");
                    HashMap<String, String> hashMap = (HashMap<String, String>) snapshot.getValue();
                    friendChatModel.setRecentMessage(hashMap.get("recentMessage"));
                    friendChatModel.setType(hashMap.get("type"));
                    arrayList.add(friendChatModel);
                }
                if (delegate != null){
                    delegate.getAllFriendSuccess(arrayList);
                }
                getAllInformation(arrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void getAllFriend(String id, final FriendChatDelegate callback) {
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mDatabase.getReference().child("Friend").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<FriendChatModel> arrayList = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    FriendChatModel friendChatModel = new FriendChatModel();
                    friendChatModel.setId(snapshot.getKey());
                    friendChatModel.setName("");
                    friendChatModel.setUrlAvatar("");
                    HashMap<String, String> hashMap = (HashMap<String, String>) snapshot.getValue();
                    friendChatModel.setRecentMessage(hashMap.get("recentMessage"));
                    friendChatModel.setType(hashMap.get("type"));
                    arrayList.add(friendChatModel);
                }
                callback.getAllFriendSuccess(arrayList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getAllInformation(ArrayList<FriendChatModel> arrayList){
        for(final FriendChatModel item: arrayList){
            String id = item.getId();
            FirebaseDatabase.getInstance().getReference().child("Profile").child(id).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    HashMap data = (HashMap) dataSnapshot.getValue();
                    item.setUrlAvatar((String) data.get("avatar"));
                    item.setName((String) data.get("name"));
                    if (delegate != null){
                        delegate.getFullInformationSuccess(item);
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }

    public void checkFriend(String idMe, final String idUser, final CheckFriendDelegate delegate) {
        FirebaseDatabase.getInstance().getReference().child("Friend").child(idMe).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                delegate.checkFriendSuccess(dataSnapshot.hasChild(idUser));
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
