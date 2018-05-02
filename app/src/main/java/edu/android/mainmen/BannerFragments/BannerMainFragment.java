package edu.android.mainmen.BannerFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.android.mainmen.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class BannerMainFragment extends Fragment {



    //Tabbed


    public BannerMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_banner1, container, false);


        return view;


    }


}
