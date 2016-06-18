package com.app.giphy;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Pratik on 6/17/16.
 */
public class SearchGifsData {


    @SerializedName("data")
    public List<DataObject> data;


    public class DataObject {
        String type;
        String id;
        String url;
        String bitly_gif_url;
        String bitly_url;
        String embed_url;
        String source;
        String content_url;
        String source_post_url;
        String rating;

    }
}
