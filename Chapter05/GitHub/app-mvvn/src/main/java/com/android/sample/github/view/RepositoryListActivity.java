package com.android.sample.github.view;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.android.sample.github.R;
import com.android.sample.github.contract.RepositoryListViewContract;
import com.android.sample.github.databinding.ActivityRepositoryListBinding;
import com.android.sample.github.model.GitHubService;
import com.android.sample.github.viewmodel.RepositoryListViewModel;

/**
 * Created by yoon on 2017. 8. 4..
 */

public class RepositoryListActivity extends AppCompatActivity
        implements RepositoryListViewContract {

    private Spinner mSpinner;
    private CoordinatorLayout mCoordinatorLayout;

    private RepositoryAdapter mRepositoryAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityRepositoryListBinding binding = DataBindingUtil
                .setContentView(this, R.layout.activity_repository_list);
        final GitHubService gitHubService = ((GitHubApplication) getApplication()).getGitHubService();
        binding.setViewModel(new RepositoryListViewModel((RepositoryListViewContract) this, gitHubService));

        setupViews();
    }

    private void setupViews() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.repository_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRepositoryAdapter = new RepositoryAdapter((Context) this, (RepositoryListViewContract) this);
        recyclerView.setAdapter(mRepositoryAdapter);

        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);
        mSpinner = (Spinner) findViewById(R.id.language_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        adapter.addAll("java", "objective-c", "swift", "groovy", "python", "ruby", "c");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(adapter);
    }

    @Override
    public void showRepositories(GitHubService.Repositories repositories) {
        mRepositoryAdapter.setItemsAndRefresh(repositories.mItems);
    }

    @Override
    public void showError() {
        Snackbar.make(mCoordinatorLayout, "cannot read", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void startDetailActivity(String fullRepositoryName) {
        DetailActivity.newIntent(this, fullRepositoryName);
    }
}
