package com.kks.mydaggertest.di.main;

import androidx.lifecycle.ViewModel;

import com.kks.mydaggertest.di.ViewModelKey;
import com.kks.mydaggertest.ui.main.posts.PostsViewModel;
import com.kks.mydaggertest.ui.main.profile.ProfileViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by kaungkhantsoe on 2020-02-15.
 **/
@Module
public abstract class MainViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel.class)
    public abstract ViewModel bindProfileViewModel(ProfileViewModel viewModel);

    @Binds
    @IntoMap
    @ViewModelKey(PostsViewModel.class)
    public abstract ViewModel bindPostsViewModel(PostsViewModel viewModel);
}
