package com.example.duan_android.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan_android.Activity.ViewMoreActivity;
import com.example.duan_android.Adapter.MovieAdapter;
import com.example.duan_android.Model.Comment;
import com.example.duan_android.Model.Movie;
import com.example.duan_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CommentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CommentFragment extends Fragment {
    private RecyclerView rcvComment;
    private MovieAdapter mCommentAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CommentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CommentFragment.
     */
    // TODO: Rename and change types and number of parameters
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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();
        rcvComment = view.findViewById(R.id.rcv_comment);
        mCommentAdapter = new MovieAdapter(context, new MovieAdapter.OnItemClickListener() {
            @Override
            public void onMovieClick(Movie movie) {

            }
        });

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,1);

        rcvComment.setLayoutManager(gridLayoutManager);

        mCommentAdapter.setDataC(getListComment());

        rcvComment.setAdapter(mCommentAdapter);



    }
    private List<Comment> getListComment() {
        List<Comment> list = new ArrayList<>();
        list.add(new Comment(R.drawable.image_cmt1,"[Preview] Venom The Last Dance: Sony Có Lấy Lại Lòng Tin Sau Morbius & Madame Web?"));
        list.add(new Comment(R.drawable.image_cmt2,"[Review] Transformers One: Xuất Sắc Ngoài Mong Đợi"));
        list.add(new Comment(R.drawable.image_cmt3,"[Review] Joker Folie À Deux: Gã Hề Hay Hoàng Tử Tội Phạm?"));
        list.add(new Comment(R.drawable.image_cmt4,"[Review] Cám: Phiên Bản Lạnh Gáy Chưa Từng Có Từ Cổ Tích Quen Thuộc!/"));
        list.add(new Comment(R.drawable.image_cmt5,"[Preview] Joker: Folie à Deux: Có Chấn Động Thế Giới Như Phần Trước?"));
        return list;
    }
}