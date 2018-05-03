package com.example.vankien.dating.delegate;

import com.example.vankien.dating.models.FriendChatModel;

import java.util.ArrayList;

/**
 * Created by vanki on 3/20/2018.
 */

public interface FriendChatDelegate {
    void getAllFriendSuccess(ArrayList<FriendChatModel> dataFriends);
    void getFullInformationSuccess(FriendChatModel friendChatModel);
}
