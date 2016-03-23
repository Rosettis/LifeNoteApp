package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 05/03/16.
 */
public class AdditionalModule extends LinearLayout {
    public AdditionalModule(Context context) {
        super(context);
        initializeViews(context);
    }

    public AdditionalModule(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public AdditionalModule(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_additional_module, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
