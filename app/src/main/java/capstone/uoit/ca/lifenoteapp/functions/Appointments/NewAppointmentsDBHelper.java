package capstone.uoit.ca.lifenoteapp.functions.Appointments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.Note;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteLayoutDBHelper;

/**
 * Created by Peter on 09/04/16.
 */
public class NewAppointmentsDBHelper extends SQLiteOpenHelper {
    private static NewAppointmentsDBHelper ourInstance;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_FILENAME = "appointments.db";
    public static final String TABLE_NAME = "Appointments";
    Context context;

    public static NewAppointmentsDBHelper getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new NewAppointmentsDBHelper(context.getApplicationContext());
        }
        return ourInstance;
    }

    private NewAppointmentsDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // don't forget to use the column name '_id' for your primary key
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            "  _id integer primary key autoincrement, " +
            "  name text not null, " +
            "  date text not null, " +
            "  time text not null, " +
            "  clinic text not null, " +
            "  doc text " +
            ")";
    public static final String DROP_STATEMENT = "DROP TABLE " + TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_STATEMENT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // the implementation below is adequate for the first version
        // however, if we change our table at all, we'd need to execute code to move the data
        // to the new table structure, then delete the old tables (renaming the new ones)

        // the current version destroys all existing data
        database.execSQL(DROP_STATEMENT);
        database.execSQL(CREATE_STATEMENT);
    }

    public NewAppointment createAppointment(NewAppointment appointment) {
        // create the object
//        Note note = new Note(modules);

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put("name", appointment.getName());
        values.put("date", appointment.getDate());
        values.put("time", appointment.getTime());
        values.put("clinic", appointment.getClinic());
        values.put("doc", appointment.getDoc());

        long id = database.insert(TABLE_NAME, null, values);

        // assign the Id of the new database row as the Id of the object
        appointment.setId(id);
        return appointment;
    }

    public NewAppointment getAppointment(long id) {
        NewAppointment appointment = null;

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the note from the database
        String[] columns = new String[] {
                "name",
                "date",
                "time",
                "clinic",
                "doc"
        };
        Cursor cursor = database.query(TABLE_NAME, columns, "_id = ?", new String[]{"" + id}, "", "", "");
        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            appointment = new NewAppointment(
                    cursor.getString(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4)
                    );
            appointment.setId(id);
        }
        Log.i("DatabaseAccess", "getNote(" + id + "):  note: " + appointment);
        return appointment;
    }

    public ArrayList<NewAppointment> getAllAppointments() {
        ArrayList<NewAppointment> appointments = new ArrayList<NewAppointment>();

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the note from the database
        String[] columns = new String[] { "_id",
                "name",
                "date",
                "time",
                "clinic",
                "doc"
        };
        try {
            Cursor cursor = database.query(TABLE_NAME, columns, "", new String[]{}, "", "", "");
            if (cursor.getCount() >= 1) {
                cursor.moveToFirst();
                do {
                    // collect the note data, and place it into a note object
                    long id = Long.parseLong(cursor.getString(0));
                    NewAppointment appointment = new NewAppointment(
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5)
                    );
                    appointment.setId(id);

                    // add the current note to the list
                    appointments.add(appointment);

                    // advance to the next row in the results
                    cursor.moveToNext();
                } while (!cursor.isAfterLast());
            }
        } catch (SQLiteException databaseEmpty) {
            databaseEmpty.printStackTrace();
        }
        Log.i("DatabaseAccess", "getAllNotes():  num: " + appointments.size());
        return appointments;
    }

    public boolean updateNote(NewAppointment appointment) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // update the data in the database
        ContentValues values = new ContentValues();
        values.put("name", appointment.getName());
        values.put("date", appointment.getDate());
        values.put("date", appointment.getTime());
        values.put("time", appointment.getTime());
        values.put("clinic", appointment.getClinic());
        values.put("doc", appointment.getDoc());

        int numRowsAffected = database.update(TABLE_NAME, values, "_id = ?", new String[] { "" + appointment.getId() });

        Log.i("DatabaseAccess", "updateNote(" + appointment + "):  numRowsAffected: " + numRowsAffected);

        // verify that the note was updated successfully
        return (numRowsAffected == 1);
    }

    public boolean deleteAppointment(long id) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // delete the note
        int numRowsAffected = database.delete(TABLE_NAME, "_id = ?", new String[]{"" + id});

        Log.i("DatabaseAccess", "deleteNote(" + id + "):  numRowsAffected: " + numRowsAffected);

        // verify that the note was deleted successfully
        return (numRowsAffected == 1);
    }

    public void deleteAllAppointments() {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // delete the note
        int numRowsAffected = database.delete(TABLE_NAME, "", new String[] {});

        Log.i("DatabaseAccess", "deleteAllNotes():  numRowsAffected: " + numRowsAffected);
    }
}
