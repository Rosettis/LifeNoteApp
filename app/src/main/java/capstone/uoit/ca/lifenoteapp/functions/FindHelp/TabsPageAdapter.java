package capstone.uoit.ca.lifenoteapp.functions.FindHelp;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Matthew on 4/2/2016.
 */
public class TabsPageAdapter extends FragmentPagerAdapter {
    public TabsPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                // Hospitals fragment activity
                return new HospitalsFragment();
            case 1:
                // Clinics fragment activity
                return new ClinicFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 2;
    }

}
