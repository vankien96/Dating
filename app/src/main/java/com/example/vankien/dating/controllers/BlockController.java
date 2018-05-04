package com.example.vankien.dating.controllers;

import com.example.vankien.dating.delegate.BlockDelegate;
import com.example.vankien.dating.models.Constant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by vanki on 4/21/2018.
 */

public class BlockController {
    private static BlockController shareInstance = new BlockController();
    public BlockDelegate delegate;
    public static BlockController getShareInstance(){
        return shareInstance;
    }
    private DatabaseReference mRef;
    private ValueEventListener listener;

    public void blockPeople(String id) {
        String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("Friend").child(myId).child(id).child("type").setValue(Constant.block);
        mRef.child("Friend").child(id).child(myId).child("type").setValue(Constant.blocked);
    }

    public void unblock(String id) {
        String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("Friend").child(myId).child(id).child("type").setValue(Constant.friend);
        mRef.child("Friend").child(id).child(myId).child("type").setValue(Constant.friend);
    }

    public void checkBlock(String id, final BlockDelegate delegate) {
        String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRef = FirebaseDatabase.getInstance().getReference();
        mRef.child("Friend").child(myId).child(id).child("type").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String type = (String) dataSnapshot.getValue();
                delegate.checkBlock(type);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void rejectPeople(String id) {
        String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference().child("Reject").child(myId).child(id).setValue("rejected");
    }

    public void getRejectList(final BlockDelegate delegate) {
        String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference()
                .child("Reject").child(myId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<String> rejected = new ArrayList<>();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()) {
                    String id = snapshot.getKey();
                    rejected.add(id);
                }
                delegate.getRejectListSuccess(rejected);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void checkBlockListener(String id, final BlockDelegate delegate) {
        String myId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mRef = FirebaseDatabase.getInstance().getReference();
        listener = mRef.child("Friend").child(myId).child(id).child("type").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String type = (String) dataSnapshot.getValue();
                delegate.checkBlock(type);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void removeListener() {
        if(mRef != null && listener != null){
            mRef.removeEventListener(listener);
        }
    }
}
