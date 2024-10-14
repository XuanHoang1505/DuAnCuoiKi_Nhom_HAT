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

import com.example.duan_android.Adapter.MovieAdapter;
import com.example.duan_android.Model.Comment;
import com.example.duan_android.Model.Movie;
import com.example.duan_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsFragment extends Fragment {
    private RecyclerView rcvNews;
    private MovieAdapter mNewsAdapter;
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
        mNewsAdapter = new MovieAdapter(context, new MovieAdapter.OnItemClickListener() {
            @Override
            public void onMovieClick(Movie movie) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,1);

        rcvNews.setLayoutManager(gridLayoutManager);

        mNewsAdapter.setDataC(getListComment());

        rcvNews.setAdapter(mNewsAdapter);



    }
    private List<Comment> getListComment() {
        List<Comment> list = new ArrayList<>();
        list.add(new Comment(R.drawable.image_news1,"Mufasa: The Lion King Tiết Lộ Hành Trình Mufasa Trở Thành Vua Sư Tử Vĩ Đại"));
        list.add(new Comment(R.drawable.image_news2,"Đếm 500 Cameo Từ Deadpool & Wolverine"));
        list.add(new Comment(R.drawable.image_news3,"Despicable Me 4: Chúng Ta Biết Được Bao Nhiêu Về Minions?"));
        list.add(new Comment(R.drawable.image_news4,"Top 10 Phim Tình Cảm Hay Nhất 2024"));
        list.add(new Comment(R.drawable.image_cmt1,"Venom Sẽ Chết?"));
        return list;
    }
}