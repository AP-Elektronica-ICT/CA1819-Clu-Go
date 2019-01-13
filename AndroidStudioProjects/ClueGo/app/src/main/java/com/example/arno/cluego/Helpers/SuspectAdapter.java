package com.example.arno.cluego.Helpers;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.example.arno.cluego.MainActivity;
import com.example.arno.cluego.Objects.Suspect;
import com.github.amlcurran.showcaseview.ShowcaseView;
import com.github.amlcurran.showcaseview.targets.PointTarget;

import java.util.ArrayList;
import java.util.List;

public class SuspectAdapter extends BaseAdapter {
    private Context mContext;
    public List<Suspect> mSuspects;
    public int mAmtSus;

    public SuspectAdapter(Context c, List<Suspect> suspects, int amtSus) {
        mContext = c;
        mSuspects = suspects;
        mAmtSus = amtSus;
    }

    public int getCount() {
        return mSuspects.size();
    }

    public Object getItem(int position) {
        return mSuspects.get(position);
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new ViewGroup.LayoutParams(455, 450));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(18, 35, 18, 35);

        } else {
            imageView = (ImageView) convertView;
        }

        populateIds();

        Drawable drawable = mContext.getResources().getDrawable(mContext.getResources().getIdentifier("suspect"+mThumbIds.get(position), "drawable", mContext.getPackageName()));
        imageView.setImageDrawable(drawable);

        return imageView;
    }

    // references to our images
    public List<Integer> mThumbIds = new ArrayList<Integer>();

    public void populateIds(){
        int susSize = 0;
        for (Suspect item: mSuspects
             ) {
            susSize++;
        }
        for (int i = 0; i < susSize; i++) {
            //mThumbIds[i] = mSuspects.get(i).getSusId();
            mThumbIds.add(mSuspects.get(i).getSusId());
        }
    }


}

