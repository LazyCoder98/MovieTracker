package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RatingDisplay extends AppCompatActivity implements RecyclerViewClickInterface {
    //    Declaring Variables
    String selectedMovie, passImgUrl;
    String movieID;
    String url;
    RecyclerView recyclerView;
    RecyclViewAdapater adapter;
    ArrayList<MovieObject> onlineMovieList;
    JSONArray jsonarray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating_display);

//        Initializing variables
        recyclerView = findViewById(R.id.ratingDisplayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new RecyclViewAdapater();
        recyclerView.setAdapter(adapter);
        onlineMovieList = new ArrayList<>();
        jsonarray = new JSONArray();
        Bundle bundle = getIntent().getExtras();
        selectedMovie = bundle.getString("selection");

        url = "https://imdb-api.com/en/API/SearchTitle/k_lm4w129j/" + selectedMovie;

        requestData();
        adapter.setData(onlineMovieList, this);
    }

//    method for requesting Data from API
    private void requestData() {
//        showing loading Screen
        ProgressDialog loadMessage = new ProgressDialog(this);
        loadMessage.setMessage("Loading....");
        loadMessage.show();

        RequestQueue requestQueue = Volley.newRequestQueue(this);
//Creating jason object request
        JsonObjectRequest jasonRequest = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    jsonarray = response.getJSONArray("results");
//                    getting JSON objects from Jason array
                    for (int i = 0; i < jsonarray.length(); i++) {
                        JSONObject movieObj = (JSONObject) jsonarray.get(i);
//                  extracting data from JSON  object and creating local movie object
                        MovieObject movie = new MovieObject();
                        movie.setTitle(movieObj.getString("title"));
                        movie.setID(movieObj.getString("id"));
                        movie.setPosterImg(movieObj.getString("image"));
                        onlineMovieList.add(movie);
                    }
//                    Sending data to GUI
                    adapter.notifyDataSetChanged();
                    loadMessage.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loadMessage.dismiss();
                Toast.makeText(RatingDisplay.this, "Error Occured ", Toast.LENGTH_SHORT);
            }
        });

        requestQueue.add(jasonRequest);
    }

//    Custom on click method for Recycler view items
    @Override
    public void onItemClick(int pos) {
        movieID = onlineMovieList.get(pos).getID();
        passImgUrl = onlineMovieList.get(pos).getPosterImg();
        Intent intent = new Intent(this, PopUp.class);
        intent.putExtra("id", movieID);
        intent.putExtra("img", passImgUrl);
        startActivity(intent);


    }
}