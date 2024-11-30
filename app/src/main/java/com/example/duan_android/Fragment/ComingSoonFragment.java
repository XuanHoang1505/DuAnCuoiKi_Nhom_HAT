package com.example.duan_android.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Activity.LC_TT_Activity;
import com.example.duan_android.Activity.ViewMoreActivity;
import com.example.duan_android.Adapter.DataMovieAdapter;
import com.example.duan_android.Model.Movie;
import com.example.duan_android.R;
import com.example.duan_android.ultil.CheckConnection;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComingSoonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComingSoonFragment extends Fragment implements DataMovieAdapter.OnItemClickListener {
    private RecyclerView rcvMovieCS;
    private Button btnShowingVMCS;
    private List<Movie> movieList;
    private DataMovieAdapter adapter;

    public ComingSoonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_coming_soon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();
        rcvMovieCS = view.findViewById(R.id.rcv_moiveCS);
        btnShowingVMCS = view.findViewById(R.id.btnXemTiepCS);
        movieList = new ArrayList<>();

        if (CheckConnection.haveNetworkConnection(getActivity().getApplicationContext())) {
            getMovie();
        } else {
            CheckConnection.ShowToast_Short(getActivity().getApplicationContext(), "Hãy kiểm tra lại kết nối của bạn");
            getActivity().finish();
        }

        // Khởi tạo adapter và truyền listener vào
        adapter = new DataMovieAdapter(context, movieList, this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        rcvMovieCS.setLayoutManager(gridLayoutManager);
        rcvMovieCS.setAdapter(adapter);

        btnShowingVMCS.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), ViewMoreActivity.class);
            intent.putExtra("selected_tab", 1);
            startActivity(intent);
        });
    }

    private void getMovie() {
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.path_MovieComingSoon, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id;
                    String movieName;
                    int resourceImage;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("IDPhim");
                            movieName = jsonObject.getString("TenPhim");
                            String resourceName = jsonObject.getString("HinhAnh");
                            resourceImage = getResources().getIdentifier(resourceName, "drawable", getActivity().getPackageName());

                            // Thêm vào danh sách
                            movieList.add(new Movie(id, movieName, resourceImage));
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý lỗi
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onItemClick(Movie movie) {
        // Lưu thông tin movie vào SharedPreferences hoặc xử lý theo ý muốn
        saveIdPhim(movie.getId());

        // Mở chi tiết của phim
        Intent intent = new Intent(getActivity(), LC_TT_Activity.class);
        startActivity(intent);
    }

    private void saveIdPhim(int id) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ShareIdPhim", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("IdPhim", id);
        editor.apply();
    }
}
