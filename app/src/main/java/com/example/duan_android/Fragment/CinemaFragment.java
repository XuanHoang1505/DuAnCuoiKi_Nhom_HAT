package com.example.duan_android.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Activity.ListMovieActivity;
import com.example.duan_android.Model.cinema;
import com.example.duan_android.Adapter.AdapterCinema;
import com.example.duan_android.R;
import com.example.duan_android.ultil.CheckConnection;
import com.example.duan_android.ultil.Server;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class CinemaFragment extends Fragment {
    private EditText location;
    private ListView lv;
    private ArrayList<cinema> arrayList;
    private AdapterCinema adapter;
    private String selectedLocation = "Toàn quốc";

    public CinemaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_cinema, container, false);
        lv = mview.findViewById(R.id.lviewcinema);
        location = mview.findViewById(R.id.editTextText);
        arrayList = new ArrayList<>();

        if (CheckConnection.haveNetworkConnection(getActivity().getApplicationContext())) {
            Log.d("ConnectionStatus", "Network is connected.");
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.rap, response -> {
                Log.d("Response", response.toString());
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            int ID = jsonObject.getInt("id");
                            String tenhinh = jsonObject.getString("hinhanh");
                            int hinhanh = getResources().getIdentifier(tenhinh, "drawable", getActivity().getPackageName());
                            String tenrap = jsonObject.getString("tenrap");
                            String diachi = jsonObject.getString("diachi");
                            String sdt = jsonObject.getString("sdt");

                            arrayList.add(new cinema(ID, hinhanh, tenrap, diachi, sdt));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
            }, error -> {
                Log.e("VolleyError", error.toString());
                error.printStackTrace();
            });

            requestQueue.add(jsonArrayRequest);
        } else {
            Log.d("ConnectionStatus", "No network connection.");
            CheckConnection.ShowToast_Short(getActivity().getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }

        // Sử dụng AdapterCinema và set listener cho item click
        adapter = new AdapterCinema(getContext(), R.layout.layout_cinema, arrayList, cinema -> {
            // Lưu ID của cinema vào SharedPreferences
            saveCinemaIdToPreferences(cinema.getId());

            // Mở một Activity hoặc Fragment mới khi click vào cinema
            Log.d("CinemaSelected", "ID: " + cinema.getId() + ", Name: " + cinema.getTenrap());
            Intent intent = new Intent(getActivity(), ListMovieActivity.class);
            intent.putExtra("cinemaID", cinema.getId()); // Truyền ID của cinema sang Activity tiếp theo
            startActivity(intent);
        });
        lv.setAdapter(adapter);

        location.setOnClickListener(v -> showLocationDialog());

        return mview;
    }

    private void saveCinemaIdToPreferences(int cinemaId) {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("CinemaPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("cinema_id", cinemaId); // Lưu ID cinema vào SharedPreferences
        editor.apply();
    }

    private void showLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Vị trí");
        String[] locations = {"Toàn quốc", "TP Hồ Chí Minh", "Hà Nội", "Đà Nẵng", "An Giang", "Bà Rịa - Vũng Tàu", "Bến Tre", "Cà Mau", "Đắk Lắk", "Hải Phòng", "Khánh Hòa", "Nghệ An"};
        int checkedItem = -1;

        builder.setSingleChoiceItems(locations, checkedItem, (dialog, which) -> selectedLocation = locations[which]);

        builder.setPositiveButton("Xác nhận", (dialog, which) -> location.setText(selectedLocation));
        builder.setNegativeButton("Đóng", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }
}