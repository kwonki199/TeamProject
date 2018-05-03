package edu.android.mainmen.Banner;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import edu.android.mainmen.R;

/**
 * A simple {@link Fragment} subclass.
 */

// 이 달의 하트왕 배너
public class Banner4Fragment extends Fragment {

    private ImageView bannerImageView4;

    public Banner4Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_banner4, container, false);
        bannerImageView4 = view.findViewById(R.id.bannerImageView4);
        bannerImageView4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), HeartKingActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

}
