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
import android.widget.Button;

import com.example.duan_android.Activity.LC_TT_Activity;
import com.example.duan_android.Activity.ViewMoreActivity;
import com.example.duan_android.Adapter.MovieAdapter;
import com.example.duan_android.Model.Movie;
import com.example.duan_android.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ComingSoonFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ComingSoonFragment extends Fragment {
    private RecyclerView rcvMovieCS;
    private MovieAdapter mMovieAdapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private Button btnShowingVMCS;

    public ComingSoonFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ComingSoonFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ComingSoonFragment newInstance(String param1, String param2) {
        ComingSoonFragment fragment = new ComingSoonFragment();
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
        list.add(new Movie("Qủy ăn tạng Phần 2",R.drawable.image_item10));
        list.add(new Movie("Lối thoát cuối cùng",R.drawable.image_item11));
        list.add(new Movie("Fantomat",R.drawable.image_item12));
        list.add(new Movie("Cô dâu hào môn",R.drawable.image_item13));
        list.add(new Movie("Đố anh còng được tôi",R.drawable.image_item14));
        list.add(new Movie("Hẹn hò với sát nhân",R.drawable.image_item15));
        list.add(new Movie("Cậu bé cá heo",R.drawable.image_item9));

        return list;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_coming_soon, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Context context = getContext();
        rcvMovieCS = view.findViewById(R.id.rcv_moiveCS);
        mMovieAdapter = new MovieAdapter(context, new MovieAdapter.OnItemClickListener() {
            @Override
            public void onMovieClick(Movie movie) {
               
            }
        });
        btnShowingVMCS = view.findViewById(R.id.btnXemTiepCS);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
        rcvMovieCS.setLayoutManager(gridLayoutManager);
        mMovieAdapter.setData(getListMovie());
        rcvMovieCS.setAdapter(mMovieAdapter);


        btnShowingVMCS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewMoreActivity.class);
                intent.putExtra("selected_tab", 1);
                startActivity(intent);

            }
        });
    }
}