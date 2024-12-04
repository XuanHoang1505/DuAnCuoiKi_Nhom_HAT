package com.example.duan_android.Fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Activity.DealActivity;
import com.example.duan_android.Activity.GiftActivity;
import com.example.duan_android.Activity.InformationActivity;
import com.example.duan_android.Activity.LoginActivity;
import com.example.duan_android.Activity.ThongTinNhomActivity;
import com.example.duan_android.R;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {
    private TextView txtName;
    private TextView txtPoint, currentSpend;
    private Button btnInfor;
    private View mView;
    private Button trade;
    private ImageView gift,thongTin;
    private ImageView myGift;
    private Button btn_logout;
    private int currentSpendAmount = 0;
    private ProgressBar progressBar;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
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

        mView = inflater.inflate(R.layout.fragment_account, container, false);
        btnInfor =mView.findViewById(R.id.btnInfor);
        trade = mView.findViewById(R.id.trade);
        progressBar = mView.findViewById(R.id.progressBar);
        currentSpend = mView.findViewById(R.id.tv_current_spend);
        gift =  mView.findViewById(R.id.exchange_gift);
        myGift = mView.findViewById(R.id.myGift);
        btn_logout = mView.findViewById(R.id.logout);
        txtName = mView.findViewById(R.id.name);
        txtPoint = mView.findViewById(R.id.point);
        thongTin = mView.findViewById(R.id.thongTin);

        int maxSpend = 4000000;
        progressBar.setMax(maxSpend);
        SharedPreferences spkh = getActivity().getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String idkh = spkh.getString("userId", null);
        if (idkh != null) {
            diemthuong(idkh);
            tongtien(idkh);
        } else {
            Toast.makeText(getActivity().getApplicationContext(), "ID khách hàng không hợp lệ", Toast.LENGTH_SHORT).show();
        }




        btnInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), InformationActivity.class);
                startActivity(intent);
            }
        });
        trade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), DealActivity.class);
                startActivity(intent);
            }
        });

        gift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(getActivity(), GiftActivity.class);
                intent.putExtra("selected_tab", 0);
                startActivity(intent);
            }
        });

        myGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), GiftActivity.class);
                intent.putExtra("selected_tab", 1);
                startActivity(intent);
            }
        });
        thongTin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent  intent = new Intent(getActivity(), ThongTinNhomActivity.class);
                startActivity(intent);
            }
        });
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Tạo dialog xác nhận
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Xác nhận đăng xuất");
                builder.setMessage("Bạn có chắc muốn đăng xuất?");

                // Nút đồng ý
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Xử lý đăng xuất ở đây
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                });

                // Nút hủy bỏ
                builder.setNegativeButton("Hủy bỏ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // Đóng dialog nếu người dùng chọn hủy bỏ
                        dialogInterface.dismiss();
                    }
                });

                // Hiển thị dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("UserPrefs", getContext().MODE_PRIVATE);
        String userName = sharedPreferences.getString("userName", null);
        txtName.setText(userName);
        return mView;
    }
    private void diemthuong(String idkh){
        String url = Server.getdiemthuong + idkh;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() > 0) {
                        JSONObject jsonObject = jsonArray.getJSONObject(0); // Lấy đối tượng đầu tiên trong mảng
                        if (jsonObject.has("DiemThuong")) {
                            String diemThuong = jsonObject.getString("DiemThuong");
                            txtPoint.setText(diemThuong);

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "Lỗi khi xử lý dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Lỗi kết nối với server", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }

    private void tongtien(String idkh){
        String url = Server.gettongtien + idkh;
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity().getApplicationContext());
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONArray jsonArray = new JSONArray(response);
                    if (jsonArray.length() > 0) {
                        JSONObject jsonObject = jsonArray.getJSONObject(0); // Lấy đối tượng đầu tiên trong mảng
                        if (jsonObject.has("TongTien")) {
                            int tongtien = jsonObject.getInt("TongTien");
                            currentSpendAmount=tongtien;
                            progressBar.setProgress(currentSpendAmount);
                            currentSpend.setText(currentSpendAmount + "đ");
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity().getApplicationContext(), "Lỗi khi xử lý dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Lỗi kết nối với server", Toast.LENGTH_SHORT).show();
            }
        });
        requestQueue.add(stringRequest);
    }
}