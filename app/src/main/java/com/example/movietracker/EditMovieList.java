package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class EditMovieList extends AppCompatActivity {
    //declaring variables
    MovieDatabase movieDB;
    ListView DisplayMovieList;
    ArrayAdapter<String> listAdapter;
    ArrayList<String> movieList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Edit Movie Details");
        setContentView(R.layout.activity_edit_movie_list);

//        Initializing variable
        movieDB = new MovieDatabase(this);
        DisplayMovieList = findViewById(R.id.editMovieList);

        movieList = new ArrayList<>();

        showData();

//setting on click list item
        DisplayMovieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                showMoviePage(i);

            }
        });

    }

    private void showMoviePage(int pos) {
//        passing data into next activity
        String selectedMovie = DisplayMovieList.getItemAtPosition(pos).toString();
        Intent passData = new Intent(this, EditMovieItem.class);
        passData.putExtra("movie", selectedMovie);
        startActivity(passData);

    }
//method to retrieve and display data
    private void showData() {
        Cursor cursor = movieDB.displayData();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No movies added ", Toast.LENGTH_SHORT).show();
        } else {
//            adding movies to list view
            while (cursor.moveToNext()) {
                movieList.add((cursor.getString(1)));
                Collections.sort(movieList);
            }
            listAdapter = new ArrayAdapter<>(this, R.layout.row_text, movieList);
            DisplayMovieList.setAdapter(listAdapter);
        }


    }
}