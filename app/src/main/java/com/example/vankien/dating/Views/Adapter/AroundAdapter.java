package com.example.vankien.dating.Views.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.vankien.dating.Models.PeopleAround;
import com.example.vankien.dating.R;
import com.example.vankien.dating.Views.Activity.DetailActivity;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by TRẦN ĐỨC LONG on 3/2/2018.
 */

public class AroundAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<PeopleAround> aroundModelList;

    public AroundAdapter(Context context, int layout, List<PeopleAround> mAroundModel) {
        this.context = context;
        this.layout = layout;
        this.aroundModelList = mAroundModel;
    }

    @Override
    public int getCount() {
        return aroundModelList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    public class ViewHolder {
        ImageView imgHinh;
        //ImageButton btnYes,btnNo;
        TextView txtName;
        ImageView imgGender;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout,null);

            viewHolder.imgHinh = convertView.findViewById(R.id.imgAround);
            //viewHolder.btnYes = convertView.findViewById(R.id.btnYes);
            //viewHolder.btnNo = convertView.findViewById(R.id.btnNo);
            viewHolder.txtName = convertView.findViewById(R.id.txt_name);
            viewHolder.imgGender = convertView.findViewById(R.id.img_gender);
            convertView.setTag(viewHolder);
        }
        else  {
            viewHolder = (ViewHolder) convertView.getTag();
        }
//        viewHolder.btnYes.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, DetailActivity.class);
//                PeopleAround people = aroundModelList.get(position);
//                intent.putExtra("UserID",people.getId());
//                context.startActivity(intent);
//            }
//        });
//        viewHolder.imgHinh.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, DetailActivity.class);
//                PeopleAround people = aroundModelList.get(position);
//                intent.putExtra("UserID",people.getId());
//                context.startActivity(intent);
//            }
//        });
        PeopleAround mAroundModel = aroundModelList.get(position);
        viewHolder.txtName.setText(mAroundModel.getName());
        String avatar = mAroundModel.getAvatarUrl();
        if (mAroundModel.getGender() == 0) {
            viewHolder.imgGender.setImageResource(R.drawable.female);
        } else {
            viewHolder.imgGender.setImageResource(R.drawable.male);
        }
        Uri uri = Uri.parse(avatar);
        //Picasso.with(context).load(uri).into(viewHolder.imgHinh);
        Glide.with(context).load(uri).into(viewHolder.imgHinh);

        return convertView;
    }

}
