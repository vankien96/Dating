package com.example.vankien.dating.Controllers;

import com.example.vankien.dating.Interface.BlockDelegate;
import com.example.vankien.dating.Models.Constant;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
}
