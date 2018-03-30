package com.example.vankien.dating.Controllers;

import android.app.Activity;
import android.util.Log;

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

//    public ArrayList<MessageModel> getExampleData(){
//        ArrayList<MessageModel> arrayList = new ArrayList<>();
//
//        arrayList.add(new MessageModel(true,"Hello em"));
//        arrayList.add(new MessageModel(false,"Dạ chào anh"));
//        arrayList.add(new MessageModel(true,"Cho anh làm quen nhé"));
//        arrayList.add(new MessageModel(false,"Anh là ai"));
//        arrayList.add(new MessageModel(false,"Tôi không biết"));
//        arrayList.add(new MessageModel(false,"Anh đi ra đi"));
//        arrayList.add(new MessageModel(true,"Đm em"));
//        arrayList.add(new MessageModel(false,"Khi mùa thu theo ta về trước cổng Có phải đã đến lúc ta chọn cho mình con đường để dệt ước mộng"));
//        arrayList.add(new MessageModel(true,"Khi mùa thu theo ta về trước cổng Có phải đã đến lúc ta chọn cho mình con đường để dệt ước mộng"));
//        return arrayList;
//    }
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
        listener = mRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                MessageModel messageModel = new MessageModel();
                HashMap mapMessage = (HashMap) dataSnapshot.getValue();
                String idSender = (String) mapMessage.get("idSender");
                String message = (String) mapMessage.get("message");
                Log.e("Message screen",idSender+" "+message);
                if (idSender != null && message != null){
                    if(idSender.equals(idMe)){
                        messageModel.setMe(true);
                    }else{
                        messageModel.setMe(false);
                    }
                    messageModel.setMessage(message);
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

    public void sendMessage(String idUser, String idFriend,String message){
        String roomID = this.createRoomID(idUser,idFriend);
        Date current = new Date();
        String dateString = TimeUtils.getShareInstance().getDateString(current);
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference().child("Chat").child(roomID).child(dateString);

        HashMap hashMap = new HashMap();
        hashMap.put("idSender",idUser);
        hashMap.put("message",message);
        myRef.setValue(hashMap);

        database.getReference().child("Friend").child(idUser).child(idFriend).setValue(message);
        database.getReference().child("Friend").child(idFriend).child(idUser).setValue(message);
    }
}
