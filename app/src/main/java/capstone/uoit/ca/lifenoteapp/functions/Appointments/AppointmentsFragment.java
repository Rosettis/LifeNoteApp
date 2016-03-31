package capstone.uoit.ca.lifenoteapp.functions.Appointments;

/**
 * Created by 100490515 on 11/16/2015.
 */

import android.app.Activity;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import capstone.uoit.ca.lifenoteapp.R;

/**
 * AppointmentsFragment
 *
 * @author Matthew Rosettis
 */
public class AppointmentsFragment extends Fragment{

    Button enter;
    View view;
    Button newAppointment, showAppointment;


    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        view =  inflater.inflate(R.layout.fragment_appointments, parent, false);

        final Bundle dataBundle = new Bundle();
        dataBundle.putInt("id", 0);


        newAppointment = (Button) view.findViewById(R.id.addAppt);
        newAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), AddAppointment.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });

        showAppointment = (Button) view.findViewById(R.id.showAppt);
        showAppointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity().getApplicationContext(), ShowAppointments.class);
                intent.putExtras(dataBundle);
                startActivity(intent);

            }
        });







//               enter = (Button)view.findViewById(R.id.appt_enter);
//
//        enter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(getActivity(), ShowAppointments.class );
//                startActivity(intent);
//                ((Activity) getActivity()).overridePendingTransition(0, 0);
//            }
//        });

        return view;
    }



}
