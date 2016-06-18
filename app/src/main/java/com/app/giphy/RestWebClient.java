package com.app.giphy;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;
import retrofit.GsonConverterFactory;
import retrofit.Retrofit;

/**
 * Created by Pratik on 6/17/16.
 */
public class RestWebClient {

    private static ApiCall REST_CLIENT;
    private static String URL =
            "http://api.giphy.com/";

    static {
        setupRestClient();
    }


    public static ApiCall get() {
        return REST_CLIENT;
    }

    private static void setupRestClient() {

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient();
        httpClient.interceptors().add(logging);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        REST_CLIENT = retrofit.create(ApiCall.class);
    }
}