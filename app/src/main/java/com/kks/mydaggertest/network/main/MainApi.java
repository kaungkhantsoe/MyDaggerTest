package com.kks.mydaggertest.network.main;

import com.kks.mydaggertest.models.Post;

import java.util.List;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by kaungkhantsoe on 2020-02-16.
 **/
public interface MainApi {

    @GET("/posts")
    Flowable<List<Post>> getPostFromUser(
            @Query("userId") int id
    );
}
