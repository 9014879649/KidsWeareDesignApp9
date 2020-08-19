package com.trendyboys.kidsdresses.adapter;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.trendyboys.kidsdresses.Assymetric.AsymmetricRecyclerView;
import com.trendyboys.kidsdresses.Assymetric.AsymmetricRecyclerViewAdapter;
import com.trendyboys.kidsdresses.Assymetric.SpacesItemDecoration;
import com.trendyboys.kidsdresses.Assymetric.Utils;
import com.trendyboys.kidsdresses.R;
import com.trendyboys.kidsdresses.model.ItemList;
import com.trendyboys.kidsdresses.utill.OnLoadMoreListener;

import java.util.List;

public class ParentAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private OnLoadMoreListener onLoadMoreListener;
    private boolean isLoading;
    private AppCompatActivity activity;
    private List<ItemList> mItemList;
    private int visibleThreshold = 5;
    private int lastVisibleItem, totalItemCount;
    private Context mCon;
    private int mDisplay= 0;
    private int mTotal= 0;



    public ParentAdapter(Context con, List<ItemList> moviesList, int max_display, int mTotal_size, RecyclerView recyclerView) {
        mCon = con;
        this.mItemList = moviesList;
        mDisplay = max_display;
        mTotal = mTotal_size;

         final LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalItemCount = linearLayoutManager.getItemCount();

                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
                if (!isLoading && totalItemCount <= (lastVisibleItem + visibleThreshold)) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                    isLoading = true;
                }
            }
        });

    }


    public void setOnLoadMoreListener(OnLoadMoreListener mOnLoadMoreListener) {
        this.onLoadMoreListener = mOnLoadMoreListener;
    }

    @Override
    public int getItemViewType(int position) {
        return mItemList.get(position) == null ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mCon).inflate(R.layout.parent_item, parent, false);
            return new UserViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mCon).inflate(R.layout.item_loading, parent, false);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof UserViewHolder) {
            UserViewHolder userViewHolder = (UserViewHolder) holder;
            ItemList item = mItemList.get(position);
            String[] strings = item.getItemName().split("-");
            userViewHolder.tvTitle.setText(""+strings[0]);
            userViewHolder.tvDate.setText(""+strings[1]);
            ChildAdapter adapter = new ChildAdapter(mCon,item.getImages(),mDisplay,item.getImgCount());
            userViewHolder.asymmetricRecyclerView.setAdapter(new AsymmetricRecyclerViewAdapter<>(mCon,userViewHolder.asymmetricRecyclerView, adapter));
        } else if (holder instanceof LoadingViewHolder) {
            LoadingViewHolder loadingViewHolder = (LoadingViewHolder) holder;
            loadingViewHolder.progressBar.setIndeterminate(true);
        }
    }

    @Override
    public int getItemCount() {
        return mItemList == null ? 0 : mItemList.size();
    }

    public void setLoaded() {
        isLoading = false;
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View view) {
            super(view);
            progressBar = (ProgressBar) view.findViewById(R.id.progressBar1);
        }
    }

    private class UserViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;
        public TextView tvDate;
        AsymmetricRecyclerView asymmetricRecyclerView;

        public UserViewHolder(View view) {
            super(view);
            tvTitle = (TextView) view.findViewById(R.id.tvTitle);
            tvDate = (TextView) view.findViewById(R.id.tvTime);
            asymmetricRecyclerView = (AsymmetricRecyclerView) view.findViewById(R.id.recyclerView);


            asymmetricRecyclerView.setRequestedColumnCount(3);
            asymmetricRecyclerView.setDebugging(true);
            asymmetricRecyclerView.setRequestedHorizontalSpacing(Utils.dpToPx(mCon, 3));
            asymmetricRecyclerView.addItemDecoration(
                    new SpacesItemDecoration(mCon.getResources().getDimensionPixelSize(R.dimen.recycler_padding)));
        }
    }


}