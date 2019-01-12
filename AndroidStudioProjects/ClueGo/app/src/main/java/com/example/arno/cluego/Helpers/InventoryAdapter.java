package com.example.arno.cluego.Helpers;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.arno.cluego.Objects.Clue;
import com.example.arno.cluego.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InventoryAdapter extends RecyclerView.Adapter<InventoryAdapter.ViewHolder> {
    private List<Clue> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context mContext;

    public interface OnDataChangeListener{
        public void onDataChanged(int price);
    }

    OnDataChangeListener mOnDataChangeListener;
    public void setOnDataChangeListener(OnDataChangeListener onDataChangeListener){
        mOnDataChangeListener = onDataChangeListener;
    }


    // data is passed into the constructor
    public InventoryAdapter(Context context, List<Clue> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mContext = context;
    }

    // inflates the row layout from xml when needed
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.inventory_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each row
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Clue clue = getClue(position);
        holder.tvName.setText(clue.getClueName());
        holder.tvDescription.setText(clue.getClueDescription());
        Picasso.get().load(clue.getClueImgUrl()).into(holder.ivClueImg);
        clue.setFound(true);
        Log.d("TAG", "onBindViewHolder: " + '"' + clue.getClueImgUrl() + '"');
       /* try{
            String uri = "@drawable/" + shop.getImgString(); //imname without extension

            int imageResource = mContext.getResources().getIdentifier(uri, null, mContext.getPackageName()); //get image  resource

            Drawable res = mContext.getResources().getDrawable(imageResource, null); // convert into drawble
            holder.ivShopIcon.setImageDrawable(res);


        }catch (Resources.NotFoundException ex){
        }*/
    }

    // total number of rows
    @Override
    public int getItemCount() {
        return mData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvName, tvDescription;
        ImageView ivClueImg;

        ViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDescription = itemView.findViewById(R.id.tvDescription);
            ivClueImg = itemView.findViewById(R.id.ivClueImg);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public Clue getClue(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
