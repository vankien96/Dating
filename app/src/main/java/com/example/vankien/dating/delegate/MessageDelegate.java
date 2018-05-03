package com.example.vankien.dating.delegate;

import com.example.vankien.dating.models.MessageModel;

import java.util.ArrayList;

/**
 * Created by vanki on 3/8/2018.
 */

public interface MessageDelegate {
    void getAllMessageSuccess(ArrayList<MessageModel> messageDatas);
    void newMessageAdded(MessageModel message);
}
