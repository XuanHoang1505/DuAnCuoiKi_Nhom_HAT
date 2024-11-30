package com.example.duan_android.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Adapter.LC_TT_ViewPageApdapter;
import com.example.duan_android.Model.Movie;
import com.example.duan_android.R;
import com.example.duan_android.ultil.Server;
import com.google.android.material.tabs.TabLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONObject;

public class LC_TT_Activity extends AppCompatActivity {
    private TabLayout tab;
    private ViewPager viewPager;
    private LC_TT_ViewPageApdapter viewPageApdapter;
    private YouTubePlayerView trailler;
    private TextView txtMovieTitle, txtDuration,txtDate,txtstar;
    private ImageView imgcinema;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_lc_tt);
        tab = findViewById(R.id.tab);
        viewPager = findViewById(R.id.viewpage_lc_tt);
        trailler = findViewById(R.id.trallercinema);
        txtMovieTitle = findViewById(R.id.txtMovieTitle);
        imgcinema = findViewById(R.id.imgcinema);
        txtDuration = findViewById(R.id.txtDuration);
        txtDate= findViewById(R.id.txtDate);
        txtstar = findViewById(R.id.txtstar);


        SharedPreferences sharedPreferences = getSharedPreferences("ShareIdPhim", MODE_PRIVATE);
        int IdPhim = sharedPreferences.getInt("IdPhim", -1);

        if (IdPhim != -1) {
            fetchMovieDetails(IdPhim);
        }

        txtMovieTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        // Lấy userId từ SharedPreferences

        viewPageApdapter = new LC_TT_ViewPageApdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(viewPageApdapter);
        tab.setupWithViewPager(viewPager);
    }

    private void fetchMovieDetails(int IdPhim) {
        String url = Server.path_getMovieById + IdPhim;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Lấy dữ liệu từ JSON Object
                    String resourceName = response.getString("HinhAnh");
                    int resourceImage = getResources().getIdentifier(resourceName, "drawable", getPackageName());
                    String TenPhim = response.getString("TenPhim");
                    int time = response.getInt("ThoiGianPhim");
                    double SoSao = response.getDouble("SoSao");
                    String NgayKhoiChieu = response.getString("NgayKhoiChieu");
                    String Trailler = response.getString("Trailer");

                    // Tạo đối tượng Movie
                    Movie movie = new Movie(resourceImage, TenPhim, time, Trailler, SoSao, NgayKhoiChieu);

                    // Cập nhật giao diện
                    txtMovieTitle.setText(movie.getName());
                    txtDuration.setText(String.valueOf(movie.getTime()) + " phút");
                    txtstar.setText(String.valueOf(movie.getSoSao()) + " sao");
                    txtDate.setText(movie.getngayKhoiChieu());
                    imgcinema.setImageResource(movie.getResourceImage());
                    // Trong phương thức onResponse
                    trailler.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady(YouTubePlayer youTubePlayer) {
                            youTubePlayer.loadVideo(movie.getTrailler(), 0);
                        }
                    });

                    getLifecycle().addObserver(trailler);

                } catch (Exception e) {
                    Log.e("LC_TT_Activity", "Error parsing movie details", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("LC_TT_Activity", "Error fetching movie details", error);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

}
