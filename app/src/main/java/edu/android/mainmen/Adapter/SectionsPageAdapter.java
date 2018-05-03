package edu.android.mainmen.Adapter;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import edu.android.mainmen.MainActivity;

/**
 * Created by leedonghee on 2018. 4. 17..
 */

public class SectionsPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> mFragmentList = new ArrayList<>();
    private List<String> mFragmentTitleList = new ArrayList<>();

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

        public SectionsPageAdapter(FragmentManager fm) {
        super(fm);
    }


    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public Fragment getItem(int position) {
//        Log.i(MainActivity.TAG, "SectionsPageAdapter getItem: position=" + position);
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

}


