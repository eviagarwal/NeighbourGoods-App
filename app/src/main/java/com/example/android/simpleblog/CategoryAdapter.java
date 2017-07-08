package com.example.android.simpleblog;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by DELL PC on 4/11/2017.
 */

public class CategoryAdapter extends FragmentPagerAdapter {
    private Context mContext;


    public CategoryAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new ElectronicsFragment();
        } else if (position == 1) {
            return new StudyMaterialsFragment();
        } else  {
            return new OthersFragment();
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return mContext.getString(R.string.category_electronics);
        } else if (position == 1) {
            return mContext.getString(R.string.category_studyMaterials);
        } else  {
            return mContext.getString(R.string.category_others);
        }

    }
}
