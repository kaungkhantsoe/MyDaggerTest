package com.kks.mydaggertest.di.main;

import android.app.Application;
import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.kks.mydaggertest.network.main.MainApi;
import com.kks.mydaggertest.ui.main.posts.PostsRecyclerAdapter;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;

/**
 * Created by kaungkhantsoe on 2020-02-16.
 **/
@Module
public class MainModule {

    @MainScope
    @Provides
    static LinearLayoutManager provideLinearLayoutManager(Application application) {
        return new LinearLayoutManager(application.getBaseContext(), LinearLayoutManager.VERTICAL,false);
    }

    @MainScope
    @Provides
    static PostsRecyclerAdapter providePostsRecyclerAdapter() {
        return new PostsRecyclerAdapter();
    }

    @MainScope
    @Provides
    static MainApi proviedMainApi(Retrofit retrofit) {
        return retrofit.create(MainApi.class);
    }
}
