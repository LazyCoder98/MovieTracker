package com.example.movietracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class MovieDatabase extends SQLiteOpenHelper {

//    Declaring variables
    static final private String DB_Name = "Movies_Database";
    static final private String Db_Table = "Watched_Movies";
    static final private int DB_version = 1;
    public static final String ID = "_id";
    public static final String MOVIE_NAME = "movie_name";
    public static final String MOVIE_YEAR = "movie_year";
    public static final String MOVIE_DIRECTOR = "movie_director";
    public static final String MOVIE_CAST = "movie_cast";
    public static final String MOVIE_RATING = "movie_rating";
    public static final String MOVIE_REVIEWS = "movie_reviews";
    public static final String FAVOURITES = "Favourites";

    Context ctx;
    SQLiteDatabase movieDB;
//DB constructor
    public MovieDatabase(Context ct){
        super(ct,DB_Name,null,DB_version);
        ctx =ct;
    }

//    query to create DB Table
    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable ="create table "+Db_Table+" (" + ID + " integer primary key  autoincrement," + MOVIE_NAME + " text," + MOVIE_YEAR + " int," + MOVIE_DIRECTOR + " text," + MOVIE_CAST + " text," + MOVIE_RATING + " int," + MOVIE_REVIEWS + " text," + FAVOURITES + " bool); ";
        db.execSQL(createTable);
        Log.i("DB","created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists "+Db_Table);
        onCreate(db);
    }


//query to enter data into DB
    public void enterData(String name,int year,String director,String cast,int rating , String reviews , boolean fav){
        movieDB = getWritableDatabase();
        movieDB.execSQL("insert into "+Db_Table+ " (" + MOVIE_NAME + "," + MOVIE_YEAR + "," + MOVIE_DIRECTOR + "," + MOVIE_CAST + "," + MOVIE_RATING + ","+MOVIE_REVIEWS+","+FAVOURITES+") values('" +name+"','"+year+"','"+director+"','"+cast+"','"+rating+"','"+reviews+"','"+fav+"');");
        Toast.makeText(ctx, "Movie Saved Successfully ", Toast.LENGTH_SHORT).show();
        Log.i("add","done");
    }


//query to retrieve data
    public Cursor displayData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuarry = "SELECT * FROM "+Db_Table;
        Cursor cursor =db.rawQuery(selectQuarry,null);
        return cursor;
    }

//query to retrieve entries with true in favourites column
    public Cursor displayFavouriteData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuarry = "SELECT * FROM "+Db_Table+" WHERE " +FAVOURITES+" LIKE 1";
        Cursor cursor =db.rawQuery(selectQuarry,null);
        return cursor;
    }
    //query to add entries with true in favourites column
    public boolean addToFavourites(String name){
        SQLiteDatabase db =this.getReadableDatabase();
        ContentValues contentVal = new ContentValues();
        contentVal.put(FAVOURITES,true);
        db.update(Db_Table,contentVal, "movie_name = ?",new String[]{ name });
        return true;
    }
    //query to remove entries with true in favourites column
    public boolean removeFromFavourites(String name){
        SQLiteDatabase db =this.getReadableDatabase();
        ContentValues contentVal = new ContentValues();
        contentVal.put(FAVOURITES,false);
        db.update(Db_Table,contentVal, "movie_name = ?",new String[]{ name });
        return true;
    }
// query return specific  match from the user input
    public Cursor displaySelectedMovie(String movie){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuarry = "SELECT * FROM "+Db_Table+" WHERE " +MOVIE_NAME+" LIKE '%"+movie+"%'";
        Cursor cursor =db.rawQuery(selectQuarry,null);
        return cursor;
    }

//    query to update data
    public void updateData(String name,int year,String director,String cast,int rating , String reviews , boolean fav ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues updateValues = new ContentValues();
        updateValues.put(MOVIE_NAME,name);
        updateValues.put(MOVIE_YEAR,year);
        updateValues.put(MOVIE_DIRECTOR,director);
        updateValues.put(MOVIE_CAST,cast);
        updateValues.put(MOVIE_RATING,rating);
        updateValues.put(MOVIE_REVIEWS,reviews);
        updateValues.put(FAVOURITES,fav);
        db.update(Db_Table,updateValues,"movie_name = ?",new String[]{ name });
    }

    //Search query to match the user input
    public Cursor searchMovie(String search){
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuarry = "SELECT * FROM "+Db_Table+" WHERE " +MOVIE_NAME+" LIKE '%"+search+"%' OR "+MOVIE_DIRECTOR+" LIKE '%"+search+"%' OR "+MOVIE_CAST+" LIKE '%"+search+"%'";
        Cursor cursor =db.rawQuery(selectQuarry,null);
        return cursor;
    }

}

