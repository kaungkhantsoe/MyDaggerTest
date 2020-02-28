package com.kks.mydaggertest.ui.main.profile;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kks.mydaggertest.R;
import com.kks.mydaggertest.models.User;
import com.kks.mydaggertest.ui.auth.AuthResource;
import com.kks.mydaggertest.viewmodels.ViewModelProviderFactory;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * Created by kaungkhantsoe on 2020-02-15.
 **/
public class ProfileFragment extends DaggerFragment {

    private static final String TAG = "ProfileFragment";

    private ProfileViewModel viewModel;

    @BindView(R.id.username)
    TextView tvUserName;

    @BindView(R.id.email)
    TextView tvEmail;

    @BindView(R.id.website)
    TextView tvWebsite;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onViewCreated: Profile fragment was created");

        ButterKnife.bind(this,view);
        viewModel = new ViewModelProvider(this,providerFactory).get(ProfileViewModel.class);

        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.getAuthenticatedUser().removeObservers(getViewLifecycleOwner());
        viewModel.getAuthenticatedUser().observe(getViewLifecycleOwner(), new Observer<AuthResource<User>>() {
            @Override
            public void onChanged(AuthResource<User> userAuthResource) {
                if (userAuthResource != null)
                    switch (userAuthResource.status) {
                        case AUTHENTICATED:
                            setUserDetail(userAuthResource.data);
                            break;
                        case NOT_AUTHENTICATED:
                            break;
                        case LOADING:
                            break;
                        case ERROR:
                            setErrorDetail(userAuthResource.message);
                            break;
                    }
            }
        });
    }

    private void setErrorDetail(String message) {
        tvEmail.setText(message);
        tvUserName.setText("Error");
        tvWebsite.setText("Error");
    }

    private void setUserDetail(User data) {

        Log.e(TAG, "setUserDetail: " + data.getUsername() );
        tvUserName.setText(data.getUsername());
        tvEmail.setText(data.getEmail());
        tvWebsite.setText(data.getWebsite());
    }

}
