package com.android.sample.github.contract;

import com.android.sample.github.model.GitHubService;

/**
 * Created by yoon on 2017. 7. 27..
 */

public interface DetailContract {

    /**
     * have to implement in View
     * used by Presenter to manipulate views
     */
    interface View {
        String getFullRepositoryName();
        void showRepositoryInfo(GitHubService.RepositoryItem response);
        void startBrowser(String url);
        void showError(String message);
    }

    /**
     * have to implement in Presenter
     * used by View to notify Presenter when view click
     */
    interface UserActions {
        void titleClick();
        void prepare();
    }
}
