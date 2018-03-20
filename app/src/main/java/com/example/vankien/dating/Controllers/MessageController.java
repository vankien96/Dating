package com.example.vankien.dating.Controllers;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;

import com.example.vankien.dating.Models.FriendChatModel;
import com.example.vankien.dating.Models.MessageModel;
import com.example.vankien.dating.Utils.TimeUtils;
import com.example.vankien.dating.Views.Activity.ChatActivity;
import com.example.vankien.dating.Views.Activity.DangNhapActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class MessageController {
    private static MessageController shareInstance = new MessageController();
    public static MessageControllerCallback callback;

    public static MessageController getInstance( ) {
        return shareInstance;
    }

    public ArrayList<MessageModel> getExampleData(){
        ArrayList<MessageModel> arrayList = new ArrayList<>();

        arrayList.add(new MessageModel(true,"Hello em"));
        arrayList.add(new MessageModel(false,"Dạ chào anh"));
        arrayList.add(new MessageModel(true,"Cho anh làm quen nhé"));
        arrayList.add(new MessageModel(false,"Anh là ai"));
        arrayList.add(new MessageModel(false,"Tôi không biết"));
        arrayList.add(new MessageModel(false,"Anh đi ra đi"));
        arrayList.add(new MessageModel(true,"Đm em"));
        arrayList.add(new MessageModel(false,"Khi mùa thu theo ta về trước cổng Có phải đã đến lúc ta chọn cho mình con đường để dệt ước mộng"));
        arrayList.add(new MessageModel(true,"Khi mùa thu theo ta về trước cổng Có phải đã đến lúc ta chọn cho mình con đường để dệt ước mộng"));
        return arrayList;
    }

    public void requestMessage(Activity activity, final String idFriend, final String idMe) {
        final ArrayList<MessageModel> arrayList = new ArrayList<>();
        Log.e("Message screen", "request");
        String roomID = this.createRoomID(idMe, idFriend);
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference mRef = mDatabase.getReference().child("Chat").child(roomID);
        mRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.e("Message screen", "onDataChange" + dataSnapshot.getKey());
                Log.e("Message screen", "length "+dataSnapshot.getChildrenCount());
                if (idFriend != null && idMe != null){
                    arrayList.clear();
                    if (dataSnapshot.exists()) {
                        Log.e("Message screen","exists");
                        for(DataSnapshot d : dataSnapshot.getChildren()) {
                            MessageModel messageModel = new MessageModel();
                            HashMap mapMessage = (HashMap) d.getValue();
                            String idSender = (String) mapMessage.get("idSender");
                            String message = (String) mapMessage.get("message");
                            Log.e("Message screen",idSender+" "+message);
                            if(idSender.equals(idMe)){
                                messageModel.setMe(true);
                            }else{
                                messageModel.setMe(false);
                            }
                            messageModel.setMessage(message);
                            arrayList.add(messageModel);
                        }
                        callback.getAllMessageSuccess(arrayList);
                    }
                }
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

    public void sendMessage(String idUser, String idFriend,String message){
        String roomID = this.createRoomID(idUser,idFriend);
        Date current = new Date();
        String dateString = TimeUtils.getShareInstance().getDateString(current);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Chat").child(roomID).child(dateString);

        DatabaseReference idSenderRef = myRef.child("idSender");
        idSenderRef.setValue(idUser);
        DatabaseReference messageRef = myRef.child("message");
        messageRef.setValue(message);

        database.getReference().child("Friend").child(idUser).child(idFriend).setValue(message);
        database.getReference().child("Friend").child(idFriend).child(idUser).setValue(message);
    }
}
