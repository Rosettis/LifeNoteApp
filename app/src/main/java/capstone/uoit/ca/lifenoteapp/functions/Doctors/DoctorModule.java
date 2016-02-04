package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.support.v4.app.Fragment;

import java.io.Serializable;

/**
 * Created by Matthew on 2/3/2016.
 */
public abstract class DoctorModule implements Serializable {
    public abstract Fragment getFragment(String mode);
    public abstract void getData(Fragment frag);
}

