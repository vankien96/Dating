package com.example.vankien.dating.models;

import java.io.Serializable;

/**
 * Created by vanki on 3/3/2018.
 */

public class FriendChatModel implements Serializable{
    private String name;
    private String id;
    private String urlAvatar;
    private String recentMessage;
    private String type;

    public String getId(){
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrlAvatar() {
        return urlAvatar;
    }

    public void setUrlAvatar(String urlAvatar) {
        this.urlAvatar = urlAvatar;
    }

    public String getRecentMessage() {
        return recentMessage;
    }

    public void setRecentMessage(String recentMessage) {
        this.recentMessage = recentMessage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public FriendChatModel(String name, String urlAvatar, String recentMessage, String id, String type) {
        this.name = name;
        this.urlAvatar = urlAvatar;
        this.recentMessage = recentMessage;
        this.id = id;
        this.type = type;
    }

    public FriendChatModel() {

    }
}
