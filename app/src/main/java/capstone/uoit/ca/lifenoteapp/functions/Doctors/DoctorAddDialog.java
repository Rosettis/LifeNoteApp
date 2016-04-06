package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Matthew on 4/5/2016.
 */
public class DoctorAddDialog extends DialogFragment {
    private EditText docName;
    private EditText docPhone;
    private EditText docAddress;
    private EditText docEmail;
    private EditText docTitle;
    Button btnAddDoctor;
    Button btnCancel;

    public DoctorAddDialog() {
        // Empty constructor required for DialogFragment
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(this.getContext());

        docName = (EditText) dialog.findViewById(R.id.addDoctorName);
        docPhone = (EditText) dialog.findViewById(R.id.addDoctorPhone);
        docAddress = (EditText) dialog.findViewById(R.id.addDoctorAddress);
        docEmail = (EditText) dialog.findViewById(R.id.adddDoctorEmail);
        docTitle = (EditText) dialog.findViewById(R.id.addDoctorTitle);
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.fragment_add_doctor,null))
                //add action buttons
                .setPositiveButton(R.string.addDoctor, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // Return input text to activity
                        DoctorAddDialogListener activity = (DoctorAddDialogListener) getActivity();
                        activity.onFinishAddDialog(docName.getText().toString(), docPhone.getText().toString(),
                                docAddress.getText().toString(), docEmail.getText().toString(), docTitle.getText().toString());

                        /*DoctorsFragment.store(docName.getText().toString(), docPhone.getText().toString(),
                                docAddress.getText().toString(), docEmail.getText().toString(), docTitle.getText().toString());
                        dismiss();*/
                        //Code to return it and restart the previous fragment (not advised)
                        /* Bundle bundle = new Bundle();
                        bundle.putString("msg_add_doctor", docName.getText().toString()+"*"+docPhone.getText().toString()
                                +"*"+docAddress.getText().toString()+"*"+ docEmail.getText().toString()
                                +"*"+ docTitle.getText().toString());
                        //set DoctorsFragment Arguments
                        DoctorsFragment fragObj = new DoctorsFragment();
                        fragObj.setArguments(bundle);*/
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        DoctorAddDialog.this.getDialog().cancel();
                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

   /* @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_add_doctor, container);

        docName = (EditText) view.findViewById(R.id.addDoctorName);
        docPhone = (EditText) view.findViewById(R.id.addDoctorPhone);
        docAddress = (EditText) view.findViewById(R.id.addDoctorAddress);
        docEmail = (EditText) view.findViewById(R.id.adddDoctorEmail);
        docTitle = (EditText) view.findViewById(R.id.addDoctorTitle);

        btnAddDoctor = (Button) view.findViewById(R.id.allow);
        btnAddDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return input text to activity
                DoctorAddDialogListener activity = (DoctorAddDialogListener) getActivity();
                activity.onFinishAddDialog(docName.getText().toString(),docPhone.getText().toString(),
                        docAddress.getText().toString(),docEmail.getText().toString(),docTitle.getText().toString());
                dismiss();
            }
        });

        btnCancel = (Button) view.findViewById(R.id.deny);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getDialog().setTitle("Add Doctor");
        // Show soft keyboard automatically
        docName.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);


        //docName.setOnEditorActionListener(this);

        return view;
    }

    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        if (EditorInfo.IME_ACTION_DONE == actionId) {
            // Return input text to activity
            DoctorAddDialogListener activity = (DoctorAddDialogListener) getActivity();
            activity.onFinishAddDialog(docName.getText().toString(),docPhone.getText().toString(),
                    docAddress.getText().toString(),docEmail.getText().toString(),docTitle.getText().toString());
            this.dismiss();
            return true;
        }
        return false;
    }*/
}
