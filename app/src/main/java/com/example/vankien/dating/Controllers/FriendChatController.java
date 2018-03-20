package com.example.vankien.dating.Controllers;

import com.example.vankien.dating.Models.FriendChatModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vanki on 3/6/2018.
 */

public class FriendChatController {
    private static FriendChatController shareInstance = new FriendChatController();
    public static FriendChatControllerCallback callback;

    public static FriendChatController getInstance( ) {
        return shareInstance;
    }


    public ArrayList<FriendChatModel> getExampleData(){
        ArrayList<FriendChatModel> arrayList = new ArrayList<>();


        arrayList.add(new FriendChatModel("Trương Văn Kiên","https://ombink.files.wordpress.com/2013/01/artworks-000024414007-6rlgdh-crop.jpg?w=700","Hello cưng","7cS7e8ihSrMvLgGBC5AepbOI1G13"));
        arrayList.add(new FriendChatModel("Trần Thông Thành Luân","https://znews-photo-td.zadn.vn/w660/Uploaded/ohunua2/2018_02_24/14504835_310918849274454_2855615938546368512_n.jpg","Em học CNTT phải không?","DINvcEFdWHeU9Gi929lMUoy0SFF3"));
        arrayList.add(new FriendChatModel("Lưu Ngọc Lan","http://img1.blogtamsu.vn/2017/07/linh-ka-1-blogtamsu1.jpg","Tối nay đi chơi nhé em","lu55hzmDx1cICJ45CAvEh7se3gr1"));
        arrayList.add(new FriendChatModel("Trần Đức Long","https://sv.1phut.mobi/uploads/2017/01/rong-pika-hai-phong-1.jpg","Bạn ơi sao bạn mập dzậy!!!","Uo1qv5GWXgWAojpjNcGL56lRnXy1"));

        return arrayList;
    }

    public void getAllFriend(String id){
        final ArrayList<FriendChatModel> arrayList = new ArrayList<>();
        FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
        mDatabase.getReference().child("Friend").child(id).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arrayList.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    FriendChatModel friendChatModel = new FriendChatModel();
                    friendChatModel.setId(snapshot.getKey());
                    friendChatModel.setName("");
                    friendChatModel.setUrlAvatar("");
                    friendChatModel.setRecentMessage((String) snapshot.getValue());
                    arrayList.add(friendChatModel);
                }
                callback.getAllFriendSuccess(arrayList);
                getAllInformation(arrayList);
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
                    callback.getFullInformationSuccess(item);
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
    }
}
