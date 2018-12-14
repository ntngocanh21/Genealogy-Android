package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Notification {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("content")
    @Expose
    private String content;

    @SerializedName("deviceId")
    @Expose
    private String deviceId;

    @SerializedName("isPushed")
    @Expose
    private Integer isPushed;

    @SerializedName("username")
    @Expose
    private String username;

    public Notification() {
    }

    public Notification(Integer id, String title, String type, String content, String deviceId, Integer isPushed, String username) {
        this.id = id;
        this.title = title;
        this.type = type;
        this.content = content;
        this.deviceId = deviceId;
        this.isPushed = isPushed;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Integer getIsPushed() {
        return isPushed;
    }

    public void setIsPushed(Integer isPushed) {
        this.isPushed = isPushed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}
