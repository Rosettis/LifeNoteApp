package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.content.res.AssetFileDescriptor;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

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

public class DownloadFeedTask extends AsyncTask<InputStream, String, ArrayList<Doctor>> {
    private DoctorDataListener listener = null;
    private Exception exception = null;

    public DownloadFeedTask(DoctorDataListener listener) {
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
                Log.d("temp values: ",temp[3]);
                Log.d("temp values: ",temp[4]);
                String[] latLong = temp[4].split(",");
                Double latitude = Double.parseDouble(latLong[0]);
                Double longitude = Double.parseDouble(latLong[1]);
                LatLng location = new LatLng(latitude,longitude);
                page.add(new Doctor.Builder(temp[0],temp[1])
                        .email(temp[2]).address(temp[3]).location(location).build());
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
        Log.d("Check","~~~Done~~~");
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
