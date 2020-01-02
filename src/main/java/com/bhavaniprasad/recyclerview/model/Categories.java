package com.bhavaniprasad.recyclerview.model;

public class Categories {
    public String id="";
    public String title="";
    public String description="";
    public String imagepath="";

    public Categories(String id,String title, String description, String imagepath) {
        this.title = title;
        this.description = description;
        this.imagepath = imagepath;
    }

    public Categories() {
    }
}
