package com.example.movietracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class RecyclViewAdapater extends RecyclerView.Adapter<RecyclViewAdapater.itemViewHolder> {

//    Creating custom adapter from Recycler view
    ArrayList<MovieObject> moviesList;
    private RecyclerViewClickInterface recyclerViewClickInterface;

    public RecyclViewAdapater() {
        moviesList = new ArrayList<>();
    }

    public void setData(ArrayList<MovieObject> moviesList, RecyclerViewClickInterface recyclerViewClickInterface) {
        this.moviesList = moviesList;
        this.recyclerViewClickInterface = recyclerViewClickInterface;
    }


//    Overiding on create View Holder to set custom adapter to the gui
    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View movieView = layoutInflater.inflate(R.layout.recycler_item, parent, false);
        return new itemViewHolder(movieView);
    }

    @Override
//    method for  adding data into gui elements
    public void onBindViewHolder(@NonNull itemViewHolder holder, int position) {
        MovieObject movie = moviesList.get(position);
        holder.listText.setText(movie.getTitle());
    }


    @Override
//    method from counting hoe many items in recycler list
    public int getItemCount() {
        return moviesList.size();
    }

//    creating custom interviewer class for recycler adapter
    class itemViewHolder extends RecyclerView.ViewHolder {
        TextView listText;


        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            listText = itemView.findViewById(R.id.listItemTextView);

//            setting up on click method
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recyclerViewClickInterface.onItemClick(getAdapterPosition());

                }
            });
        }
    }
}