package com.develop.eventmanagement;


import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RetrofitAPI {
    public static String BaseUrl = "http://cassiel.in/event/api/";
    Retrofit retrofit = null;
    public static String SignupToken = "token e74e5d9f1da545e:8559fbf04d3bb4d";
    public static String MyToken = "token e74e5d9f1da545e:8559fbf04d3bb4d";


    @FormUrlEncoded
    @POST("authentication/login")
    Call<LoginResp> LoginUrl(
            @Field("email") String username, @Field("password") String password
    );


    @FormUrlEncoded
    @POST("authentication/registration")
    Call<SignUpResp> createAccount(@Field("first_name") String fname,
                                   @Field("last_name") String lname,
                                   @Field("email") String email,
                                   @Field("password") String pass,
                                   @Field("phone") String mobile);


    @FormUrlEncoded
    @POST("events/eventpost")
    Call<SignUpResp> addEvent(@Field("title") String title,
                              @Field("description") String desc,
                              @Field("guest") String guest,
                              @Field("location") String location,
                              @Field("date") String date,
                              @Field("time") String time);

    @GET("events/eventlist")
    Call<List<EventRespModel>> getEventList();
}

