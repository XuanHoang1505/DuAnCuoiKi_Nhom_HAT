package com.example.duan_android.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Activity.ListMovieActivity;
import com.example.duan_android.Adapter.AdapterCinema;
import com.example.duan_android.Model.cinema;
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
    private RequestQueue requestQueue;

    public CinemaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_cinema, container, false);

        lv = mview.findViewById(R.id.lviewcinema);
        location = mview.findViewById(R.id.editTextText);
        arrayList = new ArrayList<>();

        requestQueue = Volley.newRequestQueue(requireContext()); // Sử dụng requireContext() thay vì getActivity()

        if (CheckConnection.haveNetworkConnection(requireContext())) {
            Log.d("ConnectionStatus", "Network is connected.");
            fetchCinemaData();
        } else {
            Log.d("ConnectionStatus", "No network connection.");
            CheckConnection.ShowToast_Short(requireContext(), "Bạn hãy kiểm tra lại kết nối");
        }

        adapter = new AdapterCinema(requireContext(), R.layout.layout_cinema, arrayList, cinema -> {
            saveCinemaIdToPreferences(cinema.getId());
            Intent intent = new Intent(getActivity(), ListMovieActivity.class);
            intent.putExtra("cinemaID", cinema.getId());
            startActivity(intent);
        });
        lv.setAdapter(adapter);

        location.setOnClickListener(v -> showLocationDialog());

        return mview;
    }

    private void fetchCinemaData() {
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.rap, response -> {
            if (!isAdded()) {
                Log.e("CinemaFragment", "Fragment not attached, ignoring response.");
                return;
            }

            Log.d("Response", response.toString());
            if (response != null) {
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int ID = jsonObject.getInt("id");
                        String tenhinh = jsonObject.getString("hinhanh");
                        int hinhanh = getResources().getIdentifier(tenhinh, "drawable", requireActivity().getPackageName());
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
    }

    private void saveCinemaIdToPreferences(int cinemaId) {
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("CinemaPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("cinema_id", cinemaId);
        editor.apply();
    }

    private void showLocationDialog() {
        if (!isAdded()) {
            Log.e("CinemaFragment", "Fragment not attached, cannot show dialog.");
            return;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Vị trí");
        String[] locations = {"Toàn quốc", "TP Hồ Chí Minh", "Hà Nội", "Đà Nẵng", "An Giang", "Bà Rịa - Vũng Tàu", "Bến Tre", "Cà Mau", "Đắk Lắk", "Hải Phòng", "Khánh Hòa", "Nghệ An"};
        int checkedItem = -1;

        builder.setSingleChoiceItems(locations, checkedItem, (dialog, which) -> selectedLocation = locations[which]);

        builder.setPositiveButton("Xác nhận", (dialog, which) -> location.setText(selectedLocation));
        builder.setNegativeButton("Đóng", (dialog, which) -> dialog.dismiss());

        builder.create().show();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (requestQueue != null) {
            requestQueue.cancelAll(tag -> true);
        }
    }
}
