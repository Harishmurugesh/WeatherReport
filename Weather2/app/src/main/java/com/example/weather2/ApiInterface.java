package com.example.weather2;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {


    @GET("data/2.5/weather")
    Call<Report> getReport(@Query("lat") double lat , @Query("lon") double lon , @Query("appid") String API_key);


}
