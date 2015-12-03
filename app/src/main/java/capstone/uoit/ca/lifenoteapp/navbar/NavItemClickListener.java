package capstone.uoit.ca.lifenoteapp.navbar;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import capstone.uoit.ca.lifenoteapp.MainActivity;
import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Appointments.AppointmentsFragment;
import capstone.uoit.ca.lifenoteapp.functions.Doctors.DoctorsFragment;
import capstone.uoit.ca.lifenoteapp.functions.FindHelp.FindHelpFragment;
import capstone.uoit.ca.lifenoteapp.functions.FindHelp.MapPane;
//import capstone.uoit.ca.lifenoteapp.functions.Notes.NotesFragment;
import capstone.uoit.ca.lifenoteapp.functions.Notes.ShowNotes;
import capstone.uoit.ca.lifenoteapp.functions.Profile.ProfileFragment;
import capstone.uoit.ca.lifenoteapp.functions.TestResults.TestResultsFragment;
import capstone.uoit.ca.lifenoteapp.functions.VisitLogs.VisitLogsFragment;
import capstone.uoit.ca.lifenoteapp.settings.SettingsFragment;

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
//                switchView(new NotesFragment(), "Notes");
                Intent showNotesActivityIntent = new Intent (activity, ShowNotes.class);
                activity.startActivity(showNotesActivityIntent);
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
                switchView(new FindHelpFragment(), "Find Help");
                break;
            case 6:
                System.out.println("Profile to go here");
                switchView(new ProfileFragment(), "Profile");
                break;
            case 7:
                switchView(new SettingsFragment(), "Settings");
                break;
            case 8:
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
