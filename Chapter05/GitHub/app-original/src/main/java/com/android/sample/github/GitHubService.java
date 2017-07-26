package com.android.sample.github;


import com.google.gson.annotations.SerializedName;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by yoon on 2017. 7. 26..
 */

public interface GitHubService {

    @GET("search/repositories?sort=stars&order=desc")
    Observable<Repositories> listRepositories(@Query("q") String query);

    @GET("repos/{repoOwner}/{repoName}")
    Observable<RepositoryItem> detailRepository(
            @Path(value = "repoOwner") String owner, @Path(value = "repoName") String repoName);

    /*
    public static class Repositories {

        @SerializedName("items")
        public final List<RepositoryItem> mItems;

        public Repositories(List<RepositoryItem> items) {
//            this.items = items;
            mItems = items;
        }
    }

    public static class RepositoryItem {

        public final String description;
        public final Owner owner;
        public final String language;
        public final String name;
        public final String stargazers_count;
        public final String forks_count;
        public final String full_name;
        public final String html_url;

        public RepositoryItem(String description, Owner owner, String language, String name, String stargazers_count, String forks_count, String full_name, String html_url) {
            this.description = description;
            this.owner = owner;
            this.language = language;
            this.name = name;
            this.stargazers_count = stargazers_count;
            this.forks_count = forks_count;
            this.full_name = full_name;
            this.html_url = html_url;
        }
    }

    public static class Owner {
        public final String received_events_url;
        public final String organizations_url;
        public final String avatar_url;
        public final String gravatar_id;
        public final String gists_url;
        public final String starred_url;
        public final String site_admin;
        public final String type;
        public final String url;
        public final String id;
        public final String html_url;
        public final String following_url;
        public final String events_url;
        public final String login;
        public final String subscriptions_url;
        public final String repos_url;
        public final String followers_url;

        public Owner(String received_events_url, String organizations_url, String avatar_url, String gravatar_id, String gists_url, String starred_url, String site_admin, String type, String url, String id, String html_url, String following_url, String events_url, String login, String subscriptions_url, String repos_url, String followers_url) {
            this.received_events_url = received_events_url;
            this.organizations_url = organizations_url;
            this.avatar_url = avatar_url;
            this.gravatar_id = gravatar_id;
            this.gists_url = gists_url;
            this.starred_url = starred_url;
            this.site_admin = site_admin;
            this.type = type;
            this.url = url;
            this.id = id;
            this.html_url = html_url;
            this.following_url = following_url;
            this.events_url = events_url;
            this.login = login;
            this.subscriptions_url = subscriptions_url;
            this.repos_url = repos_url;
            this.followers_url = followers_url;
        }
    }
    */


    public static class Repositories {

        @SerializedName("items")
        public final List<RepositoryItem> mItems;

        public Repositories(List<RepositoryItem> items) {
            mItems = items;
        }
    }

    public static class RepositoryItem {
        @SerializedName("description")
        public final String mDescription;
        @SerializedName("owner")
        public final Owner mOwner;
        @SerializedName("language")
        public final String mLanguage;
        @SerializedName("name")
        public final String mName;
        @SerializedName("stargazers_count")
        public final String mStargazersCount;
        @SerializedName("forks_count")
        public final String mForksCount;
        @SerializedName("full_name")
        public final String mFullName;
        @SerializedName("html_url")
        public final String mHtmlUrl;

        public RepositoryItem(String description, Owner owner, String language, String name,
                              String stargazersCount, String forksCount, String fullName, String htmlUrl) {
            mDescription = description;
            mOwner = owner;
            mLanguage = language;
            mName = name;
            mStargazersCount = stargazersCount;
            mForksCount = forksCount;
            mFullName = fullName;
            mHtmlUrl = htmlUrl;
        }
    }

    public static class Owner {
        @SerializedName("received_events_url")
        public final String mReceivedEventsUrl;
        @SerializedName("organizations_url")
        public final String mOrganizationsUrl;
        @SerializedName("avatar_url")
        public final String mAvatarUrl;
        @SerializedName("gravatar_id")
        public final String mGravatarId;
        @SerializedName("gists_url")
        public final String mGistsUrl;
        @SerializedName("starred_url")
        public final String mStarredUrl;
        @SerializedName("site_admin")
        public final String mSiteAdmin;
        @SerializedName("type")
        public final String mType;
        @SerializedName("url")
        public final String mUrl;
        @SerializedName("id")
        public final String mId;
        @SerializedName("html_url")
        public final String mHtmlUrl;
        @SerializedName("following_url")
        public final String mFollowingUrl;
        @SerializedName("events_url")
        public final String mEventsUrl;
        @SerializedName("login")
        public final String mLogin;
        @SerializedName("subscriptions_url")
        public final String mSubscriptionsUrl;
        @SerializedName("repos_url")
        public final String mRepositoriesUrl;
        @SerializedName("followers_url")
        public final String mFollowersUrl;

        public Owner(String receivedEventsUrl, String organizationsUrl, String avatarUrl,
                     String gravatarId, String gistsUrl, String starredUrl, String siteAdmin,
                     String type, String url, String id, String htmlUrl, String followingUrl,
                     String eventsUrl, String login, String subscriptionsUrl, String repositoriesUrl, String followersUrl) {
            mReceivedEventsUrl = receivedEventsUrl;
            mOrganizationsUrl = organizationsUrl;
            mAvatarUrl = avatarUrl;
            mGravatarId = gravatarId;
            mGistsUrl = gistsUrl;
            mStarredUrl = starredUrl;
            mSiteAdmin = siteAdmin;
            mType = type;
            mUrl = url;
            mId = id;
            mHtmlUrl = htmlUrl;
            mFollowingUrl = followingUrl;
            mEventsUrl = eventsUrl;
            mLogin = login;
            mSubscriptionsUrl = subscriptionsUrl;
            mRepositoriesUrl = repositoriesUrl;
            mFollowersUrl = followersUrl;
        }
    }

}
