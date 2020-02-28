package com.kks.mydaggertest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;

import com.kks.mydaggertest.models.User;
import com.kks.mydaggertest.ui.auth.AuthActivity;
import com.kks.mydaggertest.ui.auth.AuthResource;

import javax.inject.Inject;

import dagger.android.support.DaggerAppCompatActivity;

/**
 * Created by kaungkhantsoe on 2020-02-15.
 **/
public abstract class BaseActivity extends DaggerAppCompatActivity {

    private static final String TAG = "BaseActivity";

    @Inject
    public SessionManager sessionManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        subscribeObserver();
    }

    private void subscribeObserver() {
        sessionManager.getAuthUser().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {

                if (userAuthResource != null) {

                    switch (userAuthResource.status) {
                        case ERROR:
                            Toast.makeText(BaseActivity.this, "Error Authentication", Toast.LENGTH_LONG).show();
                            break;
                        case LOADING:
                            break;
                        case AUTHENTICATED:
                            Log.d(TAG, "onChanged: Authenticated");
                            break;
                        case NOT_AUTHENTICATED:
                            navLoginScreen();
                            break;
                    }
                }
            }
        });
    }

    private void navLoginScreen() {
        Intent intent = new Intent(this,AuthActivity.class);
        startActivity(intent);
        finish();
    }
}
