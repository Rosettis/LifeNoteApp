package capstone.uoit.ca.mobileapp.navbar;

import android.app.Activity;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;

/**
 * Created by nicholas on 30/10/15.
 */
public class NavToggle extends ActionBarDrawerToggle {
    public Activity activity;

    public NavToggle(Activity activity, DrawerLayout drawerLayout, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.activity = activity;
    }

    public NavToggle(Activity activity, DrawerLayout drawerLayout, Toolbar toolbar, int openDrawerContentDescRes, int closeDrawerContentDescRes) {
        super(activity, drawerLayout, toolbar, openDrawerContentDescRes, closeDrawerContentDescRes);
        this.activity = activity;
    }

    public void onDrawerClosed(View view) {
        super.onDrawerClosed(view);
        activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }

    public void onDrawerOpened(View drawerView) {
        super.onDrawerOpened(drawerView);
        activity.invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
    }


}
