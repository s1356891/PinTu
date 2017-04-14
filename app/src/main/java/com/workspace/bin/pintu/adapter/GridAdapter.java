package com.workspace.bin.pintu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import com.workspace.bin.pintu.R;
import com.workspace.bin.pintu.Util.ImageUtil;

/**
 * Created by bin on 2017/4/10.
 */

public class GridAdapter extends BaseAdapter {
    private int[] datas;
    private LayoutInflater inflater;
    private Context context;

    public GridAdapter(int[] datas, Context context) {
        this.datas = datas;
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public int getCount() {
        return datas.length;
    }

    @Override
    public Object getItem(int position) {
        return datas[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        GDViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gv, parent, false);
            viewHolder = new GDViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (GDViewHolder) convertView.getTag();
        }
        viewHolder.imageView.setImageDrawable(ImageUtil.setImage(context, datas[position]));
        return convertView;
    }

    public class GDViewHolder {
        ImageView imageView;

        public GDViewHolder(View convertView) {
            imageView = (ImageView) convertView.findViewById(R.id.iv_gv);
        }
    }

}
