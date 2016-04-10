package capstone.uoit.ca.lifenoteapp.functions.Medication;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.DividerItemDecoration;

/**
 * Created by Peter on 10/04/16.
 */

public class NewMedicationFragment extends Fragment implements MedicationItemAddapter.MedicationViewHolder.OnMedicationSelectedListener{
    private RecyclerView rv;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_view_notes, container, false);

        rv = (RecyclerView)rootView.findViewById(R.id.RV_notes_list);
        rv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST));
        rv.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(this.getContext());
        rv.setLayoutManager(llm);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab_createNewNote);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction transaction = fragmentManager.beginTransaction();
                transaction
                        .replace(R.id.content, new NewAddMedication(), "AddMedication")
                        .addToBackStack(null)
                        .commit();
            }
        });

        NewMedicationDBHelper dbHelper = NewMedicationDBHelper.getInstance(this.getContext());
        ArrayList<Medication> medications = dbHelper.getAllMedications();
        ArrayList<Medication> reversed = new ArrayList<>();


        MedicationItemAddapter adapter = new MedicationItemAddapter(reversed, this);
        rv.setAdapter(adapter);

        if(medications.size() == 0) {
            rv.setVisibility(View.GONE);
            TextView emptyView = (TextView) rootView.findViewById(R.id.empty_view);
            emptyView.setVisibility(View.VISIBLE);
        }
        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        NewMedicationDBHelper dbHelper = NewMedicationDBHelper.getInstance(this.getContext());
        ArrayList<Medication> medications = dbHelper.getAllMedications();
        MedicationItemAddapter adapter = new MedicationItemAddapter(medications, this);
        rv.setAdapter(adapter);
    }

    @Override
    public void displayMedication(Medication medication) {
        FragmentManager fragmentManager = getFragmentManager();
        NewAddMedication appontmentFrag = NewAddMedication.newInstance(medication.getId());
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction
                .replace(R.id.content, appontmentFrag)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void deleteMedication(Medication medication) {
        System.out.println("DELETING APPOINTMENT");
        DialogInterface.OnClickListener dialogClickListener = new OnConfirmListener(medication, this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage("Are you sure you wish to delete this note?").setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();
    }

    public class OnConfirmListener implements DialogInterface.OnClickListener {
        Medication medication;
        MedicationItemAddapter.MedicationViewHolder.OnMedicationSelectedListener lsnr;

        public OnConfirmListener(Medication medication, MedicationItemAddapter.MedicationViewHolder.OnMedicationSelectedListener lsnr) {
            this.medication = medication;
            this.lsnr = lsnr;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    NewMedicationDBHelper dbHelper = NewMedicationDBHelper.getInstance(getContext());
                    dbHelper.deleteMedication(medication.getId());
                    ArrayList<Medication> medications = dbHelper.getAllMedications();
                    MedicationItemAddapter adapter = new MedicationItemAddapter(medications, lsnr);
                    rv.setAdapter(adapter);
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    }
}
