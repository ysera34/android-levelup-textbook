package com.android.sample.github.view;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.android.sample.github.R;
import com.android.sample.github.contract.DetailViewContract;
import com.android.sample.github.databinding.ActivityDetailBinding;
import com.android.sample.github.model.GitHubService;
import com.android.sample.github.viewmodel.DetailViewModel;

/**
 * Created by yoon on 2017. 8. 4..
 */

public class DetailActivity extends AppCompatActivity implements DetailViewContract {

    private static final String EXTRA_FULL_REPOSITORY_NAME = "extra_full_repository_name";
    private String mFullRepositoryName;

    public static void newIntent(Context context, String fullRepositoryName) {
        final Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_FULL_REPOSITORY_NAME, fullRepositoryName);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final ActivityDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_detail);
        final GitHubService gitHubService = ((GitHubApplication) getApplication()).getGitHubService();
        final DetailViewModel detailViewModel = new DetailViewModel((DetailViewContract) this, gitHubService);
        binding.setViewModel(detailViewModel);

        final Intent intent = getIntent();
        mFullRepositoryName = intent.getStringExtra(EXTRA_FULL_REPOSITORY_NAME);
        detailViewModel.loadRepository();
    }

    @Override
    public String getFullRepositoryName() {
        return mFullRepositoryName;
    }

    @Override
    public void startBrowser(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void showError(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .show();
    }
}
