package com.example.duan_android.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.duan_android.Activity.BookingActivty;
import com.example.duan_android.Model.Movie;
import com.example.duan_android.R;

import java.util.List;

public class AdapterDateShow  extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Movie> listMovie;
    private ImageView movie_poster;

    public AdapterDateShow(Context context, List<Movie> arraylist, int layout) {
        this.context = context;
        this.listMovie = arraylist;
        this.layout = layout;
    }

    @Override


    public int getCount() {
        return listMovie.size();
    }

    @Override
    public Object getItem(int i) {
        return listMovie.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);
        Movie movie = listMovie.get(i);


        TextView movie_title = view.findViewById(R.id.movie_title);
        TextView movie_duration= view.findViewById(R.id.movie_duration);
        TextView movie_date = (TextView)view.findViewById(R.id.movie_date);
        movie_poster = view.findViewById(R.id.movie_poster);
        GridLayout time = view.findViewById(R.id.time);

        movie_poster.setImageResource(movie.getResourceImage());
        movie_title.setText(movie.getName());
        movie_duration.setText(movie.getTime()+"");
        movie_date.setText(movie.getDate());

        for (String Showtime: movie.getShowTime()) {
            Button btntime = new Button(context);
            btntime.setText(Showtime);
            btntime.setPadding(16, 8, 16, 8);
            btntime.setBackgroundResource(R.drawable.btn_dateshow);

            btntime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, BookingActivty.class);
                    context.startActivity(intent);
                }
            });
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(8, 8, 8, 8);

            btntime.setLayoutParams(params);
            time.addView(btntime);
        }
        return view;
    }
}
