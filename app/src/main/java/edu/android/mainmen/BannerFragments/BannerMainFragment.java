package edu.android.mainmen.BannerFragments;


import android.app.Activity;
import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.android.mainmen.Adapter.SectionsBannerPageAdapter;
import edu.android.mainmen.Adapter.SectionsPageAdapter;
import edu.android.mainmen.FoodListFragment;
import edu.android.mainmen.R;
import edu.android.mainmen.ReviewFragment.ReadReviewFragment;
import edu.android.mainmen.ReviewFragment.ReviewBossamFragment;
import edu.android.mainmen.ReviewFragment.ReviewChikenFragment;
import edu.android.mainmen.ReviewFragment.ReviewChinaFragment;
import edu.android.mainmen.ReviewFragment.ReviewFastFoodFragment;
import edu.android.mainmen.ReviewFragment.ReviewJapanFragment;
import edu.android.mainmen.ReviewFragment.ReviewKoreanFragment;
import edu.android.mainmen.ReviewFragment.ReviewSnackBarFragment;
import edu.android.mainmen.ReviewFragment.ReviewWesternFragment;


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
        View view = inflater.inflate(R.layout.fragment_banner, container, false);


        return view;


    }


}
