package com.example.duan_android.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan_android.Model.Comment;
import com.example.duan_android.Model.Movie;
import com.example.duan_android.R;

import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final int TYPE_MOVIE = 1;
    private static final int TYPE_COMMENT = 2;

    private Context mContext;
    private List<Movie> mListMovie;
    private List<Comment> mListComment;

    public interface OnItemClickListener {
        void onMovieClick(Movie movie);


    }
    private OnItemClickListener onItemClickListener;

    // Constructor nhận vào OnItemClickListener
    public MovieAdapter(Context mContext, OnItemClickListener onItemClickListener) {
        this.mContext = mContext;
        this.onItemClickListener = onItemClickListener;
    }




    public void setData(List<Movie> list) {
        this.mListMovie = list;
        notifyDataSetChanged();
    }

    public void setDataC(List<Comment> list) {
        this.mListComment = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (mListMovie != null && position < mListMovie.size()) {
            return TYPE_MOVIE;
        } else {
            return TYPE_COMMENT;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_MOVIE) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_movie, parent, false);
            return new MovieViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
            return new CommentViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (getItemViewType(position) == TYPE_MOVIE) {
            Movie movie = mListMovie.get(position);
            if (movie != null) {
                MovieViewHolder movieHolder = (MovieViewHolder) holder;
                movieHolder.imgMovie.setImageResource(movie.getResourceImage());
                movieHolder.tvName.setText(movie.getName());

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickListener.onMovieClick(movie); // Gọi sự kiện click
                    }
                });
            }
        } else {
            Comment comment = mListComment.get(position - (mListMovie != null ? mListMovie.size() : 0)); // Adjust index for comments
            if (comment != null) {
                CommentViewHolder commentHolder = (CommentViewHolder) holder;
                commentHolder.imgComment.setImageResource(comment.getResourceImage());
                commentHolder.tvComment.setText(comment.getPreview());
            }
        }
    }

    @Override
    public int getItemCount() {
        int count = 0;
        if (mListMovie != null) count += mListMovie.size();
        if (mListComment != null) count += mListComment.size();
        return count;
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgMovie;
        private TextView tvName;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMovie = itemView.findViewById(R.id.img_movie);
            tvName = itemView.findViewById(R.id.tv_movieName);
        }
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        private ImageView imgComment;
        private TextView tvComment;

        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            imgComment = itemView.findViewById(R.id.img_comment);
            tvComment = itemView.findViewById(R.id.tv_comment);
        }
    }
}

