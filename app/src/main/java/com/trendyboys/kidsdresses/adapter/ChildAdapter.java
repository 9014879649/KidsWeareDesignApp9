package com.trendyboys.kidsdresses.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.trendyboys.kidsdresses.Assymetric.AGVRecyclerViewAdapter;
import com.trendyboys.kidsdresses.Assymetric.AsymmetricItem;
import com.trendyboys.kidsdresses.R;
import com.trendyboys.kidsdresses.model.ItemImage;

import java.util.List;

/**
 * Created by Nageswara Rao.CH on 4/6/2018.
 */

public class ChildAdapter extends AGVRecyclerViewAdapter<ChildAdapter.ViewHolder> {

    List<ItemImage> items;
    private int mDisplay = 0;
    private int mTotal = 0;
    Context mContext;

    ChildAdapter(Context context, List<ItemImage> items, int mDisplay, int mTotal){
        this.mContext = context;
        this.items = items;
        this.mDisplay = mDisplay;
        this.mTotal = mTotal;

    }



    @Override
    public AsymmetricItem getItem(int position) {
        return (AsymmetricItem) items.get(position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent, viewType,items);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(items,position,mDisplay,mTotal);

    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position % 2 == 0 ? 1 : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView mImageView;
        TextView textView;

        public ViewHolder(ViewGroup parent, int viewType, List<ItemImage> items){
            super(LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_item, parent, false));

            mImageView = (ImageView) itemView.findViewById(R.id.mImageView);
            textView = (TextView) itemView.findViewById(R.id.tvCount);
        }


        public void bind(List<ItemImage> item, int position, int mDisplay, int mTotal) {
           /* Glide.with(mContext).load(String.valueOf(item.get(position).getImagePath()))
                    .placeholder(R.drawable.default_image)
                    .error(R.drawable.error_images)
                    .into(mImageView);*/

            Glide.with(mContext).load(getImage(item.get(position).getImagePath())).into(mImageView);
//            Log.e("ChildAdapter","bind==========="+(item.get(position).getImgCount()+"-"+mDisplay)+"mTotal"+mTotal);
//            textView.setText("+"+(item.get(position).getImgCount()-mDisplay));
            textView.setText("+"+(mTotal-mDisplay));
            if(mTotal > mDisplay)
            {
                if(position  == mDisplay-1) {
                    textView.setVisibility(View.VISIBLE);
                    mImageView.setAlpha(72);
                }
                else {
                    textView.setVisibility(View.INVISIBLE);
                    mImageView.setAlpha(255);
                }
            }
            else
            {
                mImageView.setAlpha(255);
                textView.setVisibility(View.INVISIBLE);
            }

            // textView.setText(String.valueOf(item.getPosition()));
        }
    }



    public int getImage(String imageName) {

        int drawableResourceId = mContext.getResources().getIdentifier(imageName, "drawable", mContext.getPackageName());

        return drawableResourceId;
    }
}
