package com.senior.project.genealogy.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GenealogyAndBranchResponse {
    @SerializedName("error")
    @Expose
    private Message error;

    @SerializedName("genealogyList")
    @Expose
    private List<Genealogy> genealogyList;

    @SerializedName("branchList")
    @Expose
    private List<Branch> branchList;

    public Message getError() {
        return error;
    }

    public void setError(Message error) {
        this.error = error;
    }

    public List<Genealogy> getGenealogyList() {
        return genealogyList;
    }

    public void setGenealogyList(List<Genealogy> genealogyList) {
        this.genealogyList = genealogyList;
    }

    public List<Branch> getBranchList() {
        return branchList;
    }

    public void setBranchList(List<Branch> branchList) {
        this.branchList = branchList;
    }
}
