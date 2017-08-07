package com.android.sample.github.viewmodel;

import android.databinding.ObservableInt;
import android.view.View;
import android.widget.AdapterView;

import com.android.sample.github.contract.RepositoryListViewContract;
import com.android.sample.github.model.GitHubService;

import java.util.Calendar;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yoon on 2017. 8. 4..
 */

public class RepositoryListViewModel {

    public final ObservableInt mProgressBarVisibility = new ObservableInt(View.VISIBLE);
    private final RepositoryListViewContract mRepositoryListViewContract;
    private final GitHubService mGitHubService;

    public RepositoryListViewModel(RepositoryListViewContract repositoryListViewContract, GitHubService gitHubService) {
        mRepositoryListViewContract = repositoryListViewContract;
        mGitHubService = gitHubService;
    }

    public void onLanguageSpinnerItemSelected(AdapterView<?> parent, View view, int position, long id) {
        loadRepositories((String) parent.getItemAtPosition(position));
    }

    private void loadRepositories(String language) {
        mProgressBarVisibility.set(View.VISIBLE);

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        String text = android.text.format.DateFormat.format("yyyy-MM-dd", calendar).toString();

        Observable<GitHubService.Repositories> observable =
                mGitHubService.listRepositories("language:" + language + " created:>" + text);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GitHubService.Repositories>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mRepositoryListViewContract.showError();
                    }

                    @Override
                    public void onNext(GitHubService.Repositories repositories) {
                        mProgressBarVisibility.set(View.GONE);
                        mRepositoryListViewContract.showRepositories(repositories);
                    }
                });
    }
}
