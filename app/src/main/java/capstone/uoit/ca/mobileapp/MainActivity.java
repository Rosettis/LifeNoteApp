package capstone.uoit.ca.mobileapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import capstone.uoit.ca.mobileapp.functions.Appointments.AppointmentsFragment;
import capstone.uoit.ca.mobileapp.functions.Doctors.DoctorsFragment;
import capstone.uoit.ca.mobileapp.functions.Notes.NotesFragment;
import capstone.uoit.ca.mobileapp.settings.SettingsFragment;
import capstone.uoit.ca.mobileapp.navbar.NavItemClickListener;
import capstone.uoit.ca.mobileapp.navbar.NavMenuAdapter;
import capstone.uoit.ca.mobileapp.navbar.NavMenuItem;
import capstone.uoit.ca.mobileapp.navbar.NavToggle;

import java.util.ArrayList;

/**
 * @author Matthew Rosettis
 */
public class MainActivity extends AppCompatActivity {
    private NavToggle navToggle;
    public static DrawerLayout navDrawer;
    private FragmentManager fragmentManager;
    private ViewPager viewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /*//Toolbar
        Toolbar toolbar = (Toolbar)findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            //actionBar.setHomeAsUpIndicator(R.drawable.ic_drawer);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }*/

        navDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView navList = (ListView) findViewById(R.id.left_drawer);

        fragmentManager = getSupportFragmentManager();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        Resources res = getResources();
        String[] navItems = res.getStringArray(R.array.nav_options);
        int[] navIcons = new int[]{
                R.drawable.ic_action_communication_textsms,
                R.drawable.ic_action_toggle_star,
                R.drawable.ic_action_action_store,
                R.drawable.ic_action_maps_local_offer,
                R.drawable.ic_action_file_file_upload,
                R.drawable.ic_action_social_person_outline,
                R.drawable.ic_action_action_settings,
                R.drawable.ic_action_av_fast_rewind
        };

        ArrayList<NavMenuItem> navOptions = new ArrayList<NavMenuItem>();
        for (int i = 0; i < navItems.length && i < navIcons.length; i++) {
            navOptions.add(new NavMenuItem(navIcons[i], navItems[i]));
        }

        navList.setAdapter(new NavMenuAdapter(this, R.layout.nav_list_item, navOptions));
        navList.setOnItemClickListener(new NavItemClickListener(this));

        navToggle = new NavToggle(this, navDrawer, R.string.nav_open, R.string.nav_close);
        navDrawer.setDrawerListener(navToggle);
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(fragmentManager);
        adapter.addFrag(NotesFragment.newInstance(), "Notes");
        /*adapter.addFrag(DoctorsFragment.newInstance(), "Doctors");
        adapter.addFrag(AppointmentsFragment.newInstance(), "Appointments");
        adapter.addFrag(SettingsFragment.newInstance(),"Settings");*/
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        navToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        System.out.println("case " + id);
        switch (id) {
            case R.id.action_settings:
                switchView(new SettingsFragment(),getString(R.string.settings_title));
                return true;
            case android.R.id.home:
                if(!navDrawer.isDrawerOpen(GravityCompat.START)) {
                    navDrawer.openDrawer(GravityCompat.START);
                }else{
                    navDrawer.closeDrawer(GravityCompat.START);
                }
                /*Toast.makeText(getApplicationContext(),
                        "Stop trying to leave",
                        Toast.LENGTH_SHORT).show();*/
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void logoutPressed(){
        startActivity(new Intent(this, StartActivity.class));
    }

    private void switchView(Fragment fragment, String fragTag) {
        System.out.println(fragTag);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onBackPressed(){
        Toast.makeText(getApplicationContext(),
                "Dick move bro, dick move",
                Toast.LENGTH_SHORT).show();
    }
}
