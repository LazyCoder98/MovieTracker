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

public class DisplayFavourites extends AppCompatActivity {

    MovieDatabase movieDB;
    ListView favDisplay;
    ArrayAdapter<String> ListAdapter;
    ArrayList<String> movieList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("Favourites");
        setContentView(R.layout.activity_display_favourites);

        movieDB = new MovieDatabase(this);
        favDisplay = findViewById(R.id.favDisplay);

        movieList = new ArrayList<>();
        showFavourites();

    }

    private void showFavourites() {
        Cursor cursor = movieDB.displayFavouriteData();

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No favourites added ", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                movieList.add((cursor.getString(1)));

                Collections.sort(movieList);

            }
            ListAdapter = new ArrayAdapter<>(this, R.layout.custom_multi_checked_list, movieList);
            favDisplay.setAdapter(ListAdapter);
            for (int i = 0; i < movieList.size(); i++) {
                favDisplay.setItemChecked(i, true);
            }
        }
    }

    public void updateFavourites(View view) {

        for (int i = 0; i < favDisplay.getCount(); i++) {
            if (!favDisplay.isItemChecked(i)) {
                movieDB.removeFromFavourites(favDisplay.getItemAtPosition(i).toString());
            }

        }

        Toast.makeText(this, "Favourites has been updated ", Toast.LENGTH_LONG).show();
    }
}