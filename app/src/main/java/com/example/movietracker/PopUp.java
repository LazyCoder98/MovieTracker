package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;


import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class PopUp extends AppCompatActivity {

    //     declaring variables
    ImageView moviePoster;
    TextView dispTitle, dispRating;

    String Url, selectedMovieID, selectedImgUrl;
    String Title;
    String Rating;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pop_up);

//        Initializing variables
        DisplayMetrics dispSize = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dispSize);
        int width = dispSize.widthPixels;
        int height = dispSize.heightPixels;
        getWindow().setLayout((int) (width * .8), (int) (height * .6));

        moviePoster = findViewById(R.id.recivedImage);
        dispTitle = findViewById(R.id.recivedMovieTitle);
        dispRating = findViewById(R.id.recivedRating);

        Bundle bundle = getIntent().getExtras();
        selectedMovieID = bundle.getString("id");
        selectedImgUrl = bundle.getString("img");

        Url = "https://imdb-api.com/API/Ratings/k_lm4w129j/" + selectedMovieID;



        requestSelectedMovie();


    }

    private void requestSelectedMovie() {

        BackgroundProcesses test = new BackgroundProcesses();
        test.execute();
    }


    //class for running aync processes on a diffent thread
    public class BackgroundProcesses extends AsyncTask<Void, Void, Void> {

        //        overriding method to send get request from API
        @Override
        protected Void doInBackground(Void... voids) {

            RequestQueue requestQueue = Volley.newRequestQueue(PopUp.this);

//            requesting JSON object from API
            JsonObjectRequest jasonRequest = new JsonObjectRequest(Request.Method.GET, Url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    try {
                        //getting data from Jason Object
                        Title = response.getString("fullTitle");
                        Rating = response.getString("imDb");
                        if (Rating.equals("null")) {
                            dispRating.setText("No Rating ");
                        }

//                        Setting data into gui elements
                        dispTitle.setText(Title);
                        dispRating.setText("Rating : " + Rating);
//                        loadMessage.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
//                handling errors
                public void onErrorResponse(VolleyError error) {
//                    loadMessage.dismiss();
                    Toast.makeText(PopUp.this, "Error Occured ", Toast.LENGTH_SHORT);
                }
            });
//             creating image  request from API
            ImageRequest imgRequest = new ImageRequest(selectedImgUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap response) {
//                    setting image to image view
                    moviePoster.setImageBitmap(response);

                }
            }, 0, 0, ImageView.ScaleType.CENTER_CROP, null, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(PopUp.this, "Image Load Error ", Toast.LENGTH_SHORT);
                }
            });
//            adding request to queue
            requestQueue.add(jasonRequest);
            requestQueue.add(imgRequest);
            return null;
        }
    }
}