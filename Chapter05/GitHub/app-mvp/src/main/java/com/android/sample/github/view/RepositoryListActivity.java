package com.android.sample.github.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.android.sample.github.R;
import com.android.sample.github.contract.RepositoryListContract;
import com.android.sample.github.model.GitHubService;
import com.android.sample.github.presenter.RepositoryListPresenter;

public class RepositoryListActivity extends AppCompatActivity
        implements RepositoryAdapter.OnRepositoryItemClickListener, RepositoryListContract.View {

    private Spinner mLanguageSpinner;
    private ProgressBar mProgressBar;
    private CoordinatorLayout mCoordinatorLayout;

    private RepositoryAdapter mRepositoryAdapter;
    private RepositoryListContract.UserActions mRepositoryListPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_list);

        setupViews();

        final GitHubService gitHubService = ((GitHubApplication) getApplication()).getGitHubService();
        mRepositoryListPresenter = new RepositoryListPresenter((RepositoryListContract.View) this, gitHubService);
    }

    private void setupViews() {

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.repository_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRepositoryAdapter = new RepositoryAdapter((Context) this, (RepositoryAdapter.OnRepositoryItemClickListener) this);
        recyclerView.setAdapter(mRepositoryAdapter);

        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mCoordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinator_layout);

        mLanguageSpinner = (Spinner) findViewById(R.id.language_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item);
        adapter.addAll("java", "objective-c", "android", "node", "swift");
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mLanguageSpinner.setAdapter(adapter);
        mLanguageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String language = (String) mLanguageSpinner.getItemAtPosition(position);
                mRepositoryListPresenter.selectLanguage(language);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public void onRepositoryItemClick(GitHubService.RepositoryItem item) {
        mRepositoryListPresenter.selectRepositoryItem(item);
    }

    @Override
    public String getSelectedLanguage() {
        return (String) mLanguageSpinner.getSelectedItem();
    }

    @Override
    public void showProgress() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        mProgressBar.setVisibility(View.GONE);
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
        DetailActivity.start(this, fullRepositoryName);
    }
}
