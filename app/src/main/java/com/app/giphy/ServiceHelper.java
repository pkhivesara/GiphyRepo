package com.app.giphy;

import android.util.Log;
import org.greenrobot.eventbus.EventBus;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Pratik on 6/17/16.
 */
public class ServiceHelper {

    public void getTrendingGifs() {
        EventBus.getDefault().hasSubscriberForEvent(TrendingGifsData.class);
        Call<TrendingGifsData> trendingGifsDataCall = RestWebClient.get().getTrendingGifs("dc6zaTOxFJmzC");
        trendingGifsDataCall.enqueue(new Callback<TrendingGifsData>() {
            @Override
            public void onResponse(Response<TrendingGifsData> response, Retrofit retrofit) {

                EventBus.getDefault().post(response);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("%%%%%", "retrofit failure");
            }
        });
    }

    public void getSearchedGifs(String searchString){
        Call<SearchGifsData> searchGifsDataCall = RestWebClient.get().getSearchedGifs(searchString,"dc6zaTOxFJmzC");
        searchGifsDataCall.enqueue(new Callback<SearchGifsData>() {
            @Override
            public void onResponse(Response<SearchGifsData> response, Retrofit retrofit) {
                EventBus.getDefault().post(response);
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("%%%%%", "retrofit failure");
            }
        });
    }


}