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
import com.example.duan_android.Model.Movie;
import com.example.duan_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ViewMoreShowingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ViewMoreShowingFragment extends Fragment {
    private RecyclerView rcvMovie;
    private MovieAdapter mMovieAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ViewMoreShowingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ViewMoreShowingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ViewMoreShowingFragment newInstance(String param1, String param2) {
        ViewMoreShowingFragment fragment = new ViewMoreShowingFragment();
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
    public List<Movie> getListMovie(){
        List<Movie> list = new ArrayList<>();
        list.add(new Movie("Cám",R.drawable.image_item1));
        list.add(new Movie("Báo thủ tìm chủ",R.drawable.image_item2));
        list.add(new Movie("Không nói điều dữ",R.drawable.image_item3));
        list.add(new Movie("ODDITY",R.drawable.image_item4));
        list.add(new Movie("Robot hoang dã",R.drawable.image_run_5));
        list.add(new Movie("Joker",R.drawable.image_run_6));
        list.add(new Movie("Mộ đom đóm",R.drawable.image_item7));
        list.add(new Movie("Làm giàu với ma",R.drawable.image_item8));
        list.add(new Movie("Cậu bé cá heo",R.drawable.image_item9));
        list.add(new Movie("Chiêu hồn vong nhi",R.drawable.image_item16));
        list.add(new Movie("Fubao: Bảo bối của ông",R.drawable.image_item17));
        list.add(new Movie("Bé trứng náo loạn châu Phi",R.drawable.image_item18));

        return list;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_more_showing, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();
        rcvMovie = view.findViewById(R.id.vm_rcv_moive);
        mMovieAdapter = new MovieAdapter(context, new MovieAdapter.OnItemClickListener() {
            @Override
            public void onMovieClick(Movie movie) {

            }
        });
        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, 2);
        rcvMovie.setLayoutManager(gridLayoutManager);
        mMovieAdapter.setData(getListMovie());
        rcvMovie.setAdapter(mMovieAdapter);
    }
}