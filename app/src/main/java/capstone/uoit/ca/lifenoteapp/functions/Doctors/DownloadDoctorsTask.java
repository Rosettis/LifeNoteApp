package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * LifeNoteApp
 * Created by 100490515 on 11/26/2015.
 */

public class DownloadDoctorsTask extends AsyncTask<String, String, ArrayList<Doctor>> {
    private DoctorDataListener listener = null;
    private Exception exception = null;

    public DownloadDoctorsTask(DoctorDataListener listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Doctor> doInBackground(String... params) {
        ArrayList<Doctor> page = new ArrayList<>();
        String line;
        try {
            // parse out the data
            FileReader freader = new FileReader("doctors.txt");
            BufferedReader breader = new BufferedReader(freader);
            while((line = breader.readLine()) != null){
                String[] temp = line.split(".");
                page.add(new Doctor(temp[0],temp[1],temp[2]));
            }
            breader.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        return page;
    }

    @Override
    protected void onPostExecute(ArrayList<Doctor> s){
        if(exception != null){
            exception.printStackTrace();
            return;
        }
        Log.d("Doctors Updated", "Doctors should be there" + s);
        listener.showDoctors(s);
    }
}
