package com.example.iot2.rentalshop;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import java.util.List;

public class BikeListAdapter extends BaseAdapter {

    private List<Bike> mBikes;
    private Context mActivityContext;
    private int mResourceId;

    public BikeListAdapter(List<Bike> mBikes, Context mActivityContext, int mResourceId) {
        this.mBikes = mBikes;
        this.mActivityContext = mActivityContext;
        this.mResourceId = mResourceId;

    }

    @Override
    public int getCount() { return mBikes.size(); }

    @Override
    public Object getItem(int position) { return mBikes.get(position); }

    @Override
    public long getItemId(int position) { return mBikes.get(position).getBikeNo(); }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if(view == null) {
            view = View.inflate(mActivityContext, mResourceId, null);
        }

        Bike bike = mBikes.get(position);

       // ImageView imageView = view.
        return view;
    }

}
