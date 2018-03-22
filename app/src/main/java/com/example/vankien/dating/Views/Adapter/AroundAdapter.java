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
        ImageButton btnYes,btnNo;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout,null);

            viewHolder.imgHinh = convertView.findViewById(R.id.imgAround);
            viewHolder.btnYes = convertView.findViewById(R.id.btnYes);
            viewHolder.btnNo = convertView.findViewById(R.id.btnNo);
            convertView.setTag(viewHolder);
        }
        else  {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                PeopleAround people = aroundModelList.get(position);
                Bundle b = new Bundle();
                b.putSerializable("Detail",people);
                intent.putExtras(b);
                context.startActivity(intent);
            }
        });
        PeopleAround mAroundModel = aroundModelList.get(position);
        String avatar = mAroundModel.getAvatarUrl();
        Uri uri = Uri.parse(avatar);
        Picasso.with(context).load(uri).into(viewHolder.imgHinh);

        return convertView;
    }

}
