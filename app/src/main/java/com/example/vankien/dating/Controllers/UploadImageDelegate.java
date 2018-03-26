package com.example.vankien.dating.Controllers;

/**
 * Created by vanki on 3/22/2018.
 */

public interface UploadImageDelegate {
    void uploadImageSuccess(String avatarUrl);
    void uploadImageFailed();
}
