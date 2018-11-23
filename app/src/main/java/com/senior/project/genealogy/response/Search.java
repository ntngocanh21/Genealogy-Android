package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Search{

    @SerializedName("name")
    @Expose
    private String name;

    public Search(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
