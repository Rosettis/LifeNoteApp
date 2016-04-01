package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.View;

import android.content.Context;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 01/04/16.
 */
public class SideLabel extends LinearLayout {
    private String text;

    public SideLabel(Context context, String text) {
        super(context);
        this.text = text;
        initializeViews(context);
    }

    public SideLabel(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public SideLabel(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_sidelabel_view, this);

        if (text.length() > 8) {
            ((TextView) this.findViewById(R.id.lable)).setTextSize(TypedValue.COMPLEX_UNIT_SP,8);
        }

        ((TextView) this.findViewById(R.id.lable)).setText(text);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
