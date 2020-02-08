package com.bhavaniprasad.recyclerview.viewmodel;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.bhavaniprasad.recyclerview.RepoDetails;
import com.bhavaniprasad.recyclerview.model.RepoResponse;
import com.bhavaniprasad.recyclerview.model.Repository;
import com.bhavaniprasad.recyclerview.model.UserDetails;
import com.bhavaniprasad.recyclerview.remote.RepositoryApiMaker;
import com.bhavaniprasad.recyclerview.remote.RepositoryApiService;
import com.bhavaniprasad.recyclerview.utils.APIError;
import com.bhavaniprasad.recyclerview.utils.ErrorUtils;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RepoViewModel extends ViewModel {



    private UserDetails userDetails;
    public MutableLiveData<ArrayList<String>> repoUserdetails = new MutableLiveData<>();
    public MutableLiveData<ArrayList<RepoResponse>> repomutablelivedata = new MutableLiveData<>();
    private ArrayList<RepoResponse> arrlist;
    public MutableLiveData<Boolean> showProgressBar = new MutableLiveData<>();
    public MutableLiveData<Boolean> show_networkError = new MutableLiveData<>();


    public LiveData<Boolean> getShowProgressBar() {
        return showProgressBar;
    }


    public MutableLiveData<Boolean> getShow_networkError() {
        return show_networkError;
    }



    public MutableLiveData<ArrayList<RepoResponse>> getMutableLiveData(final Context context) {
        arrlist=new ArrayList<>();
        userDetails = UserDetails.getInstance();
        RepositoryApiService apiService = new RepositoryApiMaker().getService();
        Call<RepoResponse> repositoryListCall= apiService.currrepo(userDetails.username,userDetails.description);
        repositoryListCall.enqueue(new Callback<RepoResponse>() {
            @Override
            public void onResponse(Call<RepoResponse> call, Response<RepoResponse> response) {
                if (response.isSuccessful()) {
                    arrlist.add(new RepoResponse(response.body().getId(),response.body().getName(),response.body().getFull_name(),response.body().getOwner(),response.body().getContributors_url()));
                    repomutablelivedata.setValue(arrlist);
                } else {
                    APIError error = ErrorUtils.parseError(response);
                    Log.d("error message", error.message());
                }
            }

            @Override
            public void onFailure(Call<RepoResponse> call, Throwable t) {

                Toast.makeText(context, "error", Toast.LENGTH_SHORT).show();
                String error_message= t.getMessage();
                Log.d("Error loading data", error_message);
                showProgressBar.setValue(false);
                show_networkError.setValue(true);
            }

        });
        return repomutablelivedata;
    }
}
