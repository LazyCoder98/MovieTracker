package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Movie Tracker");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        Adding main menu buttons and their on click functions

        Button regMovieBtn = (Button) findViewById(R.id.regMovieBtn);
        regMovieBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRegMovie();

            }
        });


        Button displayMovie = (Button) findViewById(R.id.dispMovieBtn);
        displayMovie.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDispMovie();

            }
        });

        Button displayFavourites = (Button) findViewById(R.id.favBtn);
        displayFavourites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDispFavourites();

            }
        });

        Button editMovieDetails = (Button) findViewById(R.id.editMovieBtn);
        editMovieDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEditMovie();

            }
        });

        Button search = (Button) findViewById(R.id.searchBtn);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startSearch();


            }
        });

        Button ratings = (Button) findViewById(R.id.ratingsBtn);
        ratings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRatings();

            }
        });


    }

//    Creating methods to start new activities

    private void startRatings() {
        Intent intent =new Intent(this,ViewRatings.class);
        startActivity(intent);
    }


    private void startEditMovie() {
        Intent intent =new Intent(this,EditMovieList.class);
        startActivity(intent);
    }

    private void startDispFavourites() {
        Intent intent =new Intent(this,DisplayFavourites.class);
        startActivity(intent);
    }


    private void startRegMovie() {
        Intent intent =new Intent(this,Register_Movie.class);
        startActivity(intent);
    }


    private void startDispMovie() {
        Intent intent =new Intent(this,DisplayMovies.class);
        startActivity(intent);
    }


    private void startSearch() {
        Intent intent =new Intent(this,SearchMovies.class);
        startActivity(intent);
    }

    public void startDisplayReviews(View view) {
        Intent intent =new Intent(this,ViewRatings.class);
        startActivity(intent);
    }
}