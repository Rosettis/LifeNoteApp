package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 24/03/16.
 */
public class IllnessModuleView extends LinearLayout {
    private String symptoms;
    private int severity;

    public IllnessModuleView(Context context, String illness, String symptoms, int severity) {
        super(context);
        this.symptoms = symptoms;
        this.severity = severity;
        initializeViews(context);
    }

    public IllnessModuleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public IllnessModuleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_illness_module_view, this);


    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
