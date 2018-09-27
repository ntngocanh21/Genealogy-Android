package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;



public class LoginResponse {

    @SerializedName("error")
    @Expose
    private Message error;

    @SerializedName("token")
    @Expose
    private String token;

    public Message getError() {
        return error;
    }

    public void setError(Message message) {
        this.error = error;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
