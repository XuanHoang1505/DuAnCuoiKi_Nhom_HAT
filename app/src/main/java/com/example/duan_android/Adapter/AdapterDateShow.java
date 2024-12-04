package com.example.duan_android.Adapter;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class AdapterDateShow extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Movie> listMovie;

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

        // Ánh xạ các view
        TextView movie_rating= view.findViewById(R.id.movie_rating);
        TextView movieTitle = view.findViewById(R.id.movie_title);
        TextView movieDuration = view.findViewById(R.id.movie_duration);
        TextView movieDate = view.findViewById(R.id.movie_date);
        ImageView moviePoster = view.findViewById(R.id.movie_poster);
        GridLayout timeLayout = view.findViewById(R.id.time);

        // Thiết lập dữ liệu cho các view
        moviePoster.setImageResource(movie.getResourceImage());
        movieTitle.setText(movie.getName());
        movieDuration.setText(movie.getTime() + " phút");
        movieDate.setText(movie.getDate());
        movie_rating.setText(movie.getSoSao()+"");

        // Tạo các nút giờ chiếu
        for (int j = 0; j < movie.getShowTime().size(); j++) {
            String showtime = movie.getShowTime().get(j);
            int idLichChieu = movie.getIdlc().get(j);
            // Tạo nút Button cho mỗi giờ chiếu
            Button btnShowtime = new Button(context);
            btnShowtime.setText(showtime);
            btnShowtime.setPadding(16, 8, 16, 8);
            btnShowtime.setBackgroundResource(R.drawable.btn_dateshow);

            // Gắn sự kiện click cho nút Button
            btnShowtime.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Truyền thông tin qua Intent
                    Intent intent = new Intent(context, BookingActivty.class);
                    intent.putExtra("gioChieu", showtime);
                    intent.putExtra("idLichChieu",idLichChieu);
                    SharedPreferences sharedPreferences = context.getSharedPreferences("ShareIdPhim", MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putInt("IdPhim", movie.getId());
                    editor.putString("TenPhim", movie.getName());
                    editor.apply();
                    context.startActivity(intent);
                }
            });

            // Đặt LayoutParams cho Button
            GridLayout.LayoutParams params = new GridLayout.LayoutParams();
            params.width = 0;
            params.columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f);
            params.setMargins(8, 8, 8, 8);

            btnShowtime.setLayoutParams(params);
            timeLayout.addView(btnShowtime);
        }

        return view;
    }
}
