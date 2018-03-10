package com.example.vankien.dating.Controllers;

import com.example.vankien.dating.Models.FriendChatModel;
import com.example.vankien.dating.Models.MessageModel;

import java.util.ArrayList;

/**
 * Created by vanki on 3/8/2018.
 */

public class MessageController {
    private static MessageController shareInstance = new MessageController();

    public static MessageController getInstance( ) {
        return shareInstance;
    }

    public ArrayList<MessageModel> getExampleData(){
        ArrayList<MessageModel> arrayList = new ArrayList<>();

        arrayList.add(new MessageModel(true,"Hello em"));
        arrayList.add(new MessageModel(false,"Dạ chào anh"));
        arrayList.add(new MessageModel(true,"Cho anh làm quen nhé"));
        arrayList.add(new MessageModel(false,"Anh là ai"));
        arrayList.add(new MessageModel(false,"Tôi không biết"));
        arrayList.add(new MessageModel(false,"Anh đi ra đi"));
        arrayList.add(new MessageModel(true,"Đm em"));
        return arrayList;
    }
}
