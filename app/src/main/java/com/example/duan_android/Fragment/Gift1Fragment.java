package com.example.duan_android.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.duan_android.Adapter.AdapterGift;
import com.example.duan_android.Model.gift;

import com.example.duan_android.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Gift1Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Gift1Fragment extends Fragment {

    private ListView lv;
    private AdapterGift adapterGift;
    private ArrayList<gift> arrayList;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Gift1Fragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Gift1Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static Gift1Fragment newInstance(String param1, String param2) {
        Gift1Fragment fragment = new Gift1Fragment();
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
        View mview= inflater.inflate(R.layout.fragment_gift1, container, false);
        lv=mview.findViewById(R.id.lvgift);
        arrayList=new ArrayList<>();
        arrayList.add(new gift(R.drawable.voucher1,"Voucher drink 10%",100));
        arrayList.add(new gift(R.drawable.voucher2,"Voucher food 15%",150));
        arrayList.add(new gift(R.drawable.voucher3,"Voucher 99k",500));
        arrayList.add(new gift(R.drawable.voucher4,"Voucher 1 corn free 1 drink",300));
        adapterGift=new AdapterGift(getContext(),R.layout.layout_gift,arrayList);
        lv.setAdapter(adapterGift);

        return mview;
    }
}