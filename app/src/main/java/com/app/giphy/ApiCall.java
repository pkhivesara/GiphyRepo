package com.app.giphy;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;


public interface ApiCall {
    @GET("/v1/gifs/trending")
    Call<TrendingGifsData> getTrendingGifs(@Query("api_key") String api_key);

    @GET("/v1/gifs/trending/search")
    Call<SearchGifsData> getSearchedGifs(@Query("q") String search_string, @Query("api_key") String api_key);
}


