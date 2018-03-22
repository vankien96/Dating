package com.example.vankien.dating.Controllers;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.vankien.dating.Models.PeopleAround;
import com.example.vankien.dating.R;
import com.example.vankien.dating.Models.AroundModel;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by vankien on 3/1/18.
 */

public class MapController {
    private static MapController shareInstance = new MapController();
    public static MapControllerCallback callback;

    public static MapController getShareInstance(){
        return shareInstance;
    }

    public void requestPeopleAround(final String id){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mRef = database.getReference().child("Profile");
        final ArrayList<PeopleAround> arounds = new ArrayList<>();
        mRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NewApi")
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                arounds.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    if(!snapshot.getKey().toString().equals(id)){
                        HashMap hashMap = (HashMap) snapshot.getValue();
                        PeopleAround model = new PeopleAround();

                        model.setId(dataSnapshot.getKey());
                        model.setAddress((String) hashMap.get("address"));
                        model.setAvatarUrl((String) hashMap.get("avatar"));
                        model.setName((String) hashMap.get("name"));
                        Long age = (Long) hashMap.get("age");
                        String ageString = ""+age;
                        model.setAge(Integer.parseInt(ageString));
                        Log.e("Map Screen",ageString);
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
                callback.getAroundPeopleSuccess(arounds);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
