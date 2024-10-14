package com.example.duan_android.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.duan_android.Adapter.AdapterMyGift;
import com.example.duan_android.Model.mygift;
import com.example.duan_android.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Gift3Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Gift3Fragment extends Fragment {
    private ListView lv;
    private AdapterMyGift adapterMyGift;
    private ArrayList<mygift> arrayList;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Gift3Fragment() {
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
    public static Gift3Fragment newInstance(String param1, String param2) {
        Gift3Fragment fragment = new Gift3Fragment();
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
        View mview= inflater.inflate(R.layout.fragment_gift2, container, false);
        lv=mview.findViewById(R.id.lvgift);
        arrayList=new ArrayList<>();
        arrayList.add(new mygift(R.drawable.voucher1,"Voucher drink 10%",2));
        arrayList.add(new mygift(R.drawable.voucher2,"Voucher food 15%",1));
        adapterMyGift=new AdapterMyGift(getContext(),R.layout.layout_giftused,arrayList);
        lv.setAdapter(adapterMyGift);

        return mview;
    }
}