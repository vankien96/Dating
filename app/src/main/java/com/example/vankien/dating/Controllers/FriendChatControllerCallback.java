package com.example.vankien.dating.Controllers;

import com.example.vankien.dating.Models.FriendChatModel;

import java.util.ArrayList;

/**
 * Created by vanki on 3/20/2018.
 */

public interface FriendChatControllerCallback {
    void getAllFriendSuccess(ArrayList<FriendChatModel> dataFriends);
    void getFullInformationSuccess(FriendChatModel friendChatModel);
}
