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
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.vankien.dating.models.Constant;
import com.example.vankien.dating.models.FriendChatModel;
import com.example.vankien.dating.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by vanki on 3/3/2018.
 */

public class FriendChatAdapter extends ArrayAdapter<FriendChatModel> {
    private Context context;
    private int resource;
    private ArrayList<FriendChatModel> friendChatArr;
    public FriendChatAdapter(@NonNull Context context, int resource, @NonNull ArrayList<FriendChatModel> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.friendChatArr = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.cell_friend_chat,null,true);

        RelativeLayout viewContain = convertView.findViewById(R.id.viewContain);
        LinearLayout viewPoint = convertView.findViewById(R.id.point_new);
        ImageView imgAvatar = convertView.findViewById(R.id.imgAvatar);
        TextView txtName = convertView.findViewById(R.id.txtName);
        TextView txtLatestMessage = convertView.findViewById(R.id.txtLatestMessage);


        FriendChatModel mData = friendChatArr.get(position);
        if (Constant.blocked.equals(mData.getType())) {
            viewContain.setBackgroundColor(context.getResources().getColor(R.color.grey));
        }
        if (Constant.stranger.equals(mData.getType())) {
            viewPoint.setVisibility(View.VISIBLE);
        }
        txtName.setText(mData.getName());
        txtLatestMessage.setText(mData.getRecentMessage());
        Uri uriAvatar = Uri.parse(mData.getUrlAvatar());
        Picasso.with(context).load(uriAvatar).into(imgAvatar);

        return convertView;
    }
}
