package com.example.duan_android.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Adapter.DealAdapter;
import com.example.duan_android.ultil.CheckConnection;
import com.example.duan_android.Model.deal;
import com.example.duan_android.R;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class DealActivity extends AppCompatActivity {
    private ListView lv;
    private ArrayList<deal> arrayList;
    private DealAdapter adapter;
    private ImageView imgClose;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lv_deal);

        lv = findViewById(R.id.lvdeal);
        arrayList = new ArrayList<>();
        adapter = new DealAdapter(this, R.layout.layout_deal, arrayList);
        lv.setAdapter(adapter);

        imgClose = findViewById(R.id.btn_close);
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        // Lấy userId từ SharedPreferences
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", MODE_PRIVATE);
        String userId = sharedPreferences.getString("userId", null);

        if (userId != null) {
            // Gửi yêu cầu đến API getgiaodich.php với userId
            loadTransactionHistory(userId);
        }
    }

    private void loadTransactionHistory(String userId) {
        String url = Server.giaodich + userId;

        // Kiểm tra kết nối mạng
        if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
            Log.d("ConnectionStatus", "Network is connected.");

            // Tạo RequestQueue
            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

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
                                int IDHD=transaction.getInt("IDHoaDon");
                                String tenhinh = transaction.getString("HinhAnh");
                                int hinh=getResources().getIdentifier(tenhinh,"drawable",getPackageName());
                                String tenphim = transaction.getString("TenPhim");
                                String rap = transaction.getString("TenRapPhim");
                                String phongchieu = transaction.getString("TenPhongChieu");
                                String giochieu = transaction.getString("GioChieu");
                                String ngaychieu = transaction.getString("NgayChieu");

                                arrayList.add(new deal(IDHD,hinh,tenphim,rap,phongchieu,giochieu,ngaychieu));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        // Cập nhật adapter sau khi dữ liệu đã được thêm vào
                        adapter.notifyDataSetChanged();
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
        } else {
            Log.d("ConnectionStatus", "No network connection.");
            CheckConnection.ShowToast_Short(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối");
        }
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Lấy đối tượng 'deal' từ arrayList tại vị trí người dùng chọn
                deal selectedDeal = arrayList.get(position);

                // Lấy ID hóa đơn của giao dịch được chọn
                int idHoaDon = selectedDeal.getIdhd();

                // Mở một activity mới và truyền ID hóa đơn để lấy chi tiết giao dịch
                Intent intent = new Intent(DealActivity.this, QR.class);
                intent.putExtra("IDHoaDon", idHoaDon);
                startActivity(intent);
            }
        });

    }
}