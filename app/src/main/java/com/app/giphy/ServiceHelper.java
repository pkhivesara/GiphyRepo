package com.app.giphy;

import android.util.Log;
import org.greenrobot.eventbus.EventBus;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


public class ServiceHelper {

    public void getTrendingGifs() {
        EventBus.getDefault().hasSubscriberForEvent(GifsData.class);
        Call<GifsData> trendingGifsDataCall = RestWebClient.get().getTrendingGifs("dc6zaTOxFJmzC");
        trendingGifsDataCall.enqueue(new Callback<GifsData>() {
            @Override
            public void onResponse(Response<GifsData> response, Retrofit retrofit) {
                EventBus.getDefault().post(new MessageEvent((response.body().data),true));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("%%%%%", "retrofit failure");
            }
        });
    }

    public void getSearchedGifs(String searchString){
        Call<GifsData> searchGifsDataCall = RestWebClient.get().getSearchedGifs(searchString,"dc6zaTOxFJmzC");
        searchGifsDataCall.enqueue(new Callback<GifsData>() {
            @Override
            public void onResponse(Response<GifsData> response, Retrofit retrofit) {
                EventBus.getDefault().post(new MessageEvent((response.body().data),false));
            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("%%%%%", "retrofit failure");
            }
        });
    }


}