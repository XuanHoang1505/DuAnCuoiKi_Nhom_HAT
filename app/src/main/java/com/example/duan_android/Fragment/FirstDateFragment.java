package com.example.duan_android.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Adapter.AdapterDateShow;
import com.example.duan_android.Adapter.AdapterLichChieu;
import com.example.duan_android.Model.Movie;
import com.example.duan_android.Model.lichchieu;
import com.example.duan_android.R;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirstDateFragment extends Fragment {
    private ListView lv;
    private AdapterDateShow adapter;
    private List<Movie> lichChieuList;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public FirstDateFragment() {
        // Required empty public constructor
    }

    public static FirstDateFragment newInstance(String param1, String param2) {
        FirstDateFragment fragment = new FirstDateFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_first_date, container, false);
        lv = mview.findViewById(R.id.lvMovie);
        lichChieuList = new ArrayList<>();

        if (getArguments() != null) {
            String ngaychieu = getArguments().getString("NgayChieu");
            Log.d("nc", "nc: " + ngaychieu);

            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CinemaPrefs", MODE_PRIVATE);
            int IdRap = sharedPreferences.getInt("cinema_id", -1);
            Log.d("SharedPreferences", "IdRap: " + IdRap);
            if (IdRap != -1) {
                getgiochieu(IdRap, ngaychieu);
            }
        }

        adapter = new AdapterDateShow(getContext(), lichChieuList,R.layout.item_movie_cinema);
        lv.setAdapter(adapter);
        return mview;
    }

    private void getgiochieu(int idRap, String ngaychieu) {
        String url = Server.giochieuCinema + "idRap=" + idRap + "&ngayChieu=" + ngaychieu; // URL API
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response", response.toString());
                if (response != null) {
                    lichChieuList.clear(); // Xóa dữ liệu cũ

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject movieObject = response.getJSONObject(i);

                            int idPhim = movieObject.getInt("idPhim");
                            String tenPhim = movieObject.getString("tenPhim");
                            String hinhAnh = movieObject.getString("hinhAnh");
                            int Anh = getResources().getIdentifier(hinhAnh,"drawable", getContext().getPackageName());
                            double soSao = movieObject.getDouble("soSao");
                            String ngayKhoiChieu = movieObject.getString("ngayKhoiChieu");
                            int thoiGianPhim = movieObject.getInt("thoiGianPhim");

                            JSONArray gioChieuArray = movieObject.getJSONArray("gioChieu");
                            List<String> gioChieuList = new ArrayList<>();
                            for (int j = 0; j < gioChieuArray.length(); j++) {
                                gioChieuList.add(gioChieuArray.getString(j));
                            }

                            JSONArray idLichChieuArray = movieObject.getJSONArray("idLichChieu");
                            List<Integer> idLichChieuList = new ArrayList<>();
                            for (int j = 0; j < idLichChieuArray.length(); j++) {
                                idLichChieuList.add(idLichChieuArray.getInt(j));
                            }

                            // Tạo đối tượng lichchieu
                            Movie movie = new Movie(idPhim, tenPhim, Anh, soSao, ngayKhoiChieu, thoiGianPhim, idLichChieuList, gioChieuList);
                            lichChieuList.add(movie);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged(); // Cập nhật ListView
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("VolleyError", error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}
