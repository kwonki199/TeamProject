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
public class Banner1Fragment extends Fragment {

    private ImageView bannerImageView1;

    //Tabbed


    public Banner1Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_banner1, container, false);
        bannerImageView1 = view.findViewById(R.id.bannerImageView1);
        bannerImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Post1Activity.class);
                startActivity(intent);
            }
        });


        return view;


    }


}
