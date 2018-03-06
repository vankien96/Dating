package com.example.vankien.dating.Models;

/**
 * Created by vanki on 3/3/2018.
 */

public class FriendChatModel {
    private String name;
    private String urlAvatar;
    private String recentMessage;

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

    public FriendChatModel(String name, String urlAvatar, String recentMessage) {
        this.name = name;
        this.urlAvatar = urlAvatar;
        this.recentMessage = recentMessage;
    }
}
