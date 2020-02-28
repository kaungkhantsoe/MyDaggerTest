package com.kks.mydaggertest.ui.auth;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.LiveDataReactiveStreams;
import androidx.lifecycle.ViewModel;

import com.kks.mydaggertest.SessionManager;
import com.kks.mydaggertest.models.User;
import com.kks.mydaggertest.network.auth.AuthApi;

import javax.inject.Inject;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by kaungkhantsoe on 2020-02-02.
 **/
public class AuthViewModel extends ViewModel {

    private static final String TAG = "AuthViewModel";

    private final AuthApi authApi;

    private SessionManager sessionManager;

    @Inject
    public AuthViewModel(AuthApi authApi, SessionManager sessionManager) {

        this.authApi = authApi;
        this.sessionManager = sessionManager;
        Log.e(TAG, "AuthViewModel: view model is working...");

    }

    public void authenticateWithId(int userId) {
        Log.d(TAG, "authenticateWithId: Attempting to login....");
        sessionManager.authenticatWithId(queryUserId(userId));
    }

    private LiveData<AuthResource<User>> queryUserId(int userId) {

        return LiveDataReactiveStreams.fromPublisher(
                authApi.getUser(userId)
                        .onErrorReturn(new Function<Throwable, User>() {
                            @Override
                            public User apply(Throwable throwable) throws Exception {
                                User errorUser = new User();
                                errorUser.setId(-1);
                                return errorUser;
                            }
                        })

                        .map(new Function<User, AuthResource<User>>() {

                            @Override
                            public AuthResource<User> apply(User user) throws Exception {

                                if (user.getId() == -1) {
                                    return AuthResource.error("Cound not authenticate", (User) null);
                                }
                                return AuthResource.authenticated(user);
                            }
                        })

                        .subscribeOn(Schedulers.io())
        );

    }

    public LiveData<AuthResource<User>> observeAuthState() {

        return sessionManager.getAuthUser();
    }
}
