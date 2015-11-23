package capstone.uoit.ca.mobileapp.navbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import capstone.uoit.ca.mobileapp.MainActivity;
import capstone.uoit.ca.mobileapp.R;
import capstone.uoit.ca.mobileapp.functions.Appointments.AppointmentsFragment;
import capstone.uoit.ca.mobileapp.functions.Doctors.DoctorsFragment;
import capstone.uoit.ca.mobileapp.functions.Notes.NotesFragment;
import capstone.uoit.ca.mobileapp.functions.Profile.ProfileFragment;
import capstone.uoit.ca.mobileapp.functions.TestResults.TestResultsFragment;
import capstone.uoit.ca.mobileapp.functions.VisitLogs.VisitLogsFragment;
import capstone.uoit.ca.mobileapp.settings.SettingsFragment;

public class NavItemClickListener implements ListView.OnItemClickListener {
    private MainActivity activity;
    public NavItemClickListener(MainActivity activity) {
        this.activity = activity;
    }

    @Override
    public void onItemClick(AdapterView parent, View view, int position, long id) {
        selectItem(position);
    }

    private void selectItem(int position) {
        System.out.println(position);
        switch (position) {
            case 0:
                switchView(new NotesFragment(), "Notes");
                break;
            case 1:
                switchView(new DoctorsFragment(), "Doctors");
                break;
            case 2:
                switchView(new AppointmentsFragment(), "Appointments");
                break;
            case 3:
                switchView(new VisitLogsFragment(), "Visit Logs");
                break;
            case 4:
                switchView(new TestResultsFragment(), "Test Results");
                break;
            case 5:
                System.out.println("Profile to go here");
                switchView(new ProfileFragment(), "Profile");
                break;
            case 6:
                switchView(new SettingsFragment(), "Settings");
                break;
            case 7:
                System.out.println("Logout");

                activity.logoutPressed();
                break;
        }
    }
    private void switchView(Fragment fragment, String fragTag) {
        System.out.println(fragTag);
        MainActivity.navDrawer.closeDrawers();
        FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
        transaction
                .replace(R.id.content, fragment)
                .addToBackStack(null)
                .commit();
    }
}
