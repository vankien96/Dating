package com.example.vankien.dating.Utils;

import android.graphics.Bitmap;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.vankien.dating.Interface.UploadImageDelegate;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.Date;

/**
 * Created by vanki on 3/11/2018.
 */

public class FirebaseUtils {
    private static FirebaseUtils shareInstance = new FirebaseUtils();
    public static UploadImageDelegate delegate;
    public static FirebaseUtils getShareInstance(){
        return shareInstance;
    }


    public void uploadAvatar(Bitmap bitmap, final String id){
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference().child("avatar").child(id+".jpg");

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle unsuccessful uploads
                exception.printStackTrace();
                if (delegate != null){
                    delegate.uploadImageFailed();
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                if (delegate != null) {
                    delegate.uploadImageSuccess(downloadUrl.toString());
                }
            }
        });
    }

    public void uploadImage(Bitmap bitmap, final String id) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference reference = storage.getReference().child("imageChat").child(id+TimeUtils.getShareInstance().getDateString(new Date())+".jpg");


        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = reference.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                exception.printStackTrace();
                if (delegate != null){
                    delegate.uploadImageFailed();
                }
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                if (delegate != null) {
                    delegate.uploadImageSuccess(downloadUrl.toString());
                }
            }
        });
    }
}
