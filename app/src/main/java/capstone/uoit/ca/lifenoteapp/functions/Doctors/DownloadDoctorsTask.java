package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.os.AsyncTask;
import android.util.Log;

import java.util.ArrayList;

/**
 * LifeNoteApp
 * Created by 100490515 on 11/26/2015.
 */

public class DownloadDoctorsTask extends AsyncTask<String, String, ArrayList<String>> {
    private DoctorDataListener listener = null;
    private Exception exception = null;


    @Override
    protected ArrayList<String> doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(ArrayList<String> s){
        if(exception != null){
            exception.printStackTrace();
            return;
        }
        Log.d("Doctors Updated", "Doctors should be there" + s);
        listener.showDoctors(s);
    }
}
