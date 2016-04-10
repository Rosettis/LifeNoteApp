package capstone.uoit.ca.lifenoteapp.functions.Medication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.selectors.DatePickerFragment;
import capstone.uoit.ca.lifenoteapp.selectors.TimePickerFragment;

/**
 * Created by Peter on 09/04/16.
 */
public class NewAddMedication extends Fragment {
    private EditText nameEditText;
    private EditText oftenEditText;
    private EditText reasonEditText;
    private EditText startEditText;
    private EditText dosageEditText;
    private EditText repeatsEditText;
    private boolean editMode = false;

    public static NewAddMedication newInstance(long medicationId) {
        NewAddMedication instance = new NewAddMedication();
        Bundle args = new Bundle();
        args.putBoolean("editMode", true);
        args.putLong("medicationId", medicationId);
        instance.setArguments(args);
        return instance;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Bundle args = getArguments();
        try {
            editMode = args.getBoolean("editMode");
        } catch (NullPointerException noParam) {
            //no parameter passed default to create mode
            noParam.printStackTrace();
        }

        View rootView;

        if (!editMode) {
            rootView = inflater.inflate(R.layout.fragment_new_add_medication, container, false);

            NewMedicationDBHelper dbHelper = NewMedicationDBHelper.getInstance(getContext());
            ArrayList<Medication> list = dbHelper.getAllMedications();

            System.out.println("Medication SIZE: " + list.size());

            for (Medication medication : list) {
                medication.printMedication();
            }
            nameEditText = (EditText) rootView.findViewById(R.id.editText_enterMedicationName);
            oftenEditText = (EditText) rootView.findViewById(R.id.editText_enterMedicationOften);
            reasonEditText = (EditText) rootView.findViewById(R.id.editText_enterMedicationReason);
            startEditText = (EditText) rootView.findViewById(R.id.editText_enterMedicationStart);
            dosageEditText = (EditText) rootView.findViewById(R.id.editText_enterMedicationDosage);
            repeatsEditText = (EditText) rootView.findViewById(R.id.editText_enterMedicationRepeats);

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);
            Date date = new Date(year, month, day);
            startEditText.setText(formatDate(date, year));
            startEditText.setFocusable(false);

            startEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View v, boolean hasFocus) {
                    if (hasFocus) {
                        DatePickerFragment dpf = new DatePickerFragment();
                        dpf.setCallBack(onDateSetLsnr);
                        FragmentActivity fragmentActivity = (FragmentActivity) getContext();
                        dpf.show(fragmentActivity.getSupportFragmentManager().beginTransaction(), "DatePickerFragment");
                    }
                }
            });

        } else {
            rootView = inflater.inflate(R.layout.fragment_view_medication, container, false);
            NewMedicationDBHelper dbHelper = NewMedicationDBHelper.getInstance(getContext());
            Medication medication = dbHelper.getMedication(args.getLong("medicationId"));

            nameEditText = (EditText) rootView.findViewById(R.id.editText_enterMedicationTitle);
            nameEditText.setText(medication.getName());

            oftenEditText = (EditText) rootView.findViewById(R.id.editText_enterMedicationOften);
            oftenEditText.setText(medication.getOften());

            reasonEditText = (EditText) rootView.findViewById(R.id.editText_enterMedicationReason);
            reasonEditText.setText(medication.getReason());

            startEditText = (EditText) rootView.findViewById(R.id.editText_enterMedicationStart);
            startEditText.setText(medication.getStart());

            dosageEditText = (EditText) rootView.findViewById(R.id.editText_enterMedicationDosage);
            dosageEditText.setText(medication.getDosage());

            repeatsEditText = (EditText) rootView.findViewById(R.id.editText_enterMedicationRepeats);
            repeatsEditText.setText(medication.getRepeats());

            OnDeleteListener deleteListener = new OnDeleteListener(medication);
            Button deleteBtn = (Button) rootView.findViewById(R.id.btn_deleteNote);
            deleteBtn.setVisibility(View.VISIBLE);
            deleteBtn.setOnClickListener(deleteListener);

        }


        Button saveBtn = (Button) rootView.findViewById(R.id.btn_saveCreateNoteTwo);
        Button cancelBtn = (Button) rootView.findViewById(R.id.btn_cancelCreateNoteTwo);
        saveBtn.setOnClickListener(btnLsnr);
        cancelBtn.setOnClickListener(btnLsnr);


        return rootView;
    }

    DatePickerDialog.OnDateSetListener onDateSetLsnr = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Date date = new Date(year, month, day);
            startEditText.setText(formatDate(date, year));
            startEditText.setTextColor(Color.BLACK);
        }
    };

    public String formatDate(Date date, int year) {
        String dateAsString = date.toString();
        String[] dateParts = dateAsString.split(" ");
        return dateParts[0] + " " + dateParts[1] + " " + dateParts[2] + " " + year;
    }

    private View.OnClickListener btnLsnr = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_cancelCreateNoteTwo:
                    getFragmentManager().popBackStack();
                    break;
                case R.id.btn_saveCreateNoteTwo:
                    NewMedicationDBHelper dbHelper = NewMedicationDBHelper.getInstance(getContext());
                    dbHelper.createMedication(new Medication(
                            nameEditText.getText().toString(),
                            oftenEditText.getText().toString(),
                            reasonEditText.getText().toString(),
                            startEditText.getText().toString(),
                            dosageEditText.getText().toString(),
                            repeatsEditText.getText().toString()
                    ));
                    getFragmentManager().popBackStack();
                    break;
            }
        }
    };

    private class OnDeleteListener implements View.OnClickListener {
        Medication medication;

        public OnDeleteListener(Medication medication) {
            this.medication = medication;
        }

        @Override
        public void onClick(View v) {
            DialogInterface.OnClickListener dialogClickListener = new OnDeleteConfirmListener(medication);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setMessage("Are you sure you wish to delete this medication?").setPositiveButton("Yes", dialogClickListener)
                    .setNegativeButton("No", dialogClickListener).show();
        }
    }

    public class OnDeleteConfirmListener implements DialogInterface.OnClickListener {
        Medication medication;

        public OnDeleteConfirmListener(Medication medication) {
            this.medication = medication;
        }

        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which){
                case DialogInterface.BUTTON_POSITIVE:
                    NewMedicationDBHelper dbHelper = NewMedicationDBHelper.getInstance(getContext());
                    dbHelper.deleteMedication(medication.getId());
                    getFragmentManager().popBackStack();
                    break;

                case DialogInterface.BUTTON_NEGATIVE:
                    //No button clicked
                    break;
            }
        }
    }


}

