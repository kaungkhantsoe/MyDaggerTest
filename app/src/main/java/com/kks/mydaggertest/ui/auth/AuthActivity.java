package com.kks.mydaggertest.ui.auth;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bumptech.glide.RequestManager;
import com.kks.mydaggertest.R;
import com.kks.mydaggertest.util.DebouncedOnClickListener;
import com.kks.mydaggertest.models.User;
import com.kks.mydaggertest.ui.main.MainActivity;
import com.kks.mydaggertest.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerAppCompatActivity;

public class AuthActivity extends DaggerAppCompatActivity {

    private static final String TAG = "AuthActivity";

    @BindView(R.id.login_logo)
    ImageView loginLogo;

    @BindView(R.id.user_id_input)
    EditText userIdInput;

    @BindView(R.id.login_button)
    Button loginButton;

    private ProgressBar progressBar;

    private AuthViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    Drawable logo;

    @Inject
    RequestManager requestManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        ButterKnife.bind(this);

        loginButton.setOnClickListener(new DebouncedOnClickListener(1000) {
            @Override
            public void onDebouncedClick(View v) {
                switch (v.getId()) {
                    case R.id.login_button:
                        attemptLogin();
                        break;
                }
            }
        });

        progressBar = findViewById(R.id.progress_bar);

        viewModel = new ViewModelProvider(this, providerFactory).get(AuthViewModel.class);

        setLogo();

        subscribeObservers();
    }

    private void subscribeObservers() {

        viewModel.observeAuthState().observe(this, new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null) {

                    switch (userAuthResource.status) {
                        case ERROR:
                            Toast.makeText(AuthActivity.this,"Error Authentication",Toast.LENGTH_LONG).show();
                            showProgressBar(false);
                            break;
                        case LOADING:
                            showProgressBar(true);
                            break;
                        case AUTHENTICATED:
                            Log.d(TAG, "onChanged: Authenticated");
                            onLoginSuccess();
                            showProgressBar(false);
                            break;
                        case NOT_AUTHENTICATED:
                            showProgressBar(false);
                            break;
                    }
                }
            }
        });
    }

    private void onLoginSuccess() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void showProgressBar(boolean isVisible) {

        if (isVisible)
            progressBar.setVisibility(View.VISIBLE);

        else
            progressBar.setVisibility(View.GONE);

    }

    private void setLogo() {
        requestManager
                .load(logo)
                .into(loginLogo);
    }

    private void attemptLogin() {
        if (TextUtils.isEmpty(userIdInput.getText().toString())) {
            return;
        }
        viewModel.authenticateWithId(Integer.parseInt(userIdInput.getText().toString()));
    }
}
