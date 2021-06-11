package com.example.movietracker;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Register_Movie extends AppCompatActivity {
    //    declaring Variables
    EditText movieName, year, director, cast, rating, review;
    Button save;
    MovieDatabase movieDB;
    String movieNameValue, yearValue, directorValue, castValue, ratingValue, reviewValue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        getSupportActionBar().setTitle("Register Movie");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__movie);
//Initializing  variables
        movieName = (EditText) findViewById(R.id.movieNameInput);
        year = (EditText) findViewById(R.id.yearInput);
        director = (EditText) findViewById(R.id.directorInput);
        cast = (EditText) findViewById(R.id.castInput);
        rating = (EditText) findViewById(R.id.ratingInput);
        rating.setFilters(new InputFilter[]{new InputFilterMinMax("0", "10")});
        review = (EditText) findViewById(R.id.reviewInput);
        save = (Button) findViewById(R.id.saveButton);

        movieDB = new MovieDatabase(this);
        movieNameValue = "";
        yearValue = "";
        directorValue = "";
        castValue = "";
        ratingValue = "";
        reviewValue = "";


    }

    //    Method to save data into local database
    public void saveToDataBase(View view) {
        movieNameValue = movieName.getText().toString();
        yearValue = year.getText().toString();
        directorValue = director.getText().toString();
        castValue = cast.getText().toString();
        ratingValue = rating.getText().toString();
        reviewValue = review.getText().toString();

//        validating the Inputs
        if (movieNameValue.equals("") || yearValue.equals("") || directorValue.equals("") || castValue.equals("") || ratingValue.equals("") || reviewValue.equals("")) {

            Toast.makeText(this, "Please Complete all fields to add movie ", Toast.LENGTH_SHORT).show();
        } else {

            if (Integer.parseInt(yearValue) < 1896) {
                AlertDialog alertBox = new AlertDialog.Builder(this).create();
                alertBox.setMessage(" Year Invalid  \n Please enter year after 1895");
                alertBox.show();

            } else {
//                    passing data into database
                movieDB.enterData(movieName.getText().toString(), Integer.parseInt(year.getText().toString())
                        , director.getText().toString(), cast.getText().toString()
                        , Integer.parseInt(rating.getText().toString()), review.getText().toString(), false);
            }
        }
    }

    //Filter class for incapacitation
    public class InputFilterMinMax implements InputFilter {

        private int minimum, maximum;

//parsing string into int
        public InputFilterMinMax(String minimum, String maximum) {
            this.minimum = Integer.parseInt(minimum);
            this.maximum = Integer.parseInt(maximum);
        }


        public InputFilterMinMax(int minimum, int maximum) {
            this.minimum = minimum;
            this.maximum = maximum;
        }

        //        overriding method to create custom filter
        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int startVal, int endVal) {
            try {
                int input = Integer.parseInt(dest.toString() + source.toString());
                if (isInRange(minimum, maximum, input))
                    return null;
            } catch (NumberFormatException nfe) {
            }
            return "";
        }

        private boolean isInRange(int val1, int val2, int val3) {
            return val2 > val1 ? val3 >= val1 && val3 <= val2 : val3 >= val2 && val3 <= val1;
        }


    }

}