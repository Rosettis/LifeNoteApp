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
        setContentView(R.layout.activity_add_doctor);

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
}
