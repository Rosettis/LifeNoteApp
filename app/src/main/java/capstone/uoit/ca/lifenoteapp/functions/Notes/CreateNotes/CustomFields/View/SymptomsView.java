package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.View;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 24/03/16.
 */
public class SymptomsView extends LinearLayout {
    private int severity;
    private SeekBar seekBar;

    public SymptomsView(Context context, int severity) {
        super(context);
        this.severity = severity;
        initializeViews(context);
    }

    public SymptomsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public SymptomsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    public int getSeverity() {
        return severity;
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_symptom_view, this);

        TextView severityTextView = (TextView) this.findViewById(R.id.TextView_severity);
        severityTextView.setText("Severity: " + severity);

        seekBar = (SeekBar) this.findViewById(R.id.severity_seekBar);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
