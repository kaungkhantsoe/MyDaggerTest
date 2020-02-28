package com.kks.mydaggertest.network.auth;

import com.kks.mydaggertest.models.User;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by kaungkhantsoe on 2020-02-12.
 **/
public interface AuthApi {

    @GET("/users/{id}")
    Flowable<User> getUser(
            @Path("id") int id
    );
}
