package com.app.giphy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.squareup.picasso.Picasso;

/**
 * Created by Pratik on 6/17/16.
 */
public class DetailsFragment extends Fragment {


    @Bind(R.id.details_gif_image_view)
    ImageView detailsGifImageView;

    @Bind(R.id.details_url_text_view)
    TextView detailsUrlTextView;

    public static DetailsFragment newInstance(String url, String text) {
        DetailsFragment detailsFragment = new DetailsFragment();
        Bundle args = new Bundle();
        args.putString("trendingGifObjectURL", url);
        args.putString("trendingGifObjectString", text);
        detailsFragment.setArguments(args);
        return detailsFragment;

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_details, container, false);
        ButterKnife.bind(this, view);
        String trendingGifObjectURL = getArguments().getString("trendingGifObjectURL");
        String trendingGifObjectString = getArguments().getString("trendingGifObjectString");
        Picasso.with(getActivity()).load(trendingGifObjectURL).into(detailsGifImageView);
        detailsUrlTextView.setText(trendingGifObjectURL + ", " + " " + trendingGifObjectString);
        return view;

    }


}
