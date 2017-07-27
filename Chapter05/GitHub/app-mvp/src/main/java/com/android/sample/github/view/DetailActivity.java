package com.android.sample.github.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.sample.github.model.GitHubService;
import com.android.sample.github.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DetailActivity extends AppCompatActivity {

    private static final String EXTRA_FULL_REPOSITORY_NAME = "full_repository_name";

    public static void start(Context context, String fullRepositoryName) {
        final Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra(EXTRA_FULL_REPOSITORY_NAME, fullRepositoryName);
        context.startActivity(intent);
    }

    private TextView mFullNameTextView;
    private TextView mDetailTextView;
    private TextView mRepositoryStarTextView;
    private TextView mRepositoryForkTextView;
    private ImageView mOwnerImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mFullNameTextView = (TextView) findViewById(R.id.full_name_text_view);
        mDetailTextView = (TextView) findViewById(R.id.detail_text_view);
        mRepositoryStarTextView = (TextView) findViewById(R.id.repository_star_text_view);
        mRepositoryForkTextView = (TextView) findViewById(R.id.repository_fork_text_view);
        mOwnerImageView = (ImageView) findViewById(R.id.owner_image_view);

        final Intent intent = getIntent();
        final String fullRepositoryName = intent.getStringExtra(EXTRA_FULL_REPOSITORY_NAME);

        loadRepository(fullRepositoryName);
    }

    private void loadRepository(String fullRepositoryName) {

        final String[] repositoryData = fullRepositoryName.split("/");
        final String owner = repositoryData[0];
        final String repositoryName = repositoryData[1];
        final GitHubService gitHubService = ((GitHubApplication) getApplication()).getGitHubService();
        gitHubService.detailRepository(owner, repositoryName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<GitHubService.RepositoryItem>() {
                    @Override
                    public void call(GitHubService.RepositoryItem item) {
                        setupRepositoryInfo(item);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Snackbar.make(findViewById(android.R.id.content), " cannot read", Snackbar.LENGTH_LONG)
                                .setAction("Action", null).show();
                    }
                });
    }

    private void setupRepositoryInfo(final GitHubService.RepositoryItem item) {
        mFullNameTextView.setText(item.mFullName);
        mDetailTextView.setText(item.mDescription);
        mRepositoryStarTextView.setText(item.mStargazersCount);
        mRepositoryForkTextView.setText(item.mForksCount);

        Glide.with(DetailActivity.this)
                .load(item.mOwner.mAvatarUrl)
                .asBitmap().centerCrop().into(new BitmapImageViewTarget(mOwnerImageView) {
            @Override
            protected void setResource(Bitmap resource) {
//                super.setResource(resource);
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                mOwnerImageView.setImageDrawable(circularBitmapDrawable);
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(item.mHtmlUrl)));
                } catch (Exception e) {
                    Snackbar.make(findViewById(android.R.id.content), "cannot open link", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
            }
        };
        mFullNameTextView.setOnClickListener(listener);
        mOwnerImageView.setOnClickListener(listener);
    }

}
