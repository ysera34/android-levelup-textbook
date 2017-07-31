package com.android.sample.github.presenter;

import com.android.sample.github.contract.DetailContract;
import com.android.sample.github.model.GitHubService;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by yoon on 2017. 7. 31..
 */

public class DetailPresenter implements DetailContract.UserActions {

    final DetailContract.View mDetailView;
    private final GitHubService mGitHubService;
    private GitHubService.RepositoryItem mRepositoryItem;

    public DetailPresenter(DetailContract.View detailView, GitHubService gitHubService) {
        mDetailView = detailView;
        mGitHubService = gitHubService;
    }

    @Override
    public void prepare() {
        loadRepository();
    }

    @Override
    public void titleClick() {
        try {
//            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.mHtmlUrl)));
            mDetailView.startBrowser(mRepositoryItem.mHtmlUrl);
        } catch (Exception e) {
//            Snackbar.make(findViewById(android.R.id.content), "cannot open link", Snackbar.LENGTH_LONG)
//                    .setAction("Action", null).show();
            mDetailView.showError("cannot open link");
        }
    }

    private void loadRepository() {
        String fullRepositoryName = mDetailView.getFullRepositoryName();

        final String[] repositoryData = fullRepositoryName.split("/");
        final String owner = repositoryData[0];
        final String repositoryName = repositoryData[1];
        mGitHubService
                .detailRepository(owner, repositoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GitHubService.RepositoryItem>() {
                    @Override
                    public void call(GitHubService.RepositoryItem item) {
                        mRepositoryItem = item;
                        mDetailView.showRepositoryInfo(item);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        mDetailView.showError("cannot read");
                    }
                });
    }

}
