package com.example.duan_android.Fragment;

import static android.content.Context.MODE_PRIVATE;

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
import com.example.duan_android.Adapter.AdapterLichChieu;
import com.example.duan_android.Model.cinema;
import com.example.duan_android.Model.lichchieu;
import com.example.duan_android.R;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Date1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Date1Fragment extends Fragment {

    private ListView lv;
    private AdapterLichChieu adapter;
    private List<lichchieu> lichChieuList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Date1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Date1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Date1Fragment newInstance(String param1, String param2) {
        Date1Fragment fragment = new Date1Fragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_date1, container, false);
        lv = mview.findViewById(R.id.lvgiochieu);
        lichChieuList = new ArrayList<>();
        if (getArguments() != null) {
            String ngaychieu = getArguments().getString("NgayChieu");

            // Hiển thị idGioChieu trong log
            Log.d("nc", "nc: " + ngaychieu);

            // Tiếp tục gọi API hoặc xử lý theo idGioChieu
            SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ShareIdPhim", MODE_PRIVATE);
            int IdPhim = sharedPreferences.getInt("IdPhim", -1);
            Log.d("SharedPreferences", "IdPhim: " + IdPhim);
            if (IdPhim != -1) {
                getgiochieu(IdPhim, ngaychieu);
            }
        }
        adapter = new AdapterLichChieu(getContext(), R.layout.layout_lichchieu, lichChieuList);
        lv.setAdapter(adapter);


        return mview;
    }
    private void getgiochieu(int idPhim, String ngaychieu) {
        String url = Server.giochieu + "idPhim=" + idPhim + "&" + "ngayChieu=" + ngaychieu; // Tạo URL yêu cầu
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

        // Tạo yêu cầu GET với JsonArrayRequest
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.d("Response", response.toString());
                if (response != null) {
                    lichChieuList.clear(); // Xóa dữ liệu cũ (nếu có)
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            // Lấy dữ liệu từ JSON object
                            JSONObject jsonObject = response.getJSONObject(i);
                            int idrap = jsonObject.getInt("idRap");
                            String tenrap = jsonObject.getString("tenRap");

                            JSONArray gioChieuArray = jsonObject.getJSONArray("gioChieu");
                            List<String> giochieulist = new ArrayList<>();
                            for (int j = 0; j < gioChieuArray.length(); j++) {
                                giochieulist.add(gioChieuArray.getString(j));
                            }

                            JSONArray lcArray = jsonObject.getJSONArray("idLichChieu");
                            List<Integer> lclist = new ArrayList<>();
                            for (int j = 0; j < lcArray.length(); j++) {
                                lclist.add(lcArray.getInt(j));
                            }

                            lichchieu lc = new lichchieu(idrap,tenrap,giochieulist,lclist);
                            lichChieuList.add(lc);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    // Cập nhật dữ liệu trong adapter
                    adapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
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