package com.bhavaniprasad.recyclerview.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;



public class RepositoryResponse {

    @SerializedName("items")
    private List<Repository> items;
    public List<Repository> getItems() {
        return items;
    }

}
