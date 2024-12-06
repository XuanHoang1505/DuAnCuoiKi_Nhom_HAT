package com.example.duan_android.Activity;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Adapter.DateAdapter;
import com.example.duan_android.Adapter.DateCinemaAdapter;
import com.example.duan_android.Model.Movie;
import com.example.duan_android.Model.NgayChieu;
import com.example.duan_android.Model.cinema;
import com.example.duan_android.R;
import com.example.duan_android.ultil.Server;
import com.google.android.material.tabs.TabLayout;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ListMovieActivity extends AppCompatActivity {
    private TabLayout tab;
    private TextView cinema_name,cinema_address;
    private ViewPager viewPager;
    private ImageView btn_cinemaBack;
    private DateCinemaAdapter adapter;
    private List<NgayChieu> tabItems = new ArrayList<>();
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_list_movie);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        tab = findViewById(R.id.tab_dateShow);
        viewPager = findViewById(R.id.viewPagerMovieList);
        btn_cinemaBack = findViewById(R.id.btn_cinemaBack);
        viewPager = findViewById(R.id.viewPagerMovieList);
        cinema_name =findViewById(R.id.cinema_name);
        cinema_address =findViewById(R.id.cinema_address);

        SharedPreferences sharedPreferences = getSharedPreferences("CinemaPrefs", MODE_PRIVATE);
        int IdRap = sharedPreferences.getInt("cinema_id", -1);
        if(IdRap!= -1){
            fetchCinemaDetails(IdRap);
            IteamTab(IdRap);
        }

        adapter = new DateCinemaAdapter(getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);
        btn_cinemaBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    private void fetchCinemaDetails(int IdRap) {
        String url = Server.path_getCinemaById + IdRap;

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    int IDRap = response.getInt("IDRap");
                    String resourceName = response.getString("HinhAnh");
                    int resourceImage = getResources().getIdentifier(resourceName, "drawable", getPackageName());
                    String TenRap = response.getString("TenRap");
                    String DiaChi = response.getString("DiaChi");
                    String SDT = response.getString("SDT");

                    cinema cinema = new cinema(IDRap,resourceImage, TenRap, DiaChi, SDT);

                    cinema_name.setText(cinema.getTenrap());
                    cinema_address.setText(cinema.getDiachi());

                } catch (Exception e) {
                    Log.e("ListMovieActivity", "Error parsing movie details", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ListMovieActivity", "Error fetching movie details", error);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
    private void IteamTab(int IdRap) {
        String url = Server.ngaychieuCinema + IdRap;
        Log.d("ConnectionStatus", "Network is connected.");

        // Tạo RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Tạo yêu cầu GET với JsonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response", response.toString());
                if (response != null) {
                    // Lặp qua các giao dịch trong mảng JSON
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            // Lấy thông tin từ từng đối tượng JSON trong mảng
                            JSONObject transaction = response.getJSONObject(i);
                            String ngaychieu = transaction.getString("NgayChieu");
                            // Thêm giao dịch vào arrayList
                            tabItems.add(new NgayChieu(ngaychieu));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    adapter.setTabItems(tabItems);
                    adapter.notifyDataSetChanged(); // Cập nhật dữ liệu cho ViewPager
                    for (int i = 0; i < tab.getTabCount(); i++) {
                        TabLayout.Tab tabItem = tab.getTabAt(i);
                        if (tabItem != null) {
                            View customView = LayoutInflater.from(getApplicationContext()).inflate(R.layout.custom_tab, null);
                            TextView tabText = customView.findViewById(R.id.tab_date);
                            if (i < tabItems.size()) {
                                tabText.setText(tabItems.get(i).getNgaychieu());
                                tabItem.setCustomView(customView);
                                Log.d("CustomTab", "Tab " + i + " custom view set: " + tabItems.get(i).getNgaychieu());
                            }
                        }
                    }
                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("VolleyError", error.toString());
                        error.printStackTrace();
                    }
                });

        // Thêm yêu cầu vào hàng đợi
        requestQueue.add(jsonArrayRequest);
    }
}