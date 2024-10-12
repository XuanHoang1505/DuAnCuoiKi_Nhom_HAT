package com.example.duan_android.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.duan_android.Adapter.AdapterLichChieu;
import com.example.duan_android.Model.lichchieu;
import com.example.duan_android.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Date4Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Date4Fragment extends Fragment {

    private ListView lv;
    private AdapterLichChieu adapter;
    private List<lichchieu> lichChieuList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Date4Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Date1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Date4Fragment newInstance(String param1, String param2) {
        Date4Fragment fragment = new Date4Fragment();
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
        View mview = inflater.inflate(R.layout.fragment_date4, container, false);
        lv = mview.findViewById(R.id.lvgiochieu);
        
        lichChieuList = new ArrayList<>();
        lichChieuList.add(new lichchieu("Galaxy Nguyễn Du", Arrays.asList("10:00", "12:00", "14:00")));
        lichChieuList.add(new lichchieu("Galaxy Sala", Arrays.asList("15:00", "16:30")));
        lichChieuList.add(new lichchieu("Galaxy Tân Bình", Arrays.asList("11:30", "13:30")));

        adapter = new AdapterLichChieu(getContext(), R.layout.layout_lichchieu, lichChieuList);
        lv.setAdapter(adapter);

        return mview;
    }
}