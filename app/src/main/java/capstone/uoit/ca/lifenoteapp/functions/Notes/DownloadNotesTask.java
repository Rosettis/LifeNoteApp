package capstone.uoit.ca.lifenoteapp.functions.Notes;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * LifeNoteApp
 * Created by 100490515 on 11/26/2015.
 */

public class DownloadNotesTask extends AsyncTask<InputStream, String, ArrayList<Note>> {
    private NoteListener listener = null;
    private Exception exception = null;

    public DownloadNotesTask(NoteListener listener) { this.listener = listener; }

    @Override
    protected ArrayList<Note> doInBackground(InputStream... params) {
        ArrayList<Note> page = new ArrayList<>();
        String line;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(params[0]));
            while((line = reader.readLine()) != null)
            {
                String[] temp = line.split("\\.");
                Log.d("temp values: ", temp[0]);
                Log.d("temp values: ",temp[1]);
                Log.d("temp values: ",temp[2]);
                page.add(new Note());
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
    protected void onPostExecute(ArrayList<Note> s){
        if(exception != null){
            exception.printStackTrace();
            return;
        }
        Log.d("Notes Updated", "Notes should display" + s);
        listener.showNotes(s);
    }
}
