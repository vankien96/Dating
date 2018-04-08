package com.example.vankien.dating.Controllers;

import com.example.vankien.dating.Models.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

/**
 * Created by Luu Ngoc Lan on 11-Mar-18.
 */

public class ProfileController {
    private static ProfileController sInstance = new ProfileController();
    public ProfileDelegate delegate;
    public static ProfileController getsInstance (){ return sInstance;}

    public Profile getProfile(){
        Profile profile = new Profile();
        return profile;
    }

    public void requestProfile(String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference().child("Profile").child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    Profile profile = new Profile();
                    HashMap data = (HashMap) dataSnapshot.getValue();
                    if (data != null) {
                        String about = (String) data.get("about");
                        String address = (String) data.get("address");
                        Long age = (Long) data.get("age");
                        String ageString = age+"";
                        String avatar = (String) data.get("avatar");
                        String name = (String) data.get("name");
                        Long numOfFriend = (Long) data.get("numberOfFriend");
                        String numOfFriendString = numOfFriend+"";

                        String region = (String) data.get("region");
                        Long sex = (Long) data.get("sex");
                        String sexString = sex+"";

                        profile.setmAge(Integer.parseInt(ageString));
                        profile.setmDescription(about);
                        profile.setmImage(avatar);

                        profile.setmName(name);
                        profile.setmRegion(region);
                        profile.setmNumOfFriends(Integer.parseInt(numOfFriendString));
                        profile.setmSex(Integer.parseInt(sexString));
                        profile.setmAddress(address);

                        if(delegate != null){
                            delegate.getProfileSuccess(profile);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void uploadProfile(Profile profile,String id){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference reference = database.getReference().child("Profile").child(id);

        reference.child("age").setValue(profile.getmAge());
        reference.child("numberOfFriend").setValue(profile.getmNumOfFriends());
        reference.child("sex").setValue(profile.getmSex());
        if (profile.getmLatitude() != null){
            reference.child("latitude").setValue(profile.getmLatitude());
        } else {
            reference.child("latitude").setValue(0.01);
        }
        if (profile.getmLongitude() != null) {
            reference.child("longitude").setValue(profile.getmLongitude());
        } else {
            reference.child("longitude").setValue(0.01);
        }

        if (profile.getmName() != null) {
            reference.child("name").setValue(profile.getmName());
        }
        if (profile.getmImage() != null) {
            reference.child("avatar").setValue(profile.getmImage());
        }
        if (profile.getmDescription() != null) {
            reference.child("about").setValue(profile.getmDescription());
        }
        if (profile.getmRegion() != null) {
            reference.child("region").setValue(profile.getmRegion());
        }
        if (profile.getmAddress() != null) {
            reference.child("address").setValue(profile.getmAddress());
        }
    }
}
