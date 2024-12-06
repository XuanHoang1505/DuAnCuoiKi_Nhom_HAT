package com.example.duan_android.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Adapter.AdapterVoucher;
import com.example.duan_android.Model.Voucher;
import com.example.duan_android.R;
import com.example.duan_android.ultil.CheckConnection;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Gift1Fragment extends Fragment {

    private ListView lv;
    private AdapterVoucher adapterVoucher;
    private ArrayList<Voucher> voucherList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_gift1, container, false);
        lv = mView.findViewById(R.id.lvgift);
        voucherList = new ArrayList<>();
        adapterVoucher = new AdapterVoucher(getContext(), R.layout.layout_gift, voucherList);
        lv.setAdapter(adapterVoucher);

        // Kiểm tra kết nối mạng
        if (CheckConnection.haveNetworkConnection(getContext())) {
            getVoucherList();
        } else {
            Toast.makeText(getContext(), "Hãy kiểm tra lại kết nối mạng", Toast.LENGTH_SHORT).show();
        }

        return mView;
    }

    private void getVoucherList() {
        String url = Server.path_VoucherSystem; // Make sure Server.path_VoucherSystem has the correct API URL
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);

                        // Lấy thông tin từ JSON
                        int id = jsonObject.getInt("IDVoucher");
                        String hinhAnh = jsonObject.getString("HinhAnh");
                        String tenVoucher = jsonObject.getString("TenVoucher");
                        int soDiemDoi = jsonObject.getInt("SoDiemDoi");
                        int soTienGiam = jsonObject.getInt("SoTienGiam");

                        // Chuyển đổi hình ảnh từ tên thành ID tài nguyên
                        int resourceImage = getResources().getIdentifier(hinhAnh, "drawable", getContext().getPackageName());

                        // Thêm vào danh sách
                        voucherList.add(new Voucher(id, tenVoucher, resourceImage, soDiemDoi, soTienGiam));
                    }
                    adapterVoucher.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), "Lỗi xử lý dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "Lỗi kết nối API", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}
