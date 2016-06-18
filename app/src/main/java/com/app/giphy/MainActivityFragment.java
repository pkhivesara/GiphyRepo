package com.app.giphy;

import android.app.ProgressDialog;
import android.app.SearchManager;
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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment implements MainActivityFragmentPresenter.MainFragmentPresenterInterface {


    @Bind(R.id.gifsList)
    RecyclerView recyclerView;

    MainActivityFragmentPresenter mainActivityFragmentPresenter;
    MainActivityFragmentPresenterInterface mainActivityFragmentPresenterInterface;
    List<GifsData.DataObject> trendingGifsTemporaryListData;
    GifsAdapter gifsAdapter;
    ProgressDialog progressDialog;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mainActivityFragmentPresenter = new MainActivityFragmentPresenter(this);

        ButterKnife.bind(this, view);
        setUpRecyclerView();
        return view;

    }

    private void setUpRecyclerView() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
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

        super.onResume();

    }


    @Override
    public void onPause() {

        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();
        mainActivityFragmentPresenter.onPause();
    }

    @Override
    public void onStart() {
        mainActivityFragmentPresenter.onResume();
        super.onStart();

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
                String query = null;
                try {
                    query = URLEncoder.encode(searchString, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e(MainActivityFragmentPresenter.class.getSimpleName(),e.toString());
                }
                mainActivityFragmentPresenter.searchForAnimatedGifs(query);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String textChange) {
                String query = null;
                try {
                    query = URLEncoder.encode(textChange, "utf-8");
                } catch (UnsupportedEncodingException e) {
                    Log.e(MainActivityFragmentPresenter.class.getSimpleName(),e.toString());
                }
                mainActivityFragmentPresenter.searchForAnimatedGifs(query);
                return true;

            }
        };

        searchView.setOnQueryTextListener(queryTextListener);

    }

    @Override
    public void trendingGifsList(List<GifsData.DataObject> trendingGifsDataList) {
        setupAdapter(trendingGifsDataList);
    }

    @Override
    public void searchedGifsList(List<GifsData.DataObject> searchGifsData) {
        trendingGifsTemporaryListData.clear();
        trendingGifsTemporaryListData.addAll(searchGifsData);
        gifsAdapter.notifyDataSetChanged();


    }

    @Override
    public void showLoadingSpinner() {
        progressDialog= new ProgressDialog(getActivity());
        progressDialog.setMessage(getActivity().getString(R.string.fetching_images));
        progressDialog.show();
    }

    @Override
    public void hideLoadingSpinner() {
        progressDialog.dismiss();

    }

    private void setupAdapter(List<GifsData.DataObject> trendingGifsDataList) {
        gifsAdapter = new GifsAdapter(trendingGifsDataList);
        recyclerView.setAdapter(gifsAdapter);
    }


    public class GifsAdapter extends RecyclerView.Adapter<GifsAdapter.MainViewHolder> {


        public GifsAdapter(List<GifsData.DataObject> trendingGifsListData) {
            trendingGifsTemporaryListData = trendingGifsListData;
        }

        @Override
        public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_row, parent, false);
            return new MainViewHolder(v);
        }

        @Override
        public void onBindViewHolder(MainViewHolder holder, int position) {
            String imageURL = trendingGifsTemporaryListData.get(position).imagesObject.fixedHeight.url;
            Picasso.with(getActivity()).load(imageURL).into(holder.gifsImageView);
        }

        @Override
        public int getItemCount() {
            return trendingGifsTemporaryListData.size();
        }

        public class MainViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

            @Bind(R.id.gifs_image_view)
            ImageView gifsImageView;


            public MainViewHolder(View itemView) {
                super(itemView);
                ButterKnife.bind(this, itemView);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                Log.d("#######", "clicked recycler");
                mainActivityFragmentPresenterInterface.gridItemClicked(trendingGifsTemporaryListData.get(getAdapterPosition()),view);

            }
        }
    }

    public interface MainActivityFragmentPresenterInterface {
        void gridItemClicked(GifsData.DataObject trendingGifObject, View view);
    }
}
