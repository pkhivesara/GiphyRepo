package com.app.giphy;

import android.app.SearchManager;
import android.app.Service;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.*;
import android.widget.ImageView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements MainActivityFragmentPresenter.MainFragmentPresenterInterface{


    @Bind(R.id.gifsList)
    RecyclerView recyclerView;

    MainActivityFragmentPresenter mainActivityFragmentPresenter;
    MainActivityFragmentPresenterInterface mainActivityFragmentPresenterInterface;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_main, container, false);
        mainActivityFragmentPresenter = new MainActivityFragmentPresenter(this);

        ButterKnife.bind(this, view);
        setUpRecyclerView();
        return view;

    }

    private void setUpRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.addItemDecoration(new DividerDecoration(getActivity()));
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mainActivityFragmentPresenterInterface = (MainActivityFragmentPresenterInterface) context;
    }

    public static MainActivityFragment newInstance() {
        return new MainActivityFragment();
    }

    @Override
    public void onResume() {
        mainActivityFragmentPresenter.onResume();
        super.onResume();

    }


    @Override
    public void onPause() {
        mainActivityFragmentPresenter.onPause();
        super.onPause();

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_main, menu);


    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

        final SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String searchString) {
                mainActivityFragmentPresenter.searchForAnimatedGifs(searchString);

                return true;
            }
            @Override
            public boolean onQueryTextChange(String textChange) {
                //searchView.setSuggestionsAdapter(new ExampleAdapter(context,yourData));

                return true;

            }
        };

        searchView.setOnQueryTextListener(queryTextListener);

    }

    @Override
    public void trendingGifsList(List<TrendingGifsData.DataObject> trendingGifsDataList) {
        setupAdapter(trendingGifsDataList);
    }

    private void setupAdapter(List<TrendingGifsData.DataObject> trendingGifsDataList){
        GifsAdapter gifsAdapter = new GifsAdapter(trendingGifsDataList);
        recyclerView.setAdapter(gifsAdapter);
    }


    public class GifsAdapter extends RecyclerView.Adapter<GifsAdapter.MainViewHolder>{
        List<TrendingGifsData.DataObject> trendingGifsListData;

        public GifsAdapter(List<TrendingGifsData.DataObject> trendingGifsListData){
            this.trendingGifsListData = trendingGifsListData;
        }
        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row, parent, false);
            return new MainViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            String imageURL = trendingGifsListData.get(position).url;
            Picasso.with(getActivity()).load(imageURL).into(holder.gifsImageView);
        }

        @Override
        public int getItemCount() {
            return trendingGifsListData.size();
        }

        public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            @Bind(R.id.gifs_image_view)
            ImageView gifsImageView;


            public MainViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
            }

            @Override
            public void onClick(View v) {
                mainActivityFragmentPresenterInterface.gridItemClicked(trendingGifsListData.get(getAdapterPosition()));

            }
        }
    }

    public interface MainActivityFragmentPresenterInterface {
        void gridItemClicked(TrendingGifsData.DataObject trendingGifObject);
    }
}
