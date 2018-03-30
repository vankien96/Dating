package com.example.vankien.dating.Controllers;

import android.util.Log;

import com.example.vankien.dating.Models.PeopleAround;
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

    public static MapController getShareInstance(){
        return shareInstance;
    }

    public void requestPeopleAround(final String id){
        final FirebaseDatabase database = FirebaseDatabase.getInstance();
        Query mRef = database.getReference().child("Profile");
        final ArrayList<PeopleAround> arounds = new ArrayList<>();
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
                if (delegate != null){
                    delegate.getAroundPeopleSuccess(arounds);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
