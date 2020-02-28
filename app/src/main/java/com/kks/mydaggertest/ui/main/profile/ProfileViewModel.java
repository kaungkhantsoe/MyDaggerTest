package com.kks.mydaggertest.ui.main.profile;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.kks.mydaggertest.SessionManager;
import com.kks.mydaggertest.models.User;
import com.kks.mydaggertest.ui.auth.AuthResource;

import javax.inject.Inject;

/**
 * Created by kaungkhantsoe on 2020-02-15.
 **/
public class ProfileViewModel extends ViewModel {

    private static final String TAG = "ProfileViewModel";

    private SessionManager sessionManager;

    @Inject
    public ProfileViewModel(SessionManager sessionManager) {
        Log.d(TAG, "ProfileViewModel: ViewModel is ready....");

        this.sessionManager = sessionManager;
    }

    public LiveData<AuthResource<User>> getAuthenticatedUser() {
        return sessionManager.getAuthUser();
    }
}
