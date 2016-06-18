package com.app.giphy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * Created by Pratik on 6/16/16.
 */
public class MainActivityFragmentPresenter {

    MainFragmentPresenterInterface mainFragmentPresenterInterface;
    ServiceHelper serviceHelper;

    public MainActivityFragmentPresenter(MainFragmentPresenterInterface mainFragmentPresenterInterface) {
        this.mainFragmentPresenterInterface = mainFragmentPresenterInterface;
        serviceHelper = new ServiceHelper();

        serviceHelper.getTrendingGifs();

    }

    public void onResume() {
        EventBus.getDefault().register(this);
    }

    public void onPause() {
        EventBus.getDefault().unregister(this);
    }

    public interface MainFragmentPresenterInterface {
        void trendingGifsList(List<TrendingGifsData.DataObject> trendingGifsDataList);
    }

    public void searchForAnimatedGifs(String searchString){
        serviceHelper.getSearchedGifs(searchString);
    }

    @Subscribe
    public void onEvent(TrendingGifsData trendingGifsData) {
        mainFragmentPresenterInterface.trendingGifsList(trendingGifsData.data);

    }


}
