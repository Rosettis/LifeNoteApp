package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 05/03/16.
 */
public class SeverityField extends LinearLayout {
    private SeekBar seekBar;
    private TextView textView;
    private TextView descriptionTextView;
    private String description;
    private int severity;
    private boolean editMode = false;


    public SeverityField(Context context, String descriptiontext) {
        super(context);
        this.description = descriptiontext;
        initializeViews(context);
    }

    public SeverityField(Context context, String descriptiontext, int severity) {
        super(context);
        this.description = descriptiontext;
        this.severity = severity;
        this.editMode = true;
        initializeViews(context);
    }

    public SeverityField(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);

    }

    public SeverityField(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_severity_field, this);


        seekBar = (SeekBar) findViewById(R.id.severity_seekBar);
        textView = (TextView) findViewById(R.id.count);
        textView.setText(Integer.toString(severity));
        descriptionTextView = (TextView) findViewById(R.id.textView_enterSeverity);
        descriptionTextView.setText(description);

        if (editMode) seekBar.setProgress(severity);


        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int seekBarProgress = 0;
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekBarProgress = progress;
//                Toast.makeText(getContext(),"SeekBar Progress Change",Toast.LENGTH_SHORT).show();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
//                Toast.makeText(getContext(),"SeekBar Touch Started",Toast.LENGTH_SHORT).show();
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
                textView.setText(Integer.toString(seekBarProgress));
            }

        });
    }
}
