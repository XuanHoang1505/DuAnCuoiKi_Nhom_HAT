package com.example.duan_android.Fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Adapter.AdapterKhachHangVoucher;
import com.example.duan_android.Model.KhachHangVoucher;
import com.example.duan_android.R;
import com.example.duan_android.ultil.CheckConnection;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Gift2Fragment extends Fragment {
    private TextView txtThongBao;
    private ListView lv;
    private AdapterKhachHangVoucher adapterKhachHangVoucher;
    private ArrayList<KhachHangVoucher> arrayList;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public Gift2Fragment() {
        // Required empty public constructor
    }

    public static Gift2Fragment newInstance(String param1, String param2) {
        Gift2Fragment fragment = new Gift2Fragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mview = inflater.inflate(R.layout.fragment_gift2, container, false);

        txtThongBao = mview.findViewById(R.id.txtThongBao);  // TextView thông báo
        lv = mview.findViewById(R.id.lvgift);
        arrayList = new ArrayList<>();
        adapterKhachHangVoucher = new AdapterKhachHangVoucher(getContext(), R.layout.layout_mygift, arrayList);
        lv.setAdapter(adapterKhachHangVoucher);

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        if (userId != null) {
            // Gửi yêu cầu đến API để lấy danh sách voucher
            loadVoucherData(userId);
        }

        return mview;
    }

    private void loadVoucherData(String userId) {
        String url = Server.path_VoucherCustomer + "?userId=" + userId;

        if (CheckConnection.haveNetworkConnection(getContext())) {
            Log.d("ConnectionStatus", "Network is connected.");

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            Log.d("Response", response.toString());

                            if (response != null && response.length() > 0) {
                                // Nếu có dữ liệu
                                for (int i = 0; i < response.length(); i++) {
                                    try {
                                        JSONObject voucherJson = response.getJSONObject(i);
                                        int IDVoucher = voucherJson.getInt("IDVoucher");
                                        String hinhAnh = voucherJson.getString("HinhAnh");
                                        int hinh = getResources().getIdentifier(hinhAnh, "drawable", getActivity().getPackageName());
                                        int SoDiemDoi = voucherJson.getInt("SoDiemDoi");
                                        String tenVoucher = voucherJson.getString("TenVoucher");
                                        int soLuong = voucherJson.getInt("SoLuong");
                                        String trangThai = voucherJson.getString("TrangThai");
                                        double SoTienGiam = voucherJson.getDouble("SoTienGiam");

                                        KhachHangVoucher voucher = new KhachHangVoucher(IDVoucher, tenVoucher, hinh, SoDiemDoi, SoTienGiam, soLuong, trangThai);
                                        arrayList.add(voucher);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                }
                                // Cập nhật adapter sau khi dữ liệu đã được thêm vào
                                adapterKhachHangVoucher.notifyDataSetChanged();
                            } else {
                                // Nếu không có dữ liệu (API trả về rỗng)
                                txtThongBao.setVisibility(View.VISIBLE);
                                txtThongBao.setText("Hiện tại chưa có Voucher nào !!");
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.e("VolleyError", error.toString());
                        }
                    });

            requestQueue.add(jsonArrayRequest);
        } else {
            Log.d("ConnectionStatus", "No network connection.");
            CheckConnection.ShowToast_Short(getContext(), "Bạn hãy kiểm tra lại kết nối");
        }
    }
}
