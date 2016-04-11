package capstone.uoit.ca.lifenoteapp.functions.Doctors.AddDoctor;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

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
    DoctorAddDialogListener lsnr;

    public DoctorAddDialog() {
        // Empty constructor required for DialogFragment
    }

    public void setCallBack(DoctorAddDialogListener onRemove) {
        lsnr = onRemove;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final Dialog dialog = new Dialog(this.getContext());
        // Use the Builder class for convenient dialog construction
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_add_doctor, null);
        builder.setView(view);
        //Assigning EditText fields to variables
        docName = (EditText) view.findViewById(R.id.addDoctorName);
        docPhone = (EditText) view.findViewById(R.id.addDoctorPhone);
        docAddress = (EditText) view.findViewById(R.id.addDoctorAddress);
        docEmail = (EditText) view.findViewById(R.id.adddDoctorEmail);
        docTitle = (EditText) view.findViewById(R.id.addDoctorTitle);
            //add action buttons
            builder.setPositiveButton(R.string.addDoctor, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                // Return input text to activity
                lsnr.onFinishAddDialog(docName.getText().toString(), docPhone.getText().toString(),
                        docAddress.getText().toString(), docEmail.getText().toString(), docTitle.getText().toString());
                getDialog().dismiss();
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
}
