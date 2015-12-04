package capstone.uoit.ca.lifenoteapp.functions.Appointments;

import android.content.Context;
import android.content.res.Resources;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * Created by Peter on 03/12/15.
 */
public class AppointmentAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Appointment> data;

    public AppointmentAdapter(Context context, ArrayList<Appointment> data) {
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
        int appointments = data.size();
        Appointment appointmentToDisplay = data.get(position);

        if (convertView == null) {
            // create the layout
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_view_appointment_item, parent, false);
        }

        // populate the views with the data from note
        TextView lblTitle = (TextView)convertView.findViewById(R.id.textView_appointmentClinicName);
        lblTitle.setText(appointmentToDisplay.getDoctorsName());

        TextView lblContent = (TextView)convertView.findViewById(R.id.textView_appointmentTime);
        lblContent.setText(appointmentToDisplay.getDate());

        TextView lblClinicName = (TextView)convertView.findViewById(R.id.textView_appointmentDoctorName);
        lblClinicName.setText(appointmentToDisplay.getClinicName());

        ImageView img = (ImageView)convertView.findViewById(R.id.clinic_image);
        if (position == 0 | position == 1) {
            img.setImageResource(R.drawable.clinic1);

        }
        return convertView;
    }
}
