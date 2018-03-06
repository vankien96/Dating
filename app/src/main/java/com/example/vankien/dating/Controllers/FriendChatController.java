package com.example.vankien.dating.Controllers;

import com.example.vankien.dating.Models.FriendChatModel;

import java.util.ArrayList;

/**
 * Created by vanki on 3/6/2018.
 */

public class FriendChatController {
    private static FriendChatController shareInstance = new FriendChatController();

    public static FriendChatController getInstance( ) {
        return shareInstance;
    }


    public ArrayList<FriendChatModel> getExampleData(){
        ArrayList<FriendChatModel> arrayList = new ArrayList<>();


        arrayList.add(new FriendChatModel("Trương Văn Kiên","https://ombink.files.wordpress.com/2013/01/artworks-000024414007-6rlgdh-crop.jpg?w=700","Hello cưng"));
        arrayList.add(new FriendChatModel("Trần Thông Thành Luân","https://znews-photo-td.zadn.vn/w660/Uploaded/ohunua2/2018_02_24/14504835_310918849274454_2855615938546368512_n.jpg","Em học CNTT phải không?"));
        arrayList.add(new FriendChatModel("Lưu Ngọc Lan","http://img1.blogtamsu.vn/2017/07/linh-ka-1-blogtamsu1.jpg","Tối nay đi chơi nhé em"));
        arrayList.add(new FriendChatModel("Trần Đức Long","https://sv.1phut.mobi/uploads/2017/01/rong-pika-hai-phong-1.jpg","Bạn ơi sao bạn mập dzậy!!!"));

        return arrayList;
    }
}
