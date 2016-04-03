package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.CustomFields.Create;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.text.DecimalFormat;

import capstone.uoit.ca.lifenoteapp.R;
import capstone.uoit.ca.lifenoteapp.functions.Doctors.Doctor;

/**
 * Created by Peter on 03/04/16.
 */
public class WeightModule extends LinearLayout {
    private boolean editMode = false;
    private int weight;
    private int height;
    private EditText weightEditText;
    private EditText heightEditText;
    private TextView bmiTextView;

    public WeightModule(Context context) {
        super(context);
        initializeViews(context);
    }

    public WeightModule(Context context, int weight, int height) {
        super(context);
        this.editMode = true;
        this.weight = weight;
        this.height = height;
        initializeViews(context);
    }

    public WeightModule(Context context, AttributeSet attrs) {
        super(context, attrs);
        initializeViews(context);
    }

    public WeightModule(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initializeViews(context);
    }

    private void initializeViews(Context context) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.custom_weight_module, this);

        weightEditText = (EditText) this.findViewById(R.id.EditText_weight);
        heightEditText = (EditText) this.findViewById(R.id.EditText_height);
        bmiTextView = (TextView) this.findViewById(R.id.TextView_bmi);

        if (editMode) {
            weightEditText.setText(weight);
            heightEditText.setText(height);
        }

        weightEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    calculateBmi(weightEditText.getText().toString(), heightEditText.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        heightEditText.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() > 0) {
                    calculateBmi(weightEditText.getText().toString(), heightEditText.getText().toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void calculateBmi(String weight, String height) {
        System.out.println("Weight: " + weight + ", Height: " + height);
        if (weight.length() > 0 && height.length() > 0) {
            int h = Integer.parseInt(height);
            DecimalFormat df2 = new DecimalFormat("##.#");
            Double bmi = Double.valueOf(df2.format((Double.parseDouble(weight) / (h * h)) * 703));
            if(bmi < 100 && bmi > 0) bmiTextView.setText(Double.toString(bmi));
            else bmiTextView.setText(" ");
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }
}
