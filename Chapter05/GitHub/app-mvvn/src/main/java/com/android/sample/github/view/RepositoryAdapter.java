package com.android.sample.github.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.sample.github.R;
import com.android.sample.github.contract.RepositoryListViewContract;
import com.android.sample.github.databinding.ListItemRepositoryBinding;
import com.android.sample.github.model.GitHubService;
import com.android.sample.github.viewmodel.RepositoryItemViewModel;

import java.util.List;

/**
 * Created by yoon on 2017. 8. 7..
 */

public class RepositoryAdapter extends RecyclerView.Adapter<RepositoryAdapter.RepositoryViewHolder> {

    private final RepositoryListViewContract mViewContract;
    private final Context mContext;
    private List<GitHubService.RepositoryItem> mItems;

    public RepositoryAdapter(Context context, RepositoryListViewContract viewContract) {
        mContext = context;
        mViewContract = viewContract;
    }

    public void setItemsAndRefresh(List<GitHubService.RepositoryItem> items) {
        mItems = items;
        notifyDataSetChanged();
    }

    public GitHubService.RepositoryItem getItemAt(int position) {
        return mItems.get(position);
    }

    @Override
    public RepositoryAdapter.RepositoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ListItemRepositoryBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(mContext), R.layout.list_item_repository, parent, false);
        binding.setViewModel(new RepositoryItemViewModel(mViewContract));
        return new RepositoryViewHolder(binding.getRoot(), binding.getViewModel());
    }

    @Override
    public void onBindViewHolder(RepositoryAdapter.RepositoryViewHolder holder, int position) {
        final GitHubService.RepositoryItem item = getItemAt(position);
        holder.loadItem(item);
    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        }
        return mItems.size();
    }

    static class RepositoryViewHolder extends RecyclerView.ViewHolder {

        private final RepositoryItemViewModel mViewModel;

        public RepositoryViewHolder(View itemView, RepositoryItemViewModel viewModel) {
            super(itemView);
            mViewModel = viewModel;
        }

        public void loadItem(GitHubService.RepositoryItem item) {
            mViewModel.loadItem(item);
        }
    }
}
