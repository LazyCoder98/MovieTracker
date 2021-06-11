package com.example.movietracker;

public class MovieObject {
//    declaring variables
    private String ID;
    private String Title;
    private String posterImg;

    public MovieObject() {

    }
//creating setters and getters
    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getPosterImg() {
        return posterImg;
    }

    public void setPosterImg(String posterImg) {
        this.posterImg = posterImg;
    }
}
