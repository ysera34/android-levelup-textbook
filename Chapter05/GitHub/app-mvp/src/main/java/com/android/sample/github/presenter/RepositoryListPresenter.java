package com.android.sample.github.presenter;

import com.android.sample.github.contract.RepositoryListContract;
import com.android.sample.github.model.GitHubService;

import java.util.Calendar;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yoon on 2017. 7. 27..
 */

public class RepositoryListPresenter implements RepositoryListContract.UserActions {

    private final RepositoryListContract.View mRepositoryListView;
    private final GitHubService mGitHubService;

    public RepositoryListPresenter(RepositoryListContract.View repositoryListView, GitHubService gitHubService) {
        mRepositoryListView = repositoryListView;
        mGitHubService = gitHubService;
    }

    @Override
    public void selectLanguage(String language) {
        loadRepositories();
    }

    @Override
    public void selectRepositoryItem(GitHubService.RepositoryItem item) {
        mRepositoryListView.startDetailActivity(item.mFullName);
    }

    private void loadRepositories() {

        mRepositoryListView.showProgress();

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);

        String text = android.text.format.DateFormat.format("yyyy-MM-dd", calendar).toString();

        rx.Observable<GitHubService.Repositories> observable =
                mGitHubService.listRepositories("language:" + mRepositoryListView.getSelectedLanguage() + " created:>" + text);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GitHubService.Repositories>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                mRepositoryListView.showError();
            }

            @Override
            public void onNext(GitHubService.Repositories repositories) {
                mRepositoryListView.hideProgress();
                mRepositoryListView.showRepositories(repositories);
            }
        });

    }
}
