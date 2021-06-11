package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class DisplayMovies extends AppCompatActivity {
//Declaring  variables
    MovieDatabase movieDB;
    ListView DisplayMovieList;
    ArrayAdapter<String> listAdapter;
    ArrayList<String> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Added Movies");
        setContentView(R.layout.activity_displaymovies);

//        Initializing variables
                movieDB = new MovieDatabase(this);
        DisplayMovieList = findViewById(R.id.movieDisplayList);


        movieList = new ArrayList<>();
//staring method to display list
        showData();

    }

//    method to retrieve data from local database and display in list
    private void showData() {
//        using cursor to iterate through DB records
        Cursor cursor = movieDB.displayData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No movies added ", Toast.LENGTH_SHORT).show();
        } else {
//            adding data from DB to temporary array
            while (cursor.moveToNext()) {
                movieList.add((cursor.getString(1)));
//                sorting List
                Collections.sort(movieList);
            }
//            using list adapter to add array to list view
            listAdapter = new ArrayAdapter<>(this, R.layout.custom_multi_checked_list, movieList);
            DisplayMovieList.setAdapter(listAdapter);
        }


    }

//    method to add movies to favourite
    public void selectedItems(View view) {
        for (int i = 0; i < DisplayMovieList.getCount(); i++) {
            if (DisplayMovieList.isItemChecked(i)) {
//                method for running update query
                movieDB.addToFavourites(DisplayMovieList.getItemAtPosition(i).toString());
            }

        }
        Toast.makeText(this, "Movies has be added to favourites ", Toast.LENGTH_LONG).show();
    }


}