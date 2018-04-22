package com.example.vankien.dating.Controllers;

import android.util.Log;

import com.example.vankien.dating.Interface.FriendChatDelegate;
import com.example.vankien.dating.Interface.MapDelegate;
import com.example.vankien.dating.Models.FriendChatModel;
import com.example.vankien.dating.Models.PeopleAround;
import com.example.vankien.dating.Models.Profile;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vankien on 3/1/18.
 */

public class MapController {
    private static MapController shareInstance = new MapController();
    public MapDelegate delegate;
    ArrayList<PeopleAround> arounds;

    public static MapController getShareInstance(){
        return shareInstance;
    }

    public void requestPeopleAround(final String id){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query mRef = database.getReference().child("Profile");
        arounds = new ArrayList<>();
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                arounds.clear();
                String idUser = FirebaseAuth.getInstance().getCurrentUser().getUid();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if(!snapshot.getKey().equals(idUser)){
                        Log.e("Map screen",snapshot.getKey());
                        HashMap hashMap = (HashMap) snapshot.getValue();
                        PeopleAround model = new PeopleAround();

                        model.setId(snapshot.getKey());
                        model.setAddress((String) hashMap.get("address"));
                        model.setAvatarUrl((String) hashMap.get("avatar"));
                        model.setName((String) hashMap.get("name"));
                        Long age = (Long) hashMap.get("age");
                        String ageString = ""+age;
                        model.setAge(Integer.parseInt(ageString));
                        Long sex = (Long) hashMap.get("sex");
                        String sexString = ""+sex;
                        model.setGender(Integer.parseInt(sexString));

                        Double latitude = (Double) hashMap.get("latitude");
                        String latString = ""+latitude;
                        float lat = Float.parseFloat(latString);
                        Double longitude = (Double) hashMap.get("longitude");
                        String longString = ""+longitude;
                        float lon = Float.parseFloat(longString);

                        LatLng latLng = new LatLng(lat,lon);
                        model.setAddressLatLng(latLng);
                        arounds.add(model);
                    }
                }
                String anotherId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                filterPeople(anotherId);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void filterPeople(String id) {
        FriendChatController.getInstance().getAllFriend(id, new FriendChatDelegate() {
            @Override
            public void getAllFriendSuccess(ArrayList<FriendChatModel> dataFriends) {
                ArrayList<PeopleAround> aroundArrayList = (ArrayList<PeopleAround>) arounds.clone();
                for(PeopleAround peo: arounds) {
                    for(FriendChatModel model: dataFriends) {
                        if (peo.getId().equals(model.getId())) {
                            aroundArrayList.remove(peo);
                            break;
                        }
                    }
                }
                if (delegate != null) {
                    delegate.getAroundPeopleSuccess(aroundArrayList);
                }
            }

            @Override
            public void getFullInformationSuccess(FriendChatModel friendChatModel) {

            }
        });
    }

}
