package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 04/03/16.
 */
public class DocNameField extends LinearLayout {
    public DocNameField(Context context) {
        super(context);
        initializeViews(context);
    }

    public DocNameField(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public DocNameField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        Log.i("bamalamb", "initializeViews: WOW");
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_doctor_name_field, this);

        AutoCompleteTextView docName = (AutoCompleteTextView) this.findViewById(R.id.autocomplete_doctor_two);
        String[] doctors = getResources().getStringArray(R.array.doctors_array);
        ArrayAdapter<String> adapter =
                new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, doctors);
        docName.setAdapter(adapter);
    }
}
