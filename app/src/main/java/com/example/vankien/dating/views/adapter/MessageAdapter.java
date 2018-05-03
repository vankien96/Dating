package com.example.vankien.dating.views.adapter;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.vankien.dating.models.Constant;
import com.example.vankien.dating.models.MessageModel;
import com.example.vankien.dating.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hani.momanii.supernova_emoji_library.Helper.EmojiconTextView;

/**
 * Created by vanki on 3/8/2018.
 */

public class MessageAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private ArrayList<MessageModel> arrMessage;
    private String avatar;
    public MessageAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MessageModel> objects,String avatar) {
        super(context, resource, objects);
        this.context = context;
        this.arrMessage = objects;
        this.resource = resource;
        this.avatar = avatar;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MessageModel messageModel = arrMessage.get(position);

        final MessageHolder viewHolder;
        if (convertView == null){
            viewHolder = new MessageHolder();
            if(messageModel.getMe()){
                if (Constant.typeImage.equals(messageModel.getType())) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.cell_message_img_right,null);
                    viewHolder.image = convertView.findViewById(R.id.img_chat);
                } else {
                    convertView = LayoutInflater.from(context).inflate(R.layout.cell_my_message,null);
                    viewHolder.txtMessage = convertView.findViewById(R.id.txtMessage);
                }
            }else{
                if (Constant.typeImage.equals(messageModel.getType())) {
                    convertView = LayoutInflater.from(context).inflate(R.layout.cell_message_img_left,null);
                    viewHolder.imgAvatar = convertView.findViewById(R.id.imgAvatar);
                    viewHolder.image = convertView.findViewById(R.id.img_chat);
                } else {
                    convertView = LayoutInflater.from(context).inflate(R.layout.cell_receive_message,null);
                    viewHolder.imgAvatar = convertView.findViewById(R.id.imgAvatar);
                    viewHolder.txtMessage = convertView.findViewById(R.id.txtMessage);
                }
            }
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (MessageHolder) convertView.getTag();
        }
        if(!messageModel.getMe()){
            if(position+1 < arrMessage.size()){
                MessageModel nextMessage = arrMessage.get(position+1);
                if (nextMessage.getMe() == messageModel.getMe()){
                    viewHolder.imgAvatar.setVisibility(View.INVISIBLE);
                }else{
                    viewHolder.imgAvatar.setVisibility(View.VISIBLE);
                }
            }
            Glide.with(context)
                    .load(avatar)
                    .into(viewHolder.imgAvatar);
        }
        if (Constant.typeImage.equals(messageModel.getType())) {
            Uri uri = Uri.parse(messageModel.getMessage());
            Picasso.with(context).load(uri).into(viewHolder.image);
        } else {
            viewHolder.txtMessage.setEmojiconSize(80);
            viewHolder.txtMessage.setText(messageModel.getMessage());
        }
        return convertView;
    }

    public class MessageHolder {
        ImageView imgAvatar;
        EmojiconTextView txtMessage;
        ImageView image;
    }

    @Override
    public int getViewTypeCount() {
        return 4;
    }

    @Override
    public int getItemViewType(int position) {
        MessageModel messageModel = arrMessage.get(position);
        if(messageModel.getMe()){
            if (Constant.typeImage.equals(messageModel.getType())) {
                return 0;
            } else {
                return 1;
            }
        } else {
            if (Constant.typeImage.equals(messageModel.getType())) {
                return 2;
            } else {
                return 3;
            }
        }
    }
}
