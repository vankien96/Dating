package com.example.vankien.dating.Views.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vankien.dating.Models.MessageModel;
import com.example.vankien.dating.R;

import java.util.ArrayList;

/**
 * Created by vanki on 3/8/2018.
 */

public class MessageAdapter extends ArrayAdapter {
    private Context context;
    private int resource;
    private ArrayList<MessageModel> arrMessage;
    public MessageAdapter(@NonNull Context context, int resource, @NonNull ArrayList<MessageModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.arrMessage = objects;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        MessageModel messageModel = arrMessage.get(position);

        MessageHolder viewHolder;
        if (convertView == null){
            viewHolder = new MessageHolder();
            if(messageModel.getMe()){
                convertView = LayoutInflater.from(context).inflate(R.layout.cell_my_message,null);
                viewHolder.txtMessage = convertView.findViewById(R.id.txtMessage);
            }else{
                convertView = LayoutInflater.from(context).inflate(R.layout.cell_receive_message,null);
                viewHolder.imgAvatar = convertView.findViewById(R.id.imgAvatar);
                viewHolder.txtMessage = convertView.findViewById(R.id.txtMessage);
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
        }
        viewHolder.txtMessage.setText(messageModel.getMessage());
        return convertView;
    }
    public class MessageHolder {
        ImageView imgAvatar;
        TextView txtMessage;
    }
    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        MessageModel messageModel = arrMessage.get(position);
        if(messageModel.getMe()){
            return 0;
        }else{
            return 1;
        }
    }
}
