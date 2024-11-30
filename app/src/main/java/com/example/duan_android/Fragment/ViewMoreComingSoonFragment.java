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

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Activity.LC_TT_Activity;
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

public class ViewMoreComingSoonFragment extends Fragment implements DataMovieAdapter.OnItemClickListener {

    private RecyclerView rcvMovie;
    private List<Movie> movieList;
    private DataMovieAdapter adapter;

    public ViewMoreComingSoonFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_more_coming_soon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();

        rcvMovie = view.findViewById(R.id.vm_rcv_moive_CS);
        movieList = new ArrayList<>();

        if (CheckConnection.haveNetworkConnection(context)) {
            getMovie();
        } else {
            CheckConnection.ShowToast_Short(context, "Hãy kiểm tra lại kết nối của bạn");
            getActivity().finish();
        }

        // Khởi tạo adapter và thiết lập RecyclerView
        adapter = new DataMovieAdapter(context, movieList, this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        rcvMovie.setLayoutManager(gridLayoutManager);
        rcvMovie.setAdapter(adapter);
    }

    private void getMovie() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.path_MovieViewMoreComingSoon, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int id = jsonObject.getInt("IDPhim");
                            String movieName = jsonObject.getString("TenPhim");
                            String resourceName = jsonObject.getString("HinhAnh");
                            int resourceImage = getResources().getIdentifier(resourceName, "drawable", requireContext().getPackageName());

                            // Thêm phim vào danh sách
                            movieList.add(new Movie(id, movieName, resourceImage));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý lỗi kết nối
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void onItemClick(Movie movie) {
        // Lưu IDPhim vào SharedPreferences
        saveIdPhim(movie.getId());

        // Mở chi tiết của phim
        Intent intent = new Intent(getActivity(), LC_TT_Activity.class);
        startActivity(intent);
    }

    private void saveIdPhim(int id) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("ShareIdPhim", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("IdPhim", id);
        editor.apply();
    }
}
