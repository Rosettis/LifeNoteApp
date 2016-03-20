package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 04/03/16.
 */
public class DoctorModule extends LinearLayout {
    public DoctorModule(Context context) {
        super(context);
        initializeViews(context);
    }

    public DoctorModule(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public DoctorModule(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_doctor_module, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
