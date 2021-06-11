package com.example.movietracker;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;

public class SearchMovies extends AppCompatActivity {
    //    declaring variables
    MovieDatabase movieDB;
    EditText searchInput;
    Button runSearchBtn;
    ListView searchList;
    ArrayAdapter<String> ListAdapter;
    ArrayList<String> searchResultList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Search Movies");
        setContentView(R.layout.activity_search_movies);

//        Initializing the variables
        movieDB = new MovieDatabase(this);
        searchList = findViewById(R.id.displaySearch);

        searchInput = (EditText) findViewById(R.id.searchBox);
        runSearchBtn = findViewById(R.id.runSearchBtn);


    }

//    method for looking up movies
    public void showSearch(View view) {
        searchResultList = new ArrayList<>();
//        validating user input
        if (searchInput.getText().toString().equals("")) {
            Toast.makeText(this, "Please enter a movie title / Director / Cast member to search ", Toast.LENGTH_SHORT).show();

        } else {
            Cursor cursor = movieDB.searchMovie(searchInput.getText().toString());
//retrieving movie data using cursor
            if (cursor.getCount() == 0) {
                Toast.makeText(this, "No favourites added ", Toast.LENGTH_SHORT).show();
            } else {
                System.out.println(cursor);
                while (cursor.moveToNext()) {
                    searchResultList.add((cursor.getString(1)));
                    System.out.println(cursor);

                }
                ListAdapter = new ArrayAdapter<>(this, R.layout.row_text, searchResultList);
                searchList.setAdapter(ListAdapter);
//                setting item checked
                for (int i = 0; i < searchResultList.size(); i++) {
                    searchList.setItemChecked(i, true);
                }
            }
        }
    }


}