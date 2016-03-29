package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 04/03/16.
 */
public class AutoCompleteField extends LinearLayout {
    private String mode;
    private String text = "";
    private String label = "";
    public AutoCompleteField(Context context, String mode, String text) {
        super(context);
        this.text = text;
        this.mode = mode;
        initializeViews(context);
    }

    public AutoCompleteField(Context context, String mode, String text, String label) {
        super(context);
        this.text = text;
        this.mode = mode;
        this.label = label;
        initializeViews(context);
    }

    public AutoCompleteField(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public AutoCompleteField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        if (mode.equals("create")) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.custom_doctor_name_field, this);

            AutoCompleteTextView docName = (AutoCompleteTextView) this.findViewById(R.id.autocomplete_doctor_two);
            docName.setHint(text);
            String[] doctors = getResources().getStringArray(R.array.doctors_array);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, doctors);
            docName.setAdapter(adapter);
//        } else if (mode.equals("view")) {
//            LayoutInflater inflater = (LayoutInflater) context
//                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            inflater.inflate(R.layout.custom_doctor_name_field_view, this);
//            TextView docName = (TextView) this.findViewById(R.id.autocomplete_doctor_view);
//            docName.setText(text);
//            TextView labelTextView = (TextView) this.findViewById(R.id.TextView_doctorsName_label);
//            labelTextView.setText(label);
        } else if (mode.equals("edit")){
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            inflater.inflate(R.layout.custom_doctor_name_field_view, this);
            AutoCompleteTextView docName = (AutoCompleteTextView) this.findViewById(R.id.autocomplete_doctor_two);
            docName.setText(text);

            TextView labelTextView = (TextView) this.findViewById(R.id.TextView_doctorsName_label);
            labelTextView.setText(label);

            String[] doctors = getResources().getStringArray(R.array.doctors_array);
            ArrayAdapter<String> adapter =
                    new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, doctors);
            docName.setAdapter(adapter);
        } else {
            Log.e("AutoCompleteField", "initializeViews: invalid parameter:" + mode);
        }
    }
}
