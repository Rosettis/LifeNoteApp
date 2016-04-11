package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


/**
 * Created by Matthew on 2/3/2016.
 */
public class DoctorDBHelper extends SQLiteOpenHelper{
    private static DoctorDBHelper ourInstance = null;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_FILENAME = "Doctors.db";
    public static final String TABLE_NAME = "Doctors";
    public static final String COLUMN_ID = "_id";


    // don't forget to use the column name '_id' for your primary key
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            "  _id integer primary key autoincrement, " +
            "  doctorName text not null," +
            "  doctorPhone text," +
            "  doctorAddress text," +
            "  doctorEmail text," +
            "  doctorTitle text," +
            "  doctorLocation text" +
            ")";
    public static final String DROP_STATEMENT = "DROP TABLE " + TABLE_NAME;

    public static DoctorDBHelper getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new DoctorDBHelper(context.getApplicationContext());
        }
        return ourInstance;
    }

    public DoctorDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

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

    public Doctor createDoctor(String doctorName, String doctorPhone, String doctorAddress, String doctorEmail, String doctorTitle, String doctorLocation) {
        Doctor doctor;
        if(doctorLocation != null) {
            // convert the latitude and longitude
            String[] locValues = doctorLocation.split(",");
            LatLng doctorLatLng = new LatLng(Double.parseDouble(locValues[0].substring(10)),
                    Double.parseDouble(locValues[1].substring(0, locValues[1].length() - 1)));
            //create the object
            doctor = new Doctor.Builder(doctorName,doctorPhone)
                    .address(doctorAddress).email(doctorEmail).title(doctorTitle).location(doctorLatLng).build();
        }else{
            //create the object
            doctor = new Doctor.Builder(doctorName,doctorPhone)
                    .address(doctorAddress).email(doctorEmail).title(doctorTitle).build();
        }

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // insert the data into the database
        ContentValues values = new ContentValues();
        values.put("doctorName", doctor.getName());
        values.put("doctorPhone", doctor.getPhone());
        values.put("doctorAddress", doctor.getAddress());
        values.put("doctorEmail", doctor.getEmail());
        values.put("doctorTitle",doctor.getTitle());
        values.put("doctorLocation", (doctor.getLocation()).toString());
        long id = database.insert(TABLE_NAME, null, values);

        // assign the Id of the new database row as the Id of the object
        doctor.setId(id);
        return doctor;
    }

    public Doctor createDoctor(Doctor doctor) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // insert the data into the database
        ContentValues values = new ContentValues();
        values.put("doctorName", doctor.getName());
        values.put("doctorPhone", doctor.getPhone());
        values.put("doctorAddress", doctor.getAddress());
        values.put("doctorEmail", doctor.getEmail());
        values.put("doctorTitle",doctor.getTitle());
        values.put("doctorLocation", (doctor.getLocation()).toString());
        long id = database.insert(TABLE_NAME, null, values);

        // assign the Id of the new database row as the Id of the object
        doctor.setId(id);
        return doctor;
    }

    public Doctor getDoctor(long id) {
        Doctor doctor = null;

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the doctor from the database
        String[] columns = new String[] { "_id", "doctorName", "doctorPhone", "doctorAddress", "doctorEmail", "doctorTitle", "doctorLocation" };
        Cursor cursor = database.query(TABLE_NAME, columns, "_id = ?", new String[] { "" + id }, "", "", "");
        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            String doctorName = cursor.getString(1);
            String doctorPhone = cursor.getString(2);
            String doctorAddress = cursor.getString(3);
            String doctorEmail = cursor.getString(4);
            String doctorTitle = cursor.getString(5);
            String doctorLocation = cursor.getString(6);
            String[] locValues = doctorLocation.split(",");
            LatLng doctorLatLng = new LatLng(Double.parseDouble(locValues[0].substring(10)),Double.parseDouble(locValues[1].substring(0,locValues[1].length()-1)));
            doctor = new Doctor.Builder(doctorName,doctorPhone)
                    .address(doctorAddress).email(doctorEmail).title(doctorTitle).location(doctorLatLng).build();
            doctor.setId(id);

        }

        Log.i("DatabaseAccess", "getDoctor(" + id + "):  Doctor: " + doctor);
        cursor.close();
        return doctor;
    }

    public ArrayList<Doctor> getAllDoctors() {
        ArrayList<Doctor> doctors = new ArrayList<Doctor>();

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the Doctor from the database
        String[] columns = new String[] { "_id", "doctorName", "doctorPhone", "doctorAddress", "doctorEmail", "doctorTitle", "doctorLocation" };

        try {
            Cursor cursor = database.query(TABLE_NAME, columns, "", new String[]{}, "", "", "");
//        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            do {
                // collect the Doctor data, and place it into a Doctor object
                long id = Long.parseLong(cursor.getString(0));
                String doctorName = cursor.getString(1);
                String doctorPhone = cursor.getString(2);
                String doctorAddress = cursor.getString(3);
                String doctorEmail = cursor.getString(4);
                String doctorTitle = cursor.getString(5);
                String doctorLocation = cursor.getString(6);
                String[] locValues = doctorLocation.split(",");
                LatLng doctorLatLng = new LatLng(Double.parseDouble(locValues[0].substring(10)),
                        Double.parseDouble(locValues[1].substring(0,locValues[1].length()-1)));
                Doctor doctor = new Doctor.Builder(doctorName,doctorPhone)
                        .address(doctorAddress).email(doctorEmail).title(doctorTitle).location(doctorLatLng).build();                doctor.setId(id);

                //add the current doctor to the list
                doctors.add(doctor);

                // advance to the next row in the results
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
//        }

        Log.i("DatabaseAccess", "getAllDoctors():  num: " + doctors.size());
        cursor.close();

        } catch (CursorIndexOutOfBoundsException databaseEmpty) {
            this.createDoctor("Dr. Khalid", "416-524-1230", "", "", "Doctor", null);
            //Adding Dr. Sperber
            this.createDoctor("Dr. Sperber", "905-272-8091", "", "", "Orthodontist", null);
            //Adding Dr. Kim
            this.createDoctor("Dr. Kim", "647-232-1884", "", "", "Dentist", null);
            //Adding Dr. Anderson
            this.createDoctor("Dr. Anderson", "416-023-0338", "", "", "Specialist", null);
        }
        return doctors;
    }

    public boolean updateDoctor(Doctor doctor) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // update the data in the database
        ContentValues values = new ContentValues();
        values.put("doctorName", doctor.getName());
        values.put("doctorPhone", doctor.getPhone());
        values.put("doctorAddress", doctor.getAddress());
        values.put("doctorEmail", doctor.getEmail());
        values.put("doctorTitle",doctor.getTitle());
        values.put("doctorLocation", (doctor.getLocation()).toString());
        int numRowsAffected = database.update(TABLE_NAME, values, "_id = ?", new String[] { "" + doctor.getId() });

        Log.i("DatabaseAccess", "updateDoctor(" + doctor + "):  numRowsAffected: " + numRowsAffected);

        // verify that the Doctor was updated successfully
        return (numRowsAffected == 1);
    }

    public boolean deleteDoctor(long id) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // delete the Doctor
        int numRowsAffected = database.delete(TABLE_NAME, "_id = ?", new String[]{"" + id});

        Log.i("DatabaseAccess", "deleteDoctor(" + id + "):  numRowsAffected: " + numRowsAffected);

        // verify that the Doctor was deleted successfully
        return (numRowsAffected == 1);
    }

    public void deleteAllDoctors() {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // delete the Doctor
        int numRowsAffected = database.delete(TABLE_NAME, "", new String[] {});

        Log.i("DatabaseAccess", "deleteAllDoctors():  numRowsAffected: " + numRowsAffected);
    }
}
