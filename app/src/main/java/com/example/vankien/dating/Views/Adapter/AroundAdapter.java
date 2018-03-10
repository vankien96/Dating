package com.example.vankien.dating.Views.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.vankien.dating.R;
import com.example.vankien.dating.Views.Fragment.AroundFragment;
import com.example.vankien.dating.model.AroundModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by TRẦN ĐỨC LONG on 3/2/2018.
 */

public class AroundAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<AroundModel> aroundModelList;

    public AroundAdapter(Context context, int layout, List<AroundModel> mAroundModel) {
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
       ImageButton imgYes, imgNo;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(layout,null);

            viewHolder.imgHinh = convertView.findViewById(R.id.imgAround);
            viewHolder.imgYes = convertView.findViewById(R.id.btnYes);
            viewHolder.imgNo = convertView.findViewById(R.id.btnNo);
            convertView.setTag(viewHolder);
        }
        else  {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        AroundModel mAroundModel = aroundModelList.get(position);
        viewHolder.imgHinh.setImageResource(mAroundModel.getImagePeople());
        viewHolder.imgYes.setImageResource(mAroundModel.getImgYes());

        return convertView;
    }

}
