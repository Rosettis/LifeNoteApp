package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 04/03/16.
 */
public class IllnessModule extends LinearLayout {
    public IllnessModule(Context context) {
        super(context);
        initializeViews(context);
    }

    public IllnessModule(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public IllnessModule(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_illness_module, this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
