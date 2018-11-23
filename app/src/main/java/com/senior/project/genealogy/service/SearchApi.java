package com.senior.project.genealogy.service;

import com.senior.project.genealogy.response.GenealogyAndBranchResponse;
import com.senior.project.genealogy.response.People;
import com.senior.project.genealogy.response.PeopleResponse;
import com.senior.project.genealogy.response.Search;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface SearchApi {

    @POST("/api/search/name")
    Call<GenealogyAndBranchResponse> searchGenealogyByName(@Body Search search, @Header("Authorisation") String token);

    @POST("/api/search/people")
    Call<PeopleResponse> searchGenealogyByPeople(@Body People people, @Header("Authorisation") String token);

}
