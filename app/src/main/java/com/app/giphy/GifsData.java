package com.app.giphy;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GifsData {

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

        @SerializedName("images")
        public ImagesObject imagesObject;

            public class ImagesObject{
                @SerializedName("fixed_height")
                public FixedHeight fixedHeight;

                    public class FixedHeight{
                        String url;
                    }
            }


    }
}
