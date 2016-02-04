package capstone.uoit.ca.lifenoteapp.functions.Doctors;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

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
    private static DoctorDBHelper ourInstance;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_FILENAME = "Doctors.db";
    public static final String TABLE_NAME = "Doctors";

    public static DoctorDBHelper getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new DoctorDBHelper(context.getApplicationContext());
        }
        return ourInstance;
    }

    private DoctorDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    // don't forget to use the column name '_id' for your primary key
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            "  _id integer primary key autoincrement, " +
            "  Doctor blob not null" +
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

    public Doctor createDoctor(Doctor Doctor) {
        // create the object
//        Doctor Doctor = new Doctor(modules);

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // insert the data into the database
        ContentValues values = new ContentValues();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] bytes;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(Doctor);
            bytes = bos.toByteArray();
        }catch (IOException ioe) {
            bytes = null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        values.put("Doctor", bytes);
        long id = database.insert(TABLE_NAME, null, values);

        // assign the Id of the new database row as the Id of the object
        Doctor.setId(id);
        return Doctor;
    }

    public Doctor getDoctor(long id) {
        Doctor Doctor = null;

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the Doctor from the database
        String[] columns = new String[] { "Doctor" };
        Cursor cursor = database.query(TABLE_NAME, columns, "_id = ?", new String[]{"" + id}, "", "", "");
        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            byte[] bytes = cursor.getBlob(0);
            ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
            ObjectInput in = null;
            try {
                in = new ObjectInputStream(bis);
                Doctor = new Doctor((String)in.readObject());
                Doctor.setId(id);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

        }

        Log.i("DatabaseAccess", "getDoctor(" + id + "):  Doctor: " + Doctor);

        return Doctor;
    }

    public ArrayList<Doctor> getAllDoctors() {
        ArrayList<Doctor> Doctors = new ArrayList<Doctor>();

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the Doctor from the database
        String[] columns = new String[] { "_id", "Doctor" };
        Cursor cursor = database.query(TABLE_NAME, columns, "", new String[]{}, "", "", "");
        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            do {
                // collect the Doctor data, and place it into a Doctor object
                long id = Long.parseLong(cursor.getString(0));
                byte[] bytes = cursor.getBlob(1);
                ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
                ObjectInput in = null;
                try {
                    in = new ObjectInputStream(bis);
                    Doctor doctor = new Doctor((String)in.readObject());
                    doctor.setId(id);

                    // add the current Doctor to the list
                    Doctors.add(doctor);

                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
                // advance to the next row in the results
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        Log.i("DatabaseAccess", "getAllDoctors():  num: " + Doctors.size());

        return Doctors;
    }

    public boolean updateDoctor(Doctor Doctor) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // update the data in the database
        ContentValues values = new ContentValues();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] bytes;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(Doctor);
            bytes = bos.toByteArray();
        }catch (IOException ioe) {
            bytes = null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                bos.close();
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        values.put("Doctor", bytes);
        int numRowsAffected = database.update(TABLE_NAME, values, "_id = ?", new String[] { "" + Doctor.getId() });

        Log.i("DatabaseAccess", "updateDoctor(" + Doctor + "):  numRowsAffected: " + numRowsAffected);

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
