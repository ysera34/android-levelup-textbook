package com.android.sample.github.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.android.sample.github.contract.RepositoryListViewContract;
import com.android.sample.github.model.GitHubService;

/**
 * Created by yoon on 2017. 8. 4..
 */

public class RepositoryItemViewModel {

    public ObservableField<String> mRepositoryName = new ObservableField<>();
    public ObservableField<String> mRepositoryDescription = new ObservableField<>();
    public ObservableField<String> mRepositoryStar = new ObservableField<>();
    public ObservableField<String> mRepositoryImageUrl = new ObservableField<>();

    RepositoryListViewContract mRepositoryListViewContract;
    private String mFullRepositoryName;

    public RepositoryItemViewModel(RepositoryListViewContract repositoryListViewContract) {
        mRepositoryListViewContract = repositoryListViewContract;
    }

    public void loadItem(GitHubService.RepositoryItem item) {
        mFullRepositoryName = item.mFullName;
        mRepositoryName.set(item.mName);
        mRepositoryDescription.set(item.mDescription);
        mRepositoryStar.set(item.mStargazersCount);
        mRepositoryImageUrl.set(item.mOwner.mAvatarUrl);
    }

    public void onItemClick(View itemView) {
        mRepositoryListViewContract.startDetailActivity(mFullRepositoryName);
    }
}
