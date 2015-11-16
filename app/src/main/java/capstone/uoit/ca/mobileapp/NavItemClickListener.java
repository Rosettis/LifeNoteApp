package capstone.uoit.ca.mobileapp;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import capstone.uoit.ca.mobileapp.R;
import capstone.uoit.ca.mobileapp.functions.Notes.NotesFragment;
import capstone.uoit.ca.mobileapp.settings.SettingsFragment;

/**
 * Created by nicholas on 30/10/15.
 */
public class NavItemClickListener implements ListView.OnItemClickListener {
    private AppCompatActivity activity;

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
                System.out.println("Testing Case 0");
                NotesFragment notesFragment = new NotesFragment();

                FragmentTransaction notesTransaction = activity.getSupportFragmentManager().beginTransaction();
                notesTransaction.replace(R.id.content, notesFragment);
                notesTransaction.addToBackStack(null);

                notesTransaction.commit();
                break;
            case 5: {
                SettingsFragment settingsFragment = new SettingsFragment();


                FragmentTransaction transaction = activity.getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content, settingsFragment);
                transaction.addToBackStack(null);

                transaction.commit();
                break;
            }
        }

    }
}
