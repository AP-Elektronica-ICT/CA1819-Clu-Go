package com.example.arno.cluego.Helpers;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.arno.cluego.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> items;

    public ItemAdapter(Context context,ArrayList<String> items){
        this.context = context;
        this.items = items;
    }

    public View getView(int position, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;

            gridView = new View(context);
            gridView = inflater.inflate(R.layout.grid_item, null);
            String positems = items.get(position);
            ImageView item_image = (ImageView) gridView.findViewById(R.id.item);

            Picasso.get().load(positems).into(item_image);

        return gridView;
    }

    @Override
    public int getCount() {
        return items.size();
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
