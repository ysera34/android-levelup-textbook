package com.android.sample.github.contract;

import com.android.sample.github.model.GitHubService;

/**
 * Created by yoon on 2017. 7. 27..
 */

public interface RepositoryListContract {


    /**
     * have to implement in View
     * used by Presenter to manipulate views
     */
    interface View {
        String getSelectedLanguage();
        void showProgress();
        void hideProgress();
        void showRepositories(GitHubService.Repositories repositories);
        void showError();
        void startDetailActivity(String fullRepositoryName);
    }

    /**
     * have to implement in Presenter
     * used by View to notify Presenter when view click
     */
    interface UserActions {
        void selectLanguage(String language);
        void selectRepositoryItem(GitHubService.RepositoryItem item);
    }
}
