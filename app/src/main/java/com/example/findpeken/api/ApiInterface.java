package com.example.findpeken.api;

import com.example.findpeken.model.UserModel;
import com.example.findpeken.model.Value;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("pasar")
    Call<Value> view();

    @GET("user")
    Call<UserModel> viewuser();
}
