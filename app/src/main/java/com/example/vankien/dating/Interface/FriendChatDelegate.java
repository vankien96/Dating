package com.example.vankien.dating.Interface;

import com.example.vankien.dating.Models.FriendChatModel;

import java.util.ArrayList;

/**
 * Created by vanki on 3/20/2018.
 */

public interface FriendChatDelegate {
    void getAllFriendSuccess(ArrayList<FriendChatModel> dataFriends);
    void getFullInformationSuccess(FriendChatModel friendChatModel);
}
