package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * LifeNoteApp
 * Created by 100490515 on 11/26/2015.
 */

public class DoctorAdapter extends BaseAdapter{
    private Context context;
    private ArrayList<Doctor> data;

    public DoctorAdapter(Context context, ArrayList<Doctor> data) {
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

        Log.d("DoctorAdapter", "Doctor:");
        Log.d("DoctorAdapter", "  Name:   "+doctorToDisplay.getName());
        Log.d("DoctorAdapter", "  Phone:  "+doctorToDisplay.getPhone());
        Log.d("DoctorAdapter", "  Address: "+doctorToDisplay.getAddress());
        if (convertView == null) {
            // create the layout
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_doctor_item, parent, false);
        }

        // populate the views with the data from story
        TextView lblTitle = (TextView)convertView.findViewById(R.id.doctorName);
        lblTitle.setText(doctorToDisplay.getName());

        TextView lblAuthor = (TextView)convertView.findViewById(R.id.doctorPhone);
        lblAuthor.setText(doctorToDisplay.getPhone());

        TextView lblContent = (TextView)convertView.findViewById(R.id.doctorAddress);
        lblContent.setText(doctorToDisplay.getAddress());

        return convertView;
    }
}
