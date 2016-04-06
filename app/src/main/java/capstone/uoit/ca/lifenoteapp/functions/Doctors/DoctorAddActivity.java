package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
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
 * Created by Matthew on 4/6/2016.
 */
public class DoctorAddActivity extends FragmentActivity {
    private EditText docName;
    private EditText docPhone;
    private EditText docAddress;
    private EditText docEmail;
    private EditText docTitle;
    Button btnAddDoctor;
    Button btnCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_add_doctor);

        docName = (EditText) findViewById(R.id.addDoctorName);
        docPhone = (EditText) findViewById(R.id.addDoctorPhone);
        docAddress = (EditText) findViewById(R.id.addDoctorAddress);
        docEmail = (EditText) findViewById(R.id.adddDoctorEmail);
        docTitle = (EditText) findViewById(R.id.addDoctorTitle);

        btnAddDoctor = (Button) findViewById(R.id.allow);
        btnAddDoctor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Return input text to activity
                Intent intent = new Intent(getApplicationContext(),DoctorsFragment.class);
//                if(docName.getText().toString() != null){
                intent.putExtra("name",docName.getText().toString());
                intent.putExtra("phone",docPhone.getText().toString());
                intent.putExtra("address",docAddress.getText().toString());
                intent.putExtra("email", docEmail.getText().toString());
                intent.putExtra("title", docTitle.getText().toString());
                setResult(1, intent);
                finish();
            }
        });

        btnCancel = (Button) findViewById(R.id.deny);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent(getApplicationContext(),DoctorsFragment.class);
                setResult(2);*/
                finish();
            }
        });
    }

    /*@Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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
                Intent intent = new Intent(getApplicationContext(),DoctorsFragment.class);
                intent.putExtra("name",docName.getText().toString());
                intent.putExtra("phone",docPhone.getText().toString());
                intent.putExtra("address",docAddress.getText().toString());
                intent.putExtra("email", docEmail.getText().toString());
                intent.putExtra("title", docTitle.getText().toString());
                setResult(1, intent);
                finish();
            }
        });

        btnCancel = (Button) view.findViewById(R.id.deny);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                *//*Intent intent = new Intent(getApplicationContext(),DoctorsFragment.class);
                setResult(2);*//*
                finish();
            }
        });

        return view;
    }*/

    /*public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
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
