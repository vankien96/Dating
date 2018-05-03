package com.example.vankien.dating.models;

/**
 * Created by vanki on 3/8/2018.
 */
public class MessageModel {
    private Boolean isMe;
    private String message;
    private String type;

    public MessageModel(Boolean isMe, String message,String type) {
        this.isMe = isMe;
        this.message = message;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public MessageModel() {}

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
