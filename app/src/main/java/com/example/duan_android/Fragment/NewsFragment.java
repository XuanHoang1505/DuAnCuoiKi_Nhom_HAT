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
import com.example.duan_android.Model.Movie;
import com.example.duan_android.R;
import com.example.duan_android.ultil.CheckConnection;
import com.example.duan_android.ultil.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    private RecyclerView rcvNews;
    private CommentAdapter mNewsAdapter;
    private List<Comment> newstList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public NewsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsFragment newInstance(String param1, String param2) {
        NewsFragment fragment = new NewsFragment();
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
        return inflater.inflate(R.layout.fragment_news, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();
        rcvNews = view.findViewById(R.id.rcv_news);
        newstList = new ArrayList<>();

        if (CheckConnection.haveNetworkConnection(requireContext())) {
            getNews(); // Lấy dữ liệu từ server
        } else {
            CheckConnection.ShowToast_Short(requireContext(), "Hãy kiểm tra lại kết nối mạng.");
        }


        mNewsAdapter = new CommentAdapter(context, newstList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,1);

        rcvNews.setLayoutManager(gridLayoutManager);
        rcvNews.setAdapter(mNewsAdapter);



    }
    private void getNews() {
        RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.path_news, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            String preview = jsonObject.getString("NoiDung");
                            String resourceName = jsonObject.getString("HinhAnh");
                            int resourceImage = getResources().getIdentifier(resourceName, "drawable", requireContext().getPackageName());
                            newstList.add(new Comment(resourceImage, preview));
                            mNewsAdapter.notifyDataSetChanged();
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