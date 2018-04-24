package com.example.vankien.dating.Controllers;

import android.app.Activity;
import android.util.Log;
import android.webkit.URLUtil;

import com.example.vankien.dating.Interface.CheckFriendDelegate;
import com.example.vankien.dating.Interface.MessageDelegate;
import com.example.vankien.dating.Models.Constant;
import com.example.vankien.dating.Models.MessageModel;
import com.example.vankien.dating.Utils.TimeUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;
import java.util.HashMap;

public class MessageController {
    private static MessageController shareInstance = new MessageController();
    public MessageDelegate delegate;
    private ChildEventListener listener;
    private DatabaseReference mRef;
    public static MessageController getInstance( ) {
        return shareInstance;
    }

    private final int pageSize = 20;

    public void removeListener(){
        if (listener != null && mRef != null){
            mRef.removeEventListener(listener);
        }
    }

    public void requestMessage(Activity activity, final String idFriend, final String idMe) {
        Log.e("Message screen", "request");
        String roomID = this.createRoomID(idMe, idFriend);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("Chat").child(roomID);
        listener = mRef.orderByKey().addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageModel messageModel = new MessageModel();
                HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                String idSender = (String) mapMessage.get("idSender");
                String message = (String) mapMessage.get("message");
                String type = (String) mapMessage.get("type");
                Log.e("Message screen",idSender+" "+message);
                if (idSender != null && message != null){
                    if(idSender.equals(idMe)){
                        messageModel.setMe(true);
                    }else{
                        messageModel.setMe(false);
                    }
                    messageModel.setMessage(message);
                    messageModel.setType(type);
                    if (delegate != null){
                        delegate.newMessageAdded(messageModel);
                    }
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public String createRoomID(String idUser, String idFriend){
        String roomID = "";
        if(idUser.compareTo(idFriend) < 0){
            roomID = idUser+"_"+idFriend;
        }else{
            roomID = idFriend+"_"+idUser;
        }
        return roomID;
    }

    public void sendMessage(final String idUser, final String idFriend, final String message, String type){
        String roomID = this.createRoomID(idUser,idFriend);
        Date current = new Date();
        String dateString = TimeUtils.getShareInstance().getDateString(current);
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Chat").child(roomID).child(dateString);

        HashMap hashMap = new HashMap();
        hashMap.put("idSender",idUser);
        hashMap.put("message",message);
        hashMap.put("type",type);
        myRef.setValue(hashMap);
        if (Constant.typeImage.equals(type)) {
            database.getReference().child("Friend").child(idUser).child(idFriend).child("recentMessage").setValue("Image");
            database.getReference().child("Friend").child(idFriend).child(idUser).child("recentMessage").setValue("Image");
        } else {
            database.getReference().child("Friend").child(idUser).child(idFriend).child("recentMessage").setValue(message);
            database.getReference().child("Friend").child(idFriend).child(idUser).child("recentMessage").setValue(message);
        }
        FriendChatController.getInstance().checkFriend(idUser, idFriend, new CheckFriendDelegate() {
            @Override
            public void checkFriendSuccess(boolean isFriend) {
                if (!isFriend) {
                    database.getReference().child("Friend").child(idUser).child(idFriend).child("type").setValue(Constant.friend);
                    database.getReference().child("Friend").child(idFriend).child(idUser).child("type").setValue(Constant.stranger);
                } else {
                    database.getReference().child("Friend").child(idUser).child(idFriend).child("type").setValue(Constant.friend);
                    database.getReference().child("Friend").child(idFriend).child(idUser).child("type").setValue(Constant.friend);
                }
            }
        });
    }
}
