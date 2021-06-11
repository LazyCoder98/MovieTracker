package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class ViewRatings extends AppCompatActivity {

    //    Declaring Variable
    MovieDatabase movieDB;
    ListView movieList;
    ArrayAdapter<String> listAdapter;
    ArrayList<String> savedMovieList;
    String selection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Find Movies in IMDB");
        setContentView(R.layout.activity_view_ratings);

//        Initializing variables
        movieDB = new MovieDatabase(this);
        movieList = findViewById(R.id.reviewMovieList);
        selection = "";

        savedMovieList = new ArrayList<>();
        showData();

//        checking which item is check in list
        movieList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View view, int pos, long lng) {
                selection = (String) (movieList.getItemAtPosition(pos));

            }
        });


    }

//    displaying movie list from DB
    public void showData() {
        Cursor cursor = movieDB.displayData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No movies added ", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                savedMovieList.add((cursor.getString(1)));
            }
            listAdapter = new ArrayAdapter<>(this, R.layout.custom_single_checked_list, savedMovieList);
            movieList.setAdapter(listAdapter);
        }


    }


    public void searchMovieOnline(View view) {
        if (selection.equals("")) {
            Toast.makeText(this, "Please select Movie to search ", Toast.LENGTH_SHORT).show();
        } else {
//            passing selected dating into next  activity
            Intent intent = new Intent(this, RatingDisplay.class);
            intent.putExtra("selection", selection);
            startActivity(intent);

        }
    }
}