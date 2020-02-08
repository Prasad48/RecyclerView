package com.bhavaniprasad.recyclerview.remote;

import com.bhavaniprasad.recyclerview.model.Categories;
import com.bhavaniprasad.recyclerview.model.RepoResponse;
import com.bhavaniprasad.recyclerview.model.Repository;
import com.bhavaniprasad.recyclerview.model.RepositoryResponse;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;



public interface RepositoryApiService {

   @GET("/search/repositories")
   Call<RepositoryResponse> getRepositoryList(@QueryMap(encoded = false) Map<String, String> filter);

   @GET("/users/{user}/repos")
      Call<Repository> listRepos(@Path("user") String user);

   @GET("/repos/{user}/{repo}")
   Call<RepoResponse> currrepo(@Path("user") String user, @Path("repo") String repo);



}
