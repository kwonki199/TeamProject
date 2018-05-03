package edu.android.mainmen.Banner;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import edu.android.mainmen.DrawerMenu.RouletteActivity;
import edu.android.mainmen.R;

/**
 * A simple {@link Fragment} subclass.
 */



// 행운의 룰렛 배너

public class Banner3Fragment extends Fragment {

    private ImageView bannerImageView3;
    public Banner3Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_banner3, container, false);
        bannerImageView3 = view.findViewById(R.id.bannerImageView3);
        bannerImageView3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), RouletteActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
