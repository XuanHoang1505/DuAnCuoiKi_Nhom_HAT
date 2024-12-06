package com.example.duan_android.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Adapter.AdapterLichChieu;
import com.example.duan_android.Adapter.DateAdapter;
import com.example.duan_android.Model.NgayChieu;
import com.example.duan_android.Model.cinema;
import com.example.duan_android.Model.lichchieu;
import com.example.duan_android.R;
import com.example.duan_android.ultil.CheckConnection;
import com.example.duan_android.ultil.Server;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LichChieuFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class LichChieuFragment extends Fragment {
    private List<String> cinemas;
    private ArrayList<cinema> arrayList;
    private Spinner sprap,sptinhthanh;
    private TabLayout tab;
    private ViewPager viewPager;
    private DateAdapter adapter;
    private List<NgayChieu> tabItems = new ArrayList<>();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LichChieuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LichChieuFragment newInstance(String param1, String param2) {
        LichChieuFragment fragment = new LichChieuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LichChieuFragment() {
        // Required empty public constructor
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_lich_chieu, container, false);

        // Ánh xạ view
        sprap = mview.findViewById(R.id.spcinema);
        tab = mview.findViewById(R.id.tab_date);
        viewPager = mview.findViewById(R.id.viewpagerdate);

        // Khởi tạo danh sách
        arrayList = new ArrayList<>();
        cinemas = new ArrayList<>();
        cinemas.add("Chọn rạp");
        // Lấy dữ liệu từ server
        getRap();
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("ShareIdPhim", MODE_PRIVATE);
        int IdPhim = sharedPreferences.getInt("IdPhim", -1);

        if (IdPhim != -1) {
            IteamTab(IdPhim);
        }

        // Thiết lập Adapter cho ViewPager và TabLayout
        adapter = new DateAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);

        return mview;
    }

    private void getRap() {
        if (CheckConnection.haveNetworkConnection(getActivity().getApplicationContext())) {
            Log.d("ConnectionStatus", "Network is connected.");
            RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.rap, new Response.Listener<JSONArray>() {
                @Override
                public void onResponse(JSONArray response) {
                    Log.d("Response", response.toString());
                    if (response != null) {
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);

                                int ID = jsonObject.getInt("id");
                                String tenhinh = jsonObject.getString("hinhanh");
                                int hinhanh = getResources().getIdentifier(tenhinh, "drawable", getActivity().getPackageName());
                                String tenrap = jsonObject.getString("tenrap");
                                String diachi = jsonObject.getString("diachi");
                                String sdt = jsonObject.getString("sdt");

                                // Thêm vào danh sách cinema
                                cinema cinemaItem = new cinema(ID, hinhanh, tenrap, diachi, sdt);
                                arrayList.add(cinemaItem);
                                cinemas.add(cinemaItem.getTenrap()); // Thêm tên rạp vào danh sách `cinemas`
                            }

                            // Cập nhật Adapter cho Spinner sau khi tải xong dữ liệu
                            ArrayAdapter<String> spinnerAdapterCinema = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cinemas);
                            spinnerAdapterCinema.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            sprap.setAdapter(spinnerAdapterCinema);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Log.e("VolleyError", error.toString());
                    error.printStackTrace();
                }
            });

            requestQueue.add(jsonArrayRequest);
        } else {
            Log.d("ConnectionStatus", "No network connection.");
            CheckConnection.ShowToast_Short(getActivity().getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }
    }

    private void IteamTab(int IdPhim) {
        String url = Server.ngaychieu + IdPhim;
        Log.d("ConnectionStatus", "Network is connected.");

        // Tạo RequestQueue
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());

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

                    // Cập nhật adapter sau khi dữ liệu đã được thêm vào
                    adapter.setTabItems(tabItems);
                    adapter.notifyDataSetChanged(); // Cập nhật dữ liệu cho ViewPager
                    for (int i = 0; i < tab.getTabCount(); i++) {
                        TabLayout.Tab tabItem = tab.getTabAt(i);
                        if (tabItem != null) {
                            View customView = LayoutInflater.from(getContext()).inflate(R.layout.custom_tab, null);
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