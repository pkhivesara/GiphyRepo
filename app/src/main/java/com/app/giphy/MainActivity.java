package com.app.giphy;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements MainActivityFragment.MainActivityFragmentPresenterInterface {
    MainActivityFragment mainActivityFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolBar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolBar);
        mainActivityFragment = MainActivityFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.mainFrameLayout,mainActivityFragment);
        fragmentTransaction.commit();
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void gridItemClicked(GifsData.DataObject trendingGifObject) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra("trendingGifObjectURL", trendingGifObject.imagesObject.fixedHeight.url);
        intent.putExtra("trendingGifObjectString", trendingGifObject.bitly_gif_url);
        startActivity(intent);


    }
}
