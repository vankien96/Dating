package com.example.vankien.dating.Models;

/**
 * Created by vanki on 3/8/2018.
 */

public class MessageModel {
    private Boolean isMe;
    private String message;

    public MessageModel(Boolean isMe, String message) {
        this.isMe = isMe;
        this.message = message;
    }

    public Boolean getMe() {
        return isMe;
    }

    public void setMe(Boolean me) {
        isMe = me;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
