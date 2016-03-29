package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.View;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteLayout;

/**
 * Created by Peter on 03/03/16.
 */
public class HeaderModuleView extends LinearLayout {
    TextView titleEditText;
    TextView dateEditText;
    TextView timeEditText;
    FragmentActivity context;
    String noteName = "";
    String noteDate = "";
    String noteTime = "";
    NoteLayout currentLayout;

    public HeaderModuleView(Context context, String noteName, NoteLayout currentLayout, String date, String time) {
        super(context);
        this.noteName = noteName;
        this.noteDate = date;
        this.noteTime = time;
        this.currentLayout = currentLayout;
        this.context = (FragmentActivity) context;
        initializeViews(context);
    }

    public HeaderModuleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = (FragmentActivity) context;
        initializeViews(context);
    }

    public HeaderModuleView(Context context,
                        AttributeSet attrs,
                        int defStyle) {
        super(context, attrs, defStyle);
        this.context = (FragmentActivity) context;
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_header_module_view, this);

        dateEditText = (TextView) this.findViewById(R.id.editText_enterNoteDate_view);
        dateEditText.setTextColor(Color.parseColor("#808080"));
        String displayText = "Created: " + noteDate + ", " + noteTime;
        dateEditText.setText(displayText);

        TextView noteTypeTextView = (TextView) this.findViewById(R.id.TextView_noteType);
        noteTypeTextView.setText(currentLayout.getLayoutName());

        titleEditText = (TextView) this.findViewById(R.id.editText_enterNoteTitle_view);
        titleEditText.setText(noteName.substring(0, 1).toUpperCase() + noteName.substring(1));
    }

    public String getTitle() {
        return titleEditText.getText().toString();
    }

    public String getDate() {
        return dateEditText.getText().toString();
    }

    public String getTime() {
        return timeEditText.getText().toString();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

}