package com.kks.mydaggertest.di.main;

import com.kks.mydaggertest.ui.main.posts.PostsFragment;
import com.kks.mydaggertest.ui.main.profile.ProfileFragment;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by kaungkhantsoe on 2020-02-15.
 **/
@Module
public abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract ProfileFragment constributeProfileFragment();

    @ContributesAndroidInjector
    abstract PostsFragment contributePostFragment();
}
