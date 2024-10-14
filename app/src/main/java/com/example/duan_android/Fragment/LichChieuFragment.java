package com.example.duan_android.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;


import com.example.duan_android.Adapter.AdapterLichChieu;
import com.example.duan_android.Adapter.DateAdapter;
import com.example.duan_android.Model.lichchieu;
import com.example.duan_android.R;
import com.google.android.material.tabs.TabLayout;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LichChieuFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class LichChieuFragment extends Fragment {

    private Spinner sprap,sptinhthanh;
    private TabLayout tab;
    private ViewPager viewPager;
    private DateAdapter adapter;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LichChieuFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LichChieuFragment newInstance(String param1, String param2) {
        LichChieuFragment fragment = new LichChieuFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public LichChieuFragment() {
        // Required empty public constructor
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
        View mview = inflater.inflate(R.layout.fragment_lich_chieu, container, false);


        sprap = mview.findViewById(R.id.spcinema);
        sptinhthanh =mview.findViewById(R.id.spaddress);
        tab=mview.findViewById(R.id.tab_date);
        viewPager=mview.findViewById(R.id.viewpagerdate);

        String [] diadiem={"Chọn tỉnh/thành","Đà Nẵng","Huế","Hồ Chí Minh","Hà Nội","Quảng Nam"};

        ArrayAdapter<String> spinnerAdapterAddress = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, diadiem);
        spinnerAdapterAddress.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sptinhthanh.setAdapter(spinnerAdapterAddress);

        String [] cinemas={"Chọn rạp","Galaxy Nguyễn Du","Galaxy Sala","Galaxy Tân Bình","Galaxy Quang Trung"};

        ArrayAdapter<String> spinnerAdapterCinema = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, cinemas);
        spinnerAdapterCinema.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sprap.setAdapter(spinnerAdapterCinema);

        adapter=new DateAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT, false);
        viewPager.setAdapter(adapter);
        tab.setupWithViewPager(viewPager);

        for (int i = 0; i < tab.getTabCount(); i++) {
            TabLayout.Tab tablayout_date = tab.getTabAt(i);
            if (tablayout_date != null) {
                View customView = getLayoutInflater().inflate(R.layout.custom_tab, null);

                TextView tabTitle = customView.findViewById(R.id.tab_title);
                TextView tabDate = customView.findViewById(R.id.tab_date);

                switch (i) {
                    case 0:
                        tabTitle.setText("Thứ 4");
                        tabDate.setText("25/09");
                        break;
                    case 1:
                        tabTitle.setText("Thứ 5");
                        tabDate.setText("26/09");
                        break;
                    case 2:
                        tabTitle.setText("Thứ 6");
                        tabDate.setText("27/09");
                        break;
                    case 3:
                        tabTitle.setText("Thứ 7");
                        tabDate.setText("28/09");
                        break;
                }

                tablayout_date.setCustomView(customView);
            }
        }

        return mview;

    }
}