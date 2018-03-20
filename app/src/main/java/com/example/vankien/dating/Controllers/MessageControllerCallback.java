package com.example.vankien.dating.Controllers;

import com.example.vankien.dating.Models.MessageModel;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by vanki on 3/8/2018.
 */

public interface MessageControllerCallback{
    void getAllMessageSuccess(ArrayList<MessageModel> messageDatas);
    void newMessageAdded(MessageModel message);
}
