package com.workspace.bin.pintu.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.workspace.bin.pintu.R;
import com.workspace.bin.pintu.bean.ItemBean;

import java.util.List;

/**
 * Created by bin on 2017/4/10.
 */

public class GameAdapter extends BaseAdapter {
    private List<Bitmap> list;
    private LayoutInflater inflater;
    private Context context;
    public GameAdapter(List<Bitmap> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GDViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gv, parent, false);
            holder = new GDViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (GDViewHolder) convertView.getTag();
        }
        holder.imageView.setImageBitmap(list.get(position));
        return convertView;
    }

    public class GDViewHolder {
        ImageView imageView;

        public GDViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.iv_gv);
        }
    }

}
