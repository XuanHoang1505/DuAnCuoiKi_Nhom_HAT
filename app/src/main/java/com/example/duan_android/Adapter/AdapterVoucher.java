package com.example.duan_android.Adapter;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Model.Voucher;
import com.example.duan_android.R;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AdapterVoucher extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Voucher> voucherList;
    private TextView mypoint;
    private EditText point;

    public AdapterVoucher(Context context, int layout, List<Voucher> voucherList) {
        this.context = context;
        this.layout = layout;
        this.voucherList = voucherList;
    }

    @Override
    public int getCount() {
        return voucherList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = inflater.inflate(layout, null);

        // Lấy dữ liệu từ danh sách
        Voucher voucher = voucherList.get(i);

        // Ánh xạ view
        ImageView imgVoucher = view.findViewById(R.id.imggift);
        TextView txtTenVoucher = view.findViewById(R.id.namegift);
        TextView txtSoDiemDoi = view.findViewById(R.id.pointgift);

        imgVoucher.setImageResource(voucher.getHinhAnh());
        txtTenVoucher.setText(voucher.getTenVoucher());
        txtSoDiemDoi.setText(String.valueOf(voucher.getSoDiemDoi()));
        Button btnChange = view.findViewById(R.id.btn_change);
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(voucher.getIdVoucher(),voucher.getSoDiemDoi());
            }
        });

        return view;
    }

    private void showDialog(int idvc, int diemvc) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_change);
        dialog.show();
        Button btnclose = dialog.findViewById(R.id.btn_close);
        Button btnsubmit = dialog.findViewById(R.id.btn_doi);
        mypoint = dialog.findViewById(R.id.txt_diem);
        point = dialog.findViewById(R.id.ed_point);

        SharedPreferences spkh = context.getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String idkh = spkh.getString("userId", null);

        if (idkh != null) {
            String url = Server.getdiemthuong + idkh;
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);

                        if (jsonArray.length() > 0) {
                            JSONObject jsonObject = jsonArray.getJSONObject(0); // Lấy đối tượng đầu tiên trong mảng
                            if (jsonObject.has("DiemThuong")) {
                                String diemThuong = jsonObject.getString("DiemThuong");
                                mypoint.setText(diemThuong);

                                int diem = Integer.parseInt(diemThuong);
                                int maxQuantity = diem / diemvc;

                                btnsubmit.setOnClickListener(view -> {
                                    try {
                                        int diemdoi = Integer.parseInt(point.getText().toString()); // Điểm đổi voucher

                                        if (diemdoi == 0 || diemdoi % diemvc != 0) {
                                            Toast.makeText(context, "Số điểm bạn nhập không hợp lệ", Toast.LENGTH_SHORT).show();
                                        } else {
                                            int sl = diemdoi / diemvc;
                                            if (sl > maxQuantity) {
                                                Toast.makeText(context, "Số điểm bạn nhập vượt quá số lượng tối đa có thể đổi", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Log.d("API_Request", "IDKhachHang: " + idkh + ", IDVoucher: " + idvc + ", SoLuong: " + sl + ", DiemDoi: " + diemdoi);
                                                doivc(idkh, idvc, sl, diemdoi);

                                                Toast.makeText(context, "Bạn đã đổi " + sl + " voucher thành công", Toast.LENGTH_SHORT).show();
                                                dialog.dismiss();
                                            }
                                        }
                                    } catch (NumberFormatException e) {
                                        Toast.makeText(context, "Điểm không hợp lệ", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(context, "Lỗi khi xử lý dữ liệu", Toast.LENGTH_SHORT).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(context, "Lỗi kết nối với server", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(context, "ID khách hàng không hợp lệ", Toast.LENGTH_SHORT).show();
        }


        btnclose.setOnClickListener(view -> dialog.dismiss());
    }


    private void doivc(String idkh, int idvc, int sl,int diemdoi) {
        String url = Server.postvc;
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, response -> {

        }, error -> {
            Log.e("VolleyError", error.toString());
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("IDKhachHang", idkh);
                params.put("IDVoucher", String.valueOf(idvc));
                params.put("SoLuong", String.valueOf(sl));
                params.put("DiemDoi", String.valueOf(diemdoi));
                return params;
            }
        };
        requestQueue.add(stringRequest);
    }

}