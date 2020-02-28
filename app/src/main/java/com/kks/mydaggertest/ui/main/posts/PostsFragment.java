package com.kks.mydaggertest.ui.main.posts;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kks.mydaggertest.R;
import com.kks.mydaggertest.models.Post;
import com.kks.mydaggertest.ui.main.Resource;
import com.kks.mydaggertest.util.VerticalSpacingItemDecoration;
import com.kks.mydaggertest.viewmodels.ViewModelProviderFactory;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import dagger.android.support.DaggerFragment;

/**
 * Created by kaungkhantsoe on 2020-02-16.
 **/
public class PostsFragment extends DaggerFragment {

    private static final String TAG = "PostsFragment";

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    private PostsViewModel viewModel;

    @Inject
    ViewModelProviderFactory providerFactory;

    @Inject
    PostsRecyclerAdapter adapter;

    @Inject
    LinearLayoutManager linearLayoutManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_posts,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        ButterKnife.bind(this,view);

        viewModel = new ViewModelProvider(this,providerFactory).get(PostsViewModel.class);

        initRecyclerView();
        subscribeObservers();
    }

    private void subscribeObservers() {
        viewModel.observePosts().removeObservers(getViewLifecycleOwner());
        viewModel.observePosts().observe(getViewLifecycleOwner(), new Observer<Resource<List<Post>>>() {
            @Override
            public void onChanged(Resource<List<Post>> listResource) {

                if (listResource != null) {

                    switch (listResource.status) {
                        case ERROR:
                            Log.e(TAG, "onChanged: " + listResource.message );
                            break;
                        case LOADING:
                            Log.d(TAG, "onChanged: Loading....");
                            break;
                        case SUCCESS:
                            adapter.setPosts(listResource.data);
                            break;
                    }
                }
            }
        });
    }

    private void initRecyclerView() {
        VerticalSpacingItemDecoration verticalSpacingItemDecoration = new VerticalSpacingItemDecoration(15);
        recyclerView.addItemDecoration(verticalSpacingItemDecoration);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);
    }
}
