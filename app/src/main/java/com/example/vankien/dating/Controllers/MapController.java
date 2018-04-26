package com.example.vankien.dating.Controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.vankien.dating.Interface.FriendChatDelegate;
import com.example.vankien.dating.Interface.MapDelegate;
import com.example.vankien.dating.Models.FriendChatModel;
import com.example.vankien.dating.Models.PeopleAround;
import com.example.vankien.dating.Models.Profile;
import com.facebook.share.Share;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by vankien on 3/1/18.
 */

public class MapController {
    private static MapController shareInstance = new MapController();
    public MapDelegate delegate;
    ArrayList<PeopleAround> arounds;
    public static Context context;

    public static MapController getShareInstance(){
        return shareInstance;
    }

    public void requestPeopleAround(final String id, final boolean isMap){
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
                        PeopleAround model;
                        if (hashMap.get("invisible") != null) {
                            if ((boolean) hashMap.get("invisible") ) {
                                model = createProfileWithHashMap(snapshot);
                                arounds.add(model);
                            }
                        } else {
                            model = createProfileWithHashMap(snapshot);
                            arounds.add(model);
                        }
                    }
                }
                arounds = filterPeopleBySetting(arounds);
                String anotherId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                if (isMap) {
                    if (delegate != null) {
                        delegate.getAroundPeopleSuccess(arounds);
                    }
                } else {
                    filterPeopleIsFriend(anotherId);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private ArrayList<PeopleAround> filterPeopleBySetting(ArrayList<PeopleAround> peopleArounds) {
        ArrayList<PeopleAround> filters = new ArrayList<>();
        SharedPreferences sharedPreferences = context.getSharedPreferences("MySetting",MODE_PRIVATE);
        boolean isLookingMen = sharedPreferences.getBoolean("isLookingMen",false);
        boolean isLookingWomen = sharedPreferences.getBoolean("isLookingWomen",false);
        int ageFrom =sharedPreferences.getInt("age_from",0);
        int ageTo = sharedPreferences.getInt("age_to",100);
        for (PeopleAround people: peopleArounds) {
            if (people.getAge() > ageTo || people.getAge() < ageFrom) {
                continue;
            }
            if (!isLookingMen) {
                if (people.getGender() == 1) {
                    continue;
                }
            }
            if (!isLookingWomen) {
                if (people.getGender() == 0) {
                    continue;
                }
            }
            filters.add(people);
        }
        return filters;
    }

    private PeopleAround createProfileWithHashMap(DataSnapshot snapshot) {
        PeopleAround model = new PeopleAround();
        HashMap hashMap = (HashMap) snapshot.getValue();
        model.setId(snapshot.getKey());
        model.setAddress((String) hashMap.get("address"));
        model.setAvatarUrl((String) hashMap.get("avatar"));
        model.setName((String) hashMap.get("name"));
        Long age = (Long) hashMap.get("age");
        String ageString = ""+age;
        int ageInt = 0;
        try {
            ageInt = Integer.parseInt(ageString);
        } catch (Exception ex) {

        }
        model.setAge(ageInt);
        Long sex = (Long) hashMap.get("sex");
        String sexString = ""+sex;
        int sexInt = 0;
        try {
            sexInt = Integer.parseInt(sexString);
        } catch (Exception ex) {

        }
        model.setGender(sexInt);

        Double latitude = (Double) hashMap.get("latitude");
        String latString = ""+latitude;
        float lat = Float.parseFloat(latString);
        Double longitude = (Double) hashMap.get("longitude");
        String longString = ""+longitude;
        float lon = Float.parseFloat(longString);

        LatLng latLng = new LatLng(lat,lon);
        model.setAddressLatLng(latLng);
        return model;
    }

    private void filterPeopleIsFriend(String id) {
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
