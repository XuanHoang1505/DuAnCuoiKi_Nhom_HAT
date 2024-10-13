package com.example.duan_android.Fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import com.example.duan_android.Model.cinema;
import com.example.duan_android.Adapter.AdapterCinema;
import com.example.duan_android.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CinemaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CinemaFragment extends Fragment {
    private EditText location;
    private ListView lv;
    private ArrayList<cinema> arrayList;
    private AdapterCinema adapter;
    private String selectedLocation = "Toàn quốc";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CinemaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CinemaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CinemaFragment newInstance(String param1, String param2) {
        CinemaFragment fragment = new CinemaFragment();
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
        View mview =inflater.inflate(R.layout.fragment_cinema, container, false);
        lv=mview.findViewById(R.id.lviewcinema);
        location=mview.findViewById(R.id.editTextText);
        arrayList=new ArrayList<>();
        arrayList.add(new cinema(R.drawable.nguyendu, "Galaxy Nguyễn Du", "116 Nguyễn Du, Quận 1, Tp.HCM", "1900 2224"));
        arrayList.add(new cinema(R.drawable.sala, "Galaxy SaLa", "Tầng 3, Thiaso Mall SaLa", "1900 2224"));
        arrayList.add(new cinema(R.drawable.tanbinh, "Galaxy Tân Bình", "246 Nguyễn Hồng Đào, Quận Tân Bình, Tp.HCM", "1900 2224"));
        arrayList.add(new cinema(R.drawable.kdv, "Galaxy Kinh Dương Vương", "Galaxy Kinh Dương Vương", "1900 2224"));
        arrayList.add(new cinema(R.drawable.quangtrung, "Galaxy Quang trung", "Lầu 3, TTTM CoopMart Foodcosa ", "1900 2224"));
        arrayList.add(new cinema(R.drawable.nguyendu, "Galaxy Nguyễn Du", "116 Nguyễn Du, Quận 1, Tp.HCM", "1900 2224"));
        arrayList.add(new cinema(R.drawable.sala, "Galaxy SaLa", "Tầng 3, Thiaso Mall SaLa", "1900 2224"));
        arrayList.add(new cinema(R.drawable.tanbinh, "Galaxy Tân Bình", "246 Nguyễn Hồng Đào, Quận Tân Bình, Tp.HCM", "1900 2224"));
        arrayList.add(new cinema(R.drawable.kdv, "Galaxy Kinh Dương Vương", "Galaxy Kinh Dương Vương", "1900 2224"));
        arrayList.add(new cinema(R.drawable.quangtrung, "Galaxy Quang trung", "Lầu 3, TTTM CoopMart Foodcosa ", "1900 2224"));
        adapter = new AdapterCinema(getContext(),R.layout.layout_cinema,arrayList);
        lv.setAdapter(adapter);

        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLocationDialog();
            }
        });

        return mview;
    }
    private void showLocationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Vị trí");

        // Tạo danh sách các địa điểm
        String[] locations = {"Toàn quốc", "TP Hồ Chí Minh", "Hà Nội", "Đà Nẵng", "An Giang",
                "Bà Rịa - Vũng Tàu", "Bến Tre", "Cà Mau", "Đắk Lắk", "Hải Phòng",
                "Khánh Hòa", "Nghệ An"};

        // Mảng boolean lưu trạng thái chọn
        int checkedItem = -1; // Không có lựa chọn mặc định

        // Set danh sách radio button
        builder.setSingleChoiceItems(locations, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Lưu vị trí được chọn vào biến selectedLocation
                selectedLocation = locations[which];
            }
        });

        // Thêm nút Xác nhận
        builder.setPositiveButton("Xác nhận", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Cập nhật TextView với địa điểm đã chọn
                location.setText(selectedLocation);
            }
        });

        // Thêm nút Đóng
        builder.setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        // Hiển thị dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}