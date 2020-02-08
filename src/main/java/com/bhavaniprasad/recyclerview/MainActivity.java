package com.bhavaniprasad.recyclerview;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bhavaniprasad.recyclerview.adapter.CustomAdapter;
import com.bhavaniprasad.recyclerview.interfaces.Ongitlistener;
import com.bhavaniprasad.recyclerview.model.Repository;
import com.bhavaniprasad.recyclerview.model.UserDetails;
import com.bhavaniprasad.recyclerview.viewmodel.CategoryViewModel;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.bhavaniprasad.recyclerview.remote.RepositoryApiMaker.BASE_URL;

public class MainActivity extends AppCompatActivity implements Ongitlistener {
    private CategoryViewModel categoryViewModel;
    private RecyclerView recyclerView;
    private CustomAdapter customAdapter;
    private ProgressBar progressBar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private Button btn_retry;
    private RelativeLayout layout_nonetwork;
    private ArrayList<Repository> trendingList;
    int cachesize;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        categoryViewModel = ViewModelProviders.of(this).get(CategoryViewModel.class);
        progressBar = findViewById(R.id.progress_bar);
        swipeRefreshLayout = findViewById(R.id.swipe_to_refresh);
        btn_retry = findViewById(R.id.retry);
        layout_nonetwork = findViewById(R.id.no_network);

//        if(!isNetworkAvailable()){
//            OkHttpClient client = new OkHttpClient();
//            client.networkInterceptors().add(REWRITE_CACHE_CONTROL_INTERCEPTOR);

//setup cache
//            File httpCacheDirectory = new File(context.getCacheDir(), "responses");
//            int cacheSize = 10 * 1024 * 1024; // 10 MiB
//            Cache cache = new Cache(getApplicationContext().getCacheDir(), cacheSize);

//add cache to the client
//            client.setCache(cache);
//            Retrofit retrofit = new Retrofit.Builder()
//                    .baseUrl(BASE_URL)
//                    .client(client)
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .build();
//        }

        categoryViewModel.getMutableLiveData(this).observe(this, new Observer<ArrayList<Repository>>() {
            @Override
            public void onChanged(ArrayList<Repository> categoryViewModels) {
                trendingList = categoryViewModels;
                if (trendingList.size() > 0) {
                    swipeRefreshLayout.setRefreshing(false);
                    layout_nonetwork.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);


                    customAdapter = new CustomAdapter(MainActivity.this, categoryViewModels, MainActivity.this);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerView.setAdapter(customAdapter);
                } else {
                    layout_nonetwork.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                }

            }
        });


        categoryViewModel.getShowProgressBar().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    progressBar.setVisibility(View.VISIBLE);
                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }
        });
        categoryViewModel.getShow_networkError().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    layout_nonetwork.setVisibility(View.VISIBLE);
                    swipeRefreshLayout.setVisibility(View.GONE);
                } else {
                    layout_nonetwork.setVisibility(View.GONE);
                    swipeRefreshLayout.setVisibility(View.VISIBLE);
                }
            }
        });
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                customAdapter.setRepositoryList(new ArrayList<Repository>());
                customAdapter.notifyDataSetChanged();
//                File cache_dir = getApplicationContext().getCacheDir();
//                boolean deleted = deleteCache(cache_dir);
//                if(deleted)
//                    dbManager.deleteTable();
                categoryViewModel.getMutableLiveData(MainActivity.this).observe(MainActivity.this, new Observer<ArrayList<Repository>>() {
                    @Override
                    public void onChanged(ArrayList<Repository> categoryViewModels) {
                        trendingList = categoryViewModels;
                        if (trendingList.size() > 0) {
                            swipeRefreshLayout.setRefreshing(false);
                            layout_nonetwork.setVisibility(View.GONE);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);


                            customAdapter = new CustomAdapter(MainActivity.this, categoryViewModels, MainActivity.this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            recyclerView.setAdapter(customAdapter);
                        } else {
                            layout_nonetwork.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });

        btn_retry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layout_nonetwork.setVisibility(View.GONE);
                swipeRefreshLayout.setVisibility(View.VISIBLE);
                categoryViewModel.getMutableLiveData(MainActivity.this).observe(MainActivity.this, new Observer<ArrayList<Repository>>() {
                    @Override
                    public void onChanged(ArrayList<Repository> categoryViewModels) {
                        trendingList = categoryViewModels;
                        if (trendingList.size() > 0) {
                            swipeRefreshLayout.setRefreshing(false);
                            layout_nonetwork.setVisibility(View.GONE);
                            swipeRefreshLayout.setVisibility(View.VISIBLE);


                            customAdapter = new CustomAdapter(MainActivity.this, categoryViewModels, MainActivity.this);
                            recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                            recyclerView.setAdapter(customAdapter);
                        } else {
                            layout_nonetwork.setVisibility(View.VISIBLE);
                            swipeRefreshLayout.setVisibility(View.GONE);
                        }

                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (isNetworkAvailable()) {
                    customAdapter.getFilter().filter(newText);
                    return false;
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public void onclickaction(int adapterPosition) {
        View row = recyclerView.getLayoutManager().findViewByPosition(adapterPosition);
        TextView username = row.findViewById(R.id.username);
        TextView desc = row.findViewById(R.id.description);
        Intent i = new Intent(this, RepoDetails.class);
        UserDetails instance = UserDetails.getInstance();
        instance.username=username.getText().toString();
        instance.description=desc.getText().toString();
        startActivity(i);
        Log.e("tag", "clciked" + adapterPosition);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            int maxStale=60 * 60 * 24 * 28;
            Response originalResponse = chain.proceed(chain.request());
            if (!isNetworkAvailable()) {
                 maxStale = 60 * 60 * 24 * 28; // tolerate 4-weeks stale
            }
            return originalResponse.newBuilder()
                    .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                    .build();
        }
    };
}
