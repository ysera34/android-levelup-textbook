package com.android.sample.github;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;

import java.util.Calendar;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RepositoryListActivity extends AppCompatActivity
        implements RepositoryAdapter.OnRepositoryItemClickListener {

    public static final String TAG = RepositoryListActivity.class.getSimpleName();

    private Spinner mSpinner;
    private ProgressBar mProgressBar;
    private CoordinatorLayout mCoordinatorLayout;

    private RepositoryAdapter mRepositoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_list);

        setupViews();
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
        mSpinner = (Spinner) findViewById(R.id.language_spinner);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item);
        arrayAdapter.addAll("java", "objective-c", "swift", "groovy", "python", "ruby", "c");
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinner.setAdapter(arrayAdapter);
        mSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String language = (String) mSpinner.getItemAtPosition(position);
                loadRepositories(language);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void loadRepositories(String language) {

        mProgressBar.setVisibility(View.VISIBLE);

        final Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        String text = DateFormat.format("yyyy-MM-dd", calendar).toString();

        final GitHubApplication application = (GitHubApplication) getApplication();
        Observable<GitHubService.Repositories> observable =
                application.getGitHubService().listRepositories("language:" + language + " created:>" + text);
        observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<GitHubService.Repositories>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                Snackbar.make(mCoordinatorLayout, "cannot read:" + e.toString(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Log.e(TAG, e.getMessage());
            }

            @Override
            public void onNext(GitHubService.Repositories repositories) {
                mProgressBar.setVisibility(View.GONE);
//                mRepositoryAdapter.setItemsAndRefresh(repositories.mItems);
                mRepositoryAdapter.setItemsAndRefresh(repositories.mItems);
            }
        });
    }

    @Override
    public void onRepositoryItemClick(GitHubService.RepositoryItem item) {
//        DetailActivity.start(this, item.mFullName);
    }
}
