package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

public class EditMovieItem extends AppCompatActivity {
    //declaring variables
    EditText editName, editYear, editDirector, editCast, editReviews;
    CheckBox favs;
    RatingBar rateBar;
    Button updateBtn;
    MovieDatabase movieDB;

    String name, director, cast, reviews, year;
    int favData, ratingData;
    String selectedMovie;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Movie");
        setContentView(R.layout.activity_edit_movie_item);
//receiving data from previous activity
        Bundle bundle = getIntent().getExtras();
        selectedMovie = bundle.getString("movie");


//        Initializing variables
        movieDB = new MovieDatabase(this);
        favs = (CheckBox) findViewById(R.id.editFavourite);
        updateBtn = findViewById(R.id.updateBtn);
        rateBar = (RatingBar) findViewById(R.id.movieRatingBar);

        editName = (EditText) findViewById(R.id.editMovieName);
        editYear = (EditText) findViewById(R.id.editMovieYear);
        editDirector = (EditText) findViewById(R.id.editDirector);
        editCast = (EditText) findViewById(R.id.editCast);
        editReviews = (EditText) findViewById(R.id.editReviews);
        showMovie();


    }

//    method to retrieve data from local DB and adding to edit text gui elements
    public void showMovie() {
        Cursor cursor = movieDB.displaySelectedMovie(selectedMovie);
        if (cursor != null)
            if (cursor.moveToNext()) {
                name = cursor.getString(1);
                year = cursor.getString(2);
                director = cursor.getString(3);
                cast = cursor.getString(4);
                ratingData = cursor.getInt(5);
                reviews = cursor.getString(6);
                favData = cursor.getInt(7);

                editName.setText(name);
                editYear.setText(year);
                editDirector.setText(director);
                editCast.setText(cast);
                editReviews.setText(reviews);
                rateBar.setRating(ratingData);
//                setting check if favourite
                if (favData == 1) {
                    favs.setChecked(true);
                }


            }

    }

//method to update movie details
    public void updateData(View view) {
//        running update query
        movieDB.updateData(
                editName.getText().toString(),
                Integer.parseInt(editYear.getText().toString()),
                editDirector.getText().toString(),
                editCast.getText().toString(),
                Math.round(rateBar.getRating()),
                editReviews.getText().toString(),
                favs.isChecked()

        );
        Toast.makeText(this, "Movie Details Updated ", Toast.LENGTH_SHORT).show();
    }
}