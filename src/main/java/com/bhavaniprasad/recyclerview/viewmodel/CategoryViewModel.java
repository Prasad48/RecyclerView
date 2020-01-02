package com.bhavaniprasad.recyclerview.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bhavaniprasad.recyclerview.MainActivity;
import com.bhavaniprasad.recyclerview.R;
import com.bhavaniprasad.recyclerview.adapter.RepositoriesAdapter;
import com.bhavaniprasad.recyclerview.model.Categories;
import com.bhavaniprasad.recyclerview.model.Repository;
import com.bhavaniprasad.recyclerview.model.RepositoryResponse;
import com.bhavaniprasad.recyclerview.remote.RepositoryApiMaker;
import com.bhavaniprasad.recyclerview.remote.RepositoryApiService;
import com.bhavaniprasad.recyclerview.utils.APIError;
import com.bhavaniprasad.recyclerview.utils.ErrorUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import androidx.databinding.BindingAdapter;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryViewModel extends ViewModel {
    public String id="";
    public String title="";
    public String description="";
    public String imagepath="";
    RepositoryResponse repositoriesList;
    public RepositoriesAdapter adapter;
    private RecyclerView recyclerView = null;


    public MutableLiveData<ArrayList<Repository>> arrayListMutableLiveData = new MutableLiveData<>();
    private ArrayList<CategoryViewModel> arrayList;
    private ArrayList<Repository> arrlist;
    public String getImageUrl(){
        return imagepath;
    }

    @BindingAdapter({"imageurl"})

    public static void loadImage(ImageView imageView, String imageUrl){
        Picasso.with(imageView.getContext()).load(imageUrl).placeholder(R.drawable.user).into(imageView);
    }

    public CategoryViewModel(Categories categories) {
        this.id = categories.id;
        this.title = categories.title;
        this.description = categories.description;
        this.imagepath = categories.imagepath;
    }

    public CategoryViewModel() {
    }

    public MutableLiveData<ArrayList<Repository>> getMutableLiveData(final Context context) {
        arrlist=new ArrayList<>();
        Map<String, String> data = new HashMap<>();
        data.put("q", "created:>2017-10-22");
        data.put("sort", "stars");
        data.put("order", "desc");
        RepositoryApiService apiService = new RepositoryApiMaker().getService();
        Call<RepositoryResponse> repositoryListCall= apiService.getRepositoryList(data);
        repositoryListCall.enqueue(new Callback<RepositoryResponse>() {
            @Override
            public void onResponse(Call<RepositoryResponse> call, Response<RepositoryResponse> response) {
                if (response.isSuccessful()) {
//                    Toast.makeText(MainActivity.this,
//                            " Sucessful",
//                            Toast.LENGTH_SHORT).show();
                    arrlist=(ArrayList<Repository>) response.body().getItems();
                    arrayListMutableLiveData.setValue(arrlist);

//                    prepareData(repositoriesList);

                } else {

                    APIError error = ErrorUtils.parseError(response);
                    Log.d("error message", error.message());
                }
            }

            @Override
            public void onFailure(Call<RepositoryResponse> call, Throwable t) {
                showToastMethod(context);
            }
        });
//        Categories categories = new Categories("1","title1","desc1","image1.png");
//        CategoryViewModel categoryViewModel = new CategoryViewModel(categories);
//        arrayList.add(categoryViewModel);
//        arrayListMutableLiveData.setValue(arrayList);

        return arrayListMutableLiveData;
    }

    private void fetchFirstPage(final Context context) {

    }

    public void showToastMethod(Context context) {
        Toast.makeText(context, "mymessage ", Toast.LENGTH_SHORT).show();
    }

    private void prepareData(RepositoryResponse repositoriesList) {
        adapter = new RepositoriesAdapter(repositoriesList.getItems());
        recyclerView.setAdapter(adapter);
    }
}
