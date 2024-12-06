package com.example.duan_android.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.duan_android.Adapter.CommentAdapter;
import com.example.duan_android.Model.Comment;
import com.example.duan_android.R;
import com.example.duan_android.ultil.CheckConnection;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CommentFragment extends Fragment {
    private RecyclerView rcvComment;
    private CommentAdapter commentAdapter;
    private List<Comment> commentList;

    // Các tham số truyền vào Fragment (nếu cần)
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    public CommentFragment() {
        // Required empty public constructor
    }

    public static CommentFragment newInstance(String param1, String param2) {
        CommentFragment fragment = new CommentFragment();
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
        // Inflate layout cho fragment
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Context context = getContext();
        rcvComment = view.findViewById(R.id.rcv_comment);
        commentList = new ArrayList<>();

        if (CheckConnection.haveNetworkConnection(requireContext())) {
            getComments(); // Lấy dữ liệu từ server
        } else {
            CheckConnection.ShowToast_Short(requireContext(), "Hãy kiểm tra lại kết nối mạng.");
        }

        // Khởi tạo adapter
        commentAdapter = new CommentAdapter(context, commentList);

        // Thiết lập RecyclerView với GridLayoutManager
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 1);
        rcvComment.setLayoutManager(gridLayoutManager);
        rcvComment.setAdapter(commentAdapter);
    }

    private void getComments() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.path_Comment, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String preview = jsonObject.getString("NoiDung");
                            String resourceName = jsonObject.getString("HinhAnh");
                            int resourceImage = getResources().getIdentifier(resourceName, "drawable", requireContext().getPackageName());
                            commentList.add(new Comment(resourceImage, preview));
                            commentAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Xử lý lỗi
                CheckConnection.ShowToast_Short(requireContext(), "Lỗi khi tải bình luận.");
            }
        });

        requestQueue.add(jsonArrayRequest);
    }
}
