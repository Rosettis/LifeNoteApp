package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 03/12/15.
 */
public class DoctorAdaptorDemo extends BaseAdapter {
    private Context context;
    private ArrayList<Doctor> data;

    public DoctorAdaptorDemo(Context context, ArrayList<Doctor> data) {
        this.data = data;
        this.context = context;
    }

    public int getCount() {
        return data.size();
    }

    public Object getItem(int position) {
        return data.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        Doctor doctorToDisplay = data.get(position);

        if (convertView == null) {
            // create the layout
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_doctor_item_demo, parent, false);
        }

        // populate the views with the data from doctor
        TextView lblTitle = (TextView)convertView.findViewById(R.id.lbl_docName);
        lblTitle.setText(doctorToDisplay.getName());

        TextView lblContent = (TextView)convertView.findViewById(R.id.lbl_docNum);
        lblContent.setText(doctorToDisplay.getPhone());

        TextView lblDescription = (TextView)convertView.findViewById(R.id.lbl_docAddress);
        lblDescription.setText(doctorToDisplay.getAddress());
        return convertView;
    }
}