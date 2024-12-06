package com.example.duan_android.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.R;
import com.example.duan_android.ultil.Server;

import org.json.JSONObject;

public class ThongTinFragment extends Fragment {

    private boolean trangthai = false; // Trạng thái xem tiếp/thu gọn

    public ThongTinFragment() {
        // Required empty public constructor
    }

    public static ThongTinFragment newInstance() {
        return new ThongTinFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mview = inflater.inflate(R.layout.fragment_thong_tin, container, false);

        TextView nd = mview.findViewById(R.id.txtnoidung);
        TextView xemtiep = mview.findViewById(R.id.txtxemtiep);
        TextView daoDien = mview.findViewById(R.id.txtdaodien);

        // Lấy IdPhim từ SharedPreferences
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences("ShareIdPhim", MODE_PRIVATE);
        int IdPhim = sharedPreferences.getInt("IdPhim", -1);

        // Nếu IdPhim hợp lệ, thực hiện lấy thông tin phim
        if (IdPhim != -1) {
            MovieDetails(IdPhim, nd, daoDien);
        }

        // Xử lý sự kiện "Xem tiếp"
        xemtiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (trangthai) {
                    nd.setMaxLines(3); // Thu gọn nội dung
                    nd.setEllipsize(android.text.TextUtils.TruncateAt.END);
                    xemtiep.setText("Xem tiếp");
                } else {
                    nd.setMaxLines(Integer.MAX_VALUE); // Mở rộng nội dung
                    nd.setEllipsize(null);
                    xemtiep.setText("Thu gọn");
                }
                trangthai = !trangthai; // Đổi trạng thái
            }
        });

        return mview;
    }

    private void MovieDetails(int IdPhim, TextView nd, TextView daoDien) {
        String url = Server.path_getMovieById + IdPhim;

        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    // Lấy dữ liệu từ JSON Object
                    String noiDung = response.getString("NoiDung");
                    String tenDaoDien = response.getString("DaoDien");

                    // Cập nhật giao diện
                    nd.setText(noiDung);
                    daoDien.setText(tenDaoDien);

                } catch (Exception e) {
                    Log.e("ThongTinFragment", "Error parsing movie details", e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("ThongTinFragment", "Error fetching movie details", error);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }
}
