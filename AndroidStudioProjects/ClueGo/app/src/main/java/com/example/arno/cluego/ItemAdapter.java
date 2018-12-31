package com.example.arno.cluego;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private final int[] items;

    public ItemAdapter(Context context, int[] items){
        this.context = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

            gridView = new View(context);
            gridView = inflater.inflate(R.layout.grid_item, null);
            int positems = items[position];
            ImageView item_image = (ImageView) gridView.findViewById(R.id.item);

            item_image.setImageResource(positems);

        return gridView;
    }

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
}
