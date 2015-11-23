package capstone.uoit.ca.mobileapp.navbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
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
    private AppCompatActivity activity;

    private int count = 0;
    public NavItemClickListener(AppCompatActivity activity) {
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

                /*System.out.println("Notes");
                NotesFragment notesFragment = new NotesFragment();

                FragmentTransaction notesTransaction = activity.getSupportFragmentManager().beginTransaction();
                notesTransaction.replace(R.id.content, notesFragment);
                notesTransaction.addToBackStack(null);

                notesTransaction.commit();*/
                break;
            case 1:
                switchView(new DoctorsFragment(), "Doctors");

                /*System.out.println("Doctors");
                DoctorsFragment doctorsFragment = new DoctorsFragment();

                FragmentTransaction doctorsTransaction = activity.getSupportFragmentManager().beginTransaction();
                doctorsTransaction.replace(R.id.content, doctorsFragment);
                doctorsTransaction.addToBackStack(null);

                doctorsTransaction.commit();*/
                break;
            case 2:
                switchView(new AppointmentsFragment(), "Appointments");

                //System.out.println("Appointments");

                /*AppointmentsFragment doctorsFragment = new AppointmentsFragment();
                AppointmentsFragment appointmentsFragment = new AppointmentsFragment();

                FragmentTransaction appointmentsTransaction = activity.getSupportFragmentManager().beginTransaction();
                appointmentsTransaction.replace(R.id.content, appointmentsFragment);
                appointmentsTransaction.addToBackStack(null);

                appointmentsTransaction.commit();*/
                break;
            case 3:
                switchView(new VisitLogsFragment(), "Visit Logs");
                break;
            case 4:
                switchView(new TestResultsFragment(), "Test Results");

                /*System.out.println("Test Results");
                NotesFragment resultsFragment = new NotesFragment();

                FragmentTransaction resultsTransaction = activity.getSupportFragmentManager().beginTransaction();
                resultsTransaction.replace(R.id.content, resultsFragment);
                resultsTransaction.addToBackStack(null);

                resultsTransaction.commit();*/
                break;
            case 5:
                System.out.println("Profile to go here");
                switchView(new ProfileFragment(), "Profile");
                break;
            case 6:
                switchView(new SettingsFragment(), "Settings");

                /*System.out.println("Settings");
                SettingsFragment settingsFragment = new SettingsFragment();

                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, settingsFragment);
                transaction.addToBackStack(null);

                transaction.commit();*/
                break;
            case 7:
                System.out.println("Logout");

                if(count == 0){
                    Toast.makeText(activity.getApplicationContext(), "Press again to logout", Toast.LENGTH_SHORT).show();
                    count++;
                }else if(count >= 1){
                    count = 0;
//                    MainActivity.logoutPressed();
                    //TODO: Implement a change back to the Start Menu with no ability to get back into the main unless another login is done
                }
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
