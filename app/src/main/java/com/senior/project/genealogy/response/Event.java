package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Event implements Serializable {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("date")
    @Expose
    private String date;

    @SerializedName("branch_id")
    @Expose
    private Integer branchId;

    @SerializedName("user_id_created")
    @Expose
    private Integer userIdCreated;

    public Event() {
    }

    public Event(String title, String content, String date, Integer branchId, Integer userIdCreated) {
        this.title = title;
        this.content = content;
        this.date = date;
        this.branchId = branchId;
        this.userIdCreated = userIdCreated;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getBranchId() {
        return branchId;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public Integer getUserIdCreated() {
        return userIdCreated;
    }

    public void setUserIdCreated(Integer userIdCreated) {
        this.userIdCreated = userIdCreated;
    }
}
