package com.kks.mydaggertest.di.auth;

import androidx.lifecycle.ViewModel;

import com.kks.mydaggertest.di.ViewModelKey;
import com.kks.mydaggertest.ui.auth.AuthViewModel;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;

/**
 * Created by kaungkhantsoe on 2020-02-02.
 **/

@Module
public abstract class AuthViewModelsModule {

    @Binds
    @IntoMap
    @ViewModelKey(AuthViewModel.class)
    public abstract ViewModel bindAuthViewModel(AuthViewModel authViewModel);
}
