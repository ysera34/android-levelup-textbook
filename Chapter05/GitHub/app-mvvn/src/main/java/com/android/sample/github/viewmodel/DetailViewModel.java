package com.android.sample.github.viewmodel;

import android.databinding.ObservableField;
import android.view.View;

import com.android.sample.github.contract.DetailViewContract;
import com.android.sample.github.model.GitHubService;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yoon on 2017. 8. 4..
 */

public class DetailViewModel {

    final DetailViewContract mDetailViewContract;
    private final GitHubService mGitHubService;
    public ObservableField<String> mFullRepositoryName = new ObservableField<>();
    public ObservableField<String> mRepositoryDescription = new ObservableField<>();
    public ObservableField<String> mRepositoryStar = new ObservableField<>();
    public ObservableField<String> mRepositoryFork = new ObservableField<>();
    public ObservableField<String> mRepositoryImageUrl = new ObservableField<>();
    private GitHubService.RepositoryItem mRepositoryItem;

    public DetailViewModel(DetailViewContract detailViewContract, GitHubService gitHubService) {
        mDetailViewContract = detailViewContract;
        mGitHubService = gitHubService;
    }

    public void prepare() {
        loadRepository();
    }

    public void loadRepository() {
        String fullRepositoryName = mDetailViewContract.getFullRepositoryName();
        final String[] repositoryData = fullRepositoryName.split("/");
        final String owner = repositoryData[0];
        final String repositoryName = repositoryData[1];
        mGitHubService.detailRepository(owner, repositoryName)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GitHubService.RepositoryItem>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mDetailViewContract.showError("cannot load");
                    }

                    @Override
                    public void onNext(GitHubService.RepositoryItem item) {
                        loadRepositoryItem(item);
                    }
                });
    }

    private void loadRepositoryItem(GitHubService.RepositoryItem repositoryItem) {
        mRepositoryItem = repositoryItem;
        mFullRepositoryName.set(mRepositoryItem.mFullName);
        mRepositoryDescription.set(mRepositoryItem.mDescription);
        mRepositoryStar.set(mRepositoryItem.mStargazersCount);
        mRepositoryFork.set(mRepositoryItem.mForksCount);
        mRepositoryImageUrl.set(mRepositoryItem.mOwner.mAvatarUrl);
    }

    public void onTitleClick(View view) {
        try {
            mDetailViewContract.startBrowser(mRepositoryItem.mHtmlUrl);
        } catch (Exception e) {
            mDetailViewContract.showError("cannot open the link");
        }
    }
}
