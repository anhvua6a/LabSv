package com.example.sv402;

import com.example.sv402.model.User;

import com.example.sv402.model.serverResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface apiSever {
    @FormUrlEncoded
    @POST("api/login")
    Call<serverResponse> login(@Field("username") String username,
             @Field("password") String password);

    @FormUrlEncoded
    @POST("api/register")
    Call<serverResponse> register(@Field("username") String username,
                                  @Field("password") String password,
                                  @Field("name") String name,
                                  @Field("age") int age,
                                  @Field("address") String address);

    @GET("api/users")
    Call<serverResponse> getAllUser();

    @GET("api/user/delete/{id}")
    Call<serverResponse> deleteUser(@Path("id") String id);

    @FormUrlEncoded
    @POST("api/user/update/{id}")
    Call<serverResponse> updateUser(@Path("id") String id,
                                    @Field("username") String username,
                                  @Field("password") String password,
                                  @Field("name") String name,
                                  @Field("age") int age,
                                  @Field("address") String address);
}
