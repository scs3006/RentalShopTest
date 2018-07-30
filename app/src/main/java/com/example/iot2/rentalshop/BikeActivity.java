package com.example.iot2.rentalshop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

public class BikeActivity extends AppCompatActivity {

    private String rentalShopNo;
    private List<Bike> mBikes = new ArrayList<>();
    private ListView mBikeListView;
    private BikeListAdapter mBikeListAdapter;
    private TextView mLogText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bike);

        mLogText = findViewById(R.id.log);
        mBikeListAdapter = new BikeListAdapter(mBikes, this, R.layout.bike_list_view);
        mBikeListView = findViewById(R.id.bike_list);
        mBikeListView.setAdapter(mBikeListAdapter);

        Intent intent = getIntent();
        rentalShopNo = intent.getStringExtra("rentalShopNo");
                //Integer.parseInt(intent.getStringExtra("rentalShopNo"));

        loadBikes();

        //aaa=findViewById(R.id.aaa);
        //aaa.setText(rentalShopNo);

    }

    private void loadBikes() {
        Thread t = new Thread() {
            public void run() {
                try {
                    int mRentalShopNo = Integer.parseInt(rentalShopNo);
                    String serverUrl = String.format("http://172.16.6.13:8080/bikelong/mobile_bike.action?rentalShopNo=%d", mRentalShopNo);

                    URL url = new URL(serverUrl);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    final int responseCode = con.getResponseCode();

                    Log.e("갔다오기 성공", ".");

                    if (responseCode == 200) {
                        processResult(con.getInputStream());
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getApplicationContext(),
                                        "error " + responseCode, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }


                } catch (Exception e){
                    System.out.println(e);
                }
            }
        };
        t.start();
    }

    private void processResult(InputStream inputStream) {
        mBikes.clear();

        try {
            InputStreamReader reader = new InputStreamReader(inputStream);
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
            Bike[] bikes = gson.fromJson(reader, Bike[].class);

            for(Bike bike : bikes) {
                mBikes.add(bike);
            }

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mBikeListAdapter.notifyDataSetChanged();
                }
            });

            //Log.e("dd", mBikes.get(0).getRentalShopName());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void loadBikeView() {

    }
}
