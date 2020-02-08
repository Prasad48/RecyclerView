package com.bhavaniprasad.recyclerview.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;



public class RepositoryResponse {

    @SerializedName("items")
    private List<Repository> items;

//    @SerializedName("owner")
//    @Expose
//    private List<Repository> owner;

    public List<Repository> getItems() {
        return items;
    }

//    public  List<Repository> getOwner() {
//        return owner;
//    }
}
