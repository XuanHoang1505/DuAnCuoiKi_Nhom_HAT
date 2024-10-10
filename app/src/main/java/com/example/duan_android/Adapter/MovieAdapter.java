package com.example.duan_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan_android.Model.Movie;
import com.example.duan_android.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder>{
    private Context mContext;
    private List<Movie> mListMovie;

    public MovieAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<Movie> list){
        this.mListMovie=list;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie,parent,false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = mListMovie.get(position);
        if(movie==null){
            return;
        }
        holder.imgMovie.setImageResource(movie.getResourceImage());
        holder.tvName.setText(movie.getName());
    }

    @Override
    public int getItemCount() {
        if(mListMovie!=null){
            return mListMovie.size();
        }
        return 0;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgMovie;
        private TextView tvName;
        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_movie);
            tvName = itemView.findViewById(R.id.tv_movieName);
        }
    }
}
