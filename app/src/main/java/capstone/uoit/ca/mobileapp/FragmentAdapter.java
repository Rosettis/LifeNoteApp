package capstone.uoit.ca.mobileapp;/**
 * Created by 100490515 on 11/16/2015.
 */

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * FragmentAdapter
 *
 * @author Matthew Rosettis
 */
public class FragmentAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> mFragments;
    private List<String> tags;

    public FragmentAdapter(FragmentManager fm) {
        super(fm);
        tags = new ArrayList<>();
        mFragments = new ArrayList<>();
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }


    public void addFrag(Fragment fragment, String title) {
        mFragments.add(fragment);
        tags.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return null;
    }
}
