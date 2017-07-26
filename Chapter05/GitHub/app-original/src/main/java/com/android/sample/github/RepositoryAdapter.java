package com.android.sample.github;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.List;

/**
 * Created by yoon on 2017. 7. 26..
 */

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private final Context mContext;
    private List<GitHubService.RepositoryItem> mItems;
    private final OnRepositoryItemClickListener mOnRepositoryItemClickListener;

    public RepositoryAdapter(Context context, OnRepositoryItemClickListener onRepositoryItemClickListener) {
        mContext = context;
        mOnRepositoryItemClickListener = onRepositoryItemClickListener;
    }

    public void setItemsAndRefresh(List<GitHubService.RepositoryItem> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public GitHubService.RepositoryItem getItemAt(int position) {
        return mItems.get(position);
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext)
                .inflate(R.layout.list_item_repository, parent, false);
        return new RepositoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RepositoryViewHolder holder, int position) {
        final GitHubService.RepositoryItem item = getItemAt(position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnRepositoryItemClickListener.onRepositoryItemClick(item);
            }
        });
        holder.mRepositoryNameTextView.setText(item.mName);
        holder.mRepositoryDetailTextView.setText(item.mDescription);
        holder.mStarCountTextView.setText(item.mStargazersCount);
//        holder.mRepositoryNameTextView.setText(item.name);
//        holder.mRepositoryDetailTextView.setText(item.description);
//        holder.mStarCountTextView.setText(item.stargazers_count);

        Glide.with(mContext)
                .load(item.mOwner.mAvatarUrl)
//                .load(item.owner.avatar_url)
                .asBitmap().centerCrop().into(new BitmapImageViewTarget(holder.mRepositoryImageView) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(mContext.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                holder.mRepositoryImageView.setImageDrawable(circularBitmapDrawable);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        }
        return mItems.size();
    }

    interface OnRepositoryItemClickListener {
        void onRepositoryItemClick(GitHubService.RepositoryItem item);
    }

    static class RepositoryViewHolder extends RecyclerView.ViewHolder {

        private final TextView mRepositoryNameTextView;
        private final TextView mRepositoryDetailTextView;
        private final ImageView mRepositoryImageView;
        private final TextView mStarCountTextView;

        public RepositoryViewHolder(View itemView) {
            super(itemView);

            mRepositoryNameTextView = (TextView)
                    itemView.findViewById(R.id.list_item_repository_name_text_view);
            mRepositoryDetailTextView = (TextView)
                    itemView.findViewById(R.id.list_item_repository_detail_text_view);
            mRepositoryImageView = (ImageView)
                    itemView.findViewById(R.id.list_item_repository_image_view);
            mStarCountTextView = (TextView)
                    itemView.findViewById(R.id.list_item_repository_star_text_view);
        }
    }

}
