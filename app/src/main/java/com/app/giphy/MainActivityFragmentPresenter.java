package com.app.giphy;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;


public class MainActivityFragmentPresenter {

    MainFragmentPresenterInterface mainFragmentPresenterInterface;
    ServiceHelper serviceHelper;

    public MainActivityFragmentPresenter(MainFragmentPresenterInterface mainFragmentPresenterInterface) {
        this.mainFragmentPresenterInterface = mainFragmentPresenterInterface;
        serviceHelper = new ServiceHelper();

        serviceHelper.getTrendingGifs();
        mainFragmentPresenterInterface.showLoadingSpinner();

    }

    public void onResume() {
        EventBus.getDefault().register(this);
    }

    public void onPause() {
        EventBus.getDefault().unregister(this);
    }

    public interface MainFragmentPresenterInterface {
        void trendingGifsList(List<GifsData.DataObject> trendingGifsDataList);

        void searchedGifsList(List<GifsData.DataObject> searchGifsData);

        void showLoadingSpinner();

        void hideLoadingSpinner();
    }

    public void searchForAnimatedGifs(String searchString) {
        serviceHelper.getSearchedGifs(searchString);
    }

    @Subscribe
    public void gifsEventRecieved(MessageEvent gifsData) {
        if (!gifsData.isTrending()) {
            mainFragmentPresenterInterface.searchedGifsList(gifsData.getSearchedGifs());
        } else {
            mainFragmentPresenterInterface.trendingGifsList(gifsData.getSearchedGifs());
        }
        mainFragmentPresenterInterface.hideLoadingSpinner();
    }


}
