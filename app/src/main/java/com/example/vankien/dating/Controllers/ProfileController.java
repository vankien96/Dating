package com.example.vankien.dating.Controllers;

import com.example.vankien.dating.Models.Profile;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
                Profile profile = new Profile();
                HashMap data = (HashMap) dataSnapshot.getValue();
                String about = (String) data.get("about");
                String address = (String) data.get("address");
                String age = (String) data.get("age");
                String ageString = age+"";
                String avatar = (String) data.get("avatar");
 //               float latitude = (float) data.get("latitude");
//                float longitude = (float) data.get("longitude");
                String name = (String) data.get("name");
                Long numOfFriend = (Long) data.get("numberOfFriend");
                String numOfFriendString = numOfFriend+"";

                String region = (String) data.get("region");
                Long sex = (Long) data.get("sex");
                String sexString = sex+"";

                profile.setmAge(age);
                profile.setmDescription(about);
                profile.setmImage(avatar);

//                profile.setmLatitude(latitude);
//                profile.setmLongitude(longitude);
                profile.setmName(name);
                profile.setmRegion(region);
                profile.setmNumOfFriends(Integer.parseInt(numOfFriendString));
                profile.setmSex(sexString);
                profile.setmAddress(address);

                if(delegate != null){
                    delegate.getProfileSuccess(profile);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }


}
