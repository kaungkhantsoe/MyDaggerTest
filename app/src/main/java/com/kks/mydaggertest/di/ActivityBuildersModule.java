package com.kks.mydaggertest.di;

import com.kks.mydaggertest.di.auth.AuthModule;
import com.kks.mydaggertest.di.auth.AuthScope;
import com.kks.mydaggertest.di.auth.AuthViewModelsModule;
import com.kks.mydaggertest.di.main.MainFragmentBuildersModule;
import com.kks.mydaggertest.di.main.MainModule;
import com.kks.mydaggertest.di.main.MainScope;
import com.kks.mydaggertest.di.main.MainViewModelsModule;
import com.kks.mydaggertest.ui.auth.AuthActivity;
import com.kks.mydaggertest.ui.main.MainActivity;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by kaungkhantsoe on 2020-02-02.
 **/

@Module
public abstract class ActivityBuildersModule {

    @AuthScope
    @ContributesAndroidInjector(
            modules = {
                    AuthViewModelsModule.class,
                    AuthModule.class,
            }
    )
    abstract AuthActivity contributeAuthActivity();

    @MainScope
    @ContributesAndroidInjector(
            modules = {
                    MainViewModelsModule.class,
                    MainFragmentBuildersModule.class,
                    MainModule.class
            }
    )
    abstract MainActivity contributeMainActivity();
}
