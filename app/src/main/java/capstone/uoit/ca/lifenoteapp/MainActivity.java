package capstone.uoit.ca.lifenoteapp;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.functions.Graphs.GraphHome;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.ViewNotesFragment;
import capstone.uoit.ca.lifenoteapp.navbar.NavItemClickListener;
import capstone.uoit.ca.lifenoteapp.navbar.NavMenuAdapter;
import capstone.uoit.ca.lifenoteapp.navbar.NavMenuItem;
import capstone.uoit.ca.lifenoteapp.navbar.NavToggle;
import capstone.uoit.ca.lifenoteapp.settings.SettingsFragment;

/**
 * @author Matthew Rosettis
 */
public class MainActivity extends AppCompatActivity {
    public static DrawerLayout navDrawer;
    private NavToggle navToggle;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Sets the colour parameter for the Android status bar
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
        }
        //Adding the Nav Drawer to the activity
        navDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ListView navList = (ListView) findViewById(R.id.left_drawer);
        //Adding the main view page
        fragmentManager = getSupportFragmentManager();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        //adding the nav drawer icons
        Resources res = getResources();
        String[] navItems = res.getStringArray(R.array.nav_options);
        int[] navIcons = new int[]{
                R.drawable.ic_action_communication_textsms,
                R.drawable.ic_action_toggle_star,
                R.drawable.ic_action_action_store,
                R.drawable.ic_action_maps_local_offer,
                R.drawable.ic_action_file_file_upload,
                R.drawable.ic_action_maps_local_play,
                R.drawable.ic_action_social_person_outline,
                R.drawable.ic_action_action_settings,
                R.drawable.ic_action_av_fast_rewind
        };
        //adding icons to the nav drawer
        ArrayList<NavMenuItem> navOptions = new ArrayList<NavMenuItem>();
        for (int i = 0; i < navItems.length && i < navIcons.length; i++) {
            navOptions.add(new NavMenuItem(navIcons[i], navItems[i]));
        }
        //Set up drawer to have selections on item press
        navList.setAdapter(new NavMenuAdapter(this, R.layout.nav_list_item, navOptions));
        navList.setOnItemClickListener(new NavItemClickListener(this));
        //Set drawer to open and close
        navToggle = new NavToggle(this, navDrawer, R.string.nav_open, R.string.nav_close);
        navDrawer.setDrawerListener(navToggle);
        //Change the following line to alter the starting fragment (potentially store the last location)
        ViewNotesFragment fragment = new ViewNotesFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction
                .replace(R.id.content, fragment, "viewNotesFragment")
                .addToBackStack(null)
                .commit();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        navToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
                return true;
            case R.id.action_quick_note:
                switchView(GraphHome.newInstance(), "Graphs Home");
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupViewPager(ViewPager viewPager) {
        FragmentAdapter adapter = new FragmentAdapter(fragmentManager);
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        navToggle.onConfigurationChanged(newConfig);
    }

    public void logoutPressed(){
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.logout_dialog);
        // Custom Android Alert Dialog Title
        dialog.setTitle("Logout?");
        Button dialogButtonCancel = (Button) dialog.findViewById(R.id.customDialogCancel);
        Button dialogButtonOk = (Button) dialog.findViewById(R.id.customDialogOk);
        // Click cancel to dismiss android custom dialog box
        dialogButtonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        // Your android custom dialog ok action
        // Action for custom dialog ok button click
        dialogButtonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(dialog.getContext(), StartActivity.class));
                finish();
            }
        });
        dialog.show();
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
        if(navDrawer.isDrawerOpen(GravityCompat.START)) {
            navDrawer.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }
}
