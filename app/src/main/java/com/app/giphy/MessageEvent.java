package com.app.giphy;

import java.util.List;


public class MessageEvent {
    private List<GifsData.DataObject> searchedGifs;
    private boolean isTrending;


    public MessageEvent(List<GifsData.DataObject> searchedGifs, boolean isTrending){
        this.searchedGifs = searchedGifs;
        this.isTrending = isTrending;

    }



    public List<GifsData.DataObject> getSearchedGifs() {
        return searchedGifs;
    }
    public boolean isTrending() {
        return isTrending;
    }
}
