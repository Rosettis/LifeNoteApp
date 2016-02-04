package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.content.res.AssetFileDescriptor;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * LifeNoteApp
 * Created by 100490515 on 11/26/2015.
 */

public class DownloadDoctorsTask extends AsyncTask<InputStream, String, ArrayList<Doctor>> {
    private DoctorDataListener listener = null;
    private Exception exception = null;

    public DownloadDoctorsTask(DoctorDataListener listener) {
        this.listener = listener;
    }

    @Override
    protected ArrayList<Doctor> doInBackground(InputStream... params) {
        ArrayList<Doctor> page = new ArrayList<>();
        String line;
        BufferedReader reader = null;
        try {
            /*// parse out the data
            FileReader freader = new FileReader("doctors.txt");
            BufferedReader breader = new BufferedReader(freader);
            while((line = breader.readLine()) != null){
                String[] temp = line.split(".");
                page.add(new Doctor(temp[0],temp[1],temp[2]));
            }
            breader.close();*/
            reader = new BufferedReader(
                    new InputStreamReader(params[0]));
            while((line = reader.readLine()) != null)
            {
                String[] temp = line.split("\\*");
                Log.d("temp values: ",temp[0]);
                Log.d("temp values: ",temp[1]);
                Log.d("temp values: ",temp[2]);
                page.add(new Doctor(temp[0],temp[1],temp[2],temp[3]));
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    //log the exception
                }
            }
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
