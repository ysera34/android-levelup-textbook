package com.android.sample.github;

import android.app.Application;
import android.util.Log;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by yoon on 2017. 7. 26..
 */

public class GitHubApplication extends Application {

    private static final String TAG = GitHubApplication.class.getSimpleName();

    private Retrofit mRetrofit;
    private GitHubService mGitHubService;

    @Override
    public void onCreate() {
        super.onCreate();

        setupApiClient();
    }

    private void setupApiClient() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                Log.d(TAG, "api log: " + message);
            }
        });

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(loggingInterceptor).build();
        mRetrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl("https://api.github.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        mGitHubService = mRetrofit.create(GitHubService.class);
    }

    public GitHubService getGitHubService() {
        return mGitHubService;
    }
}
