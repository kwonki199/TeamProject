package edu.android.mainmen.Adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leedonghee on 2018. 4. 17..
 */

public class SectionsBannerPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();

    public void addFragment(Fragment fragment) {
        mFragmentList.add(fragment);

    }

    public void addOnlyFragment(Fragment fragment) {
        mFragmentList.add(fragment);
    }




    public SectionsBannerPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Nullable

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }


}


