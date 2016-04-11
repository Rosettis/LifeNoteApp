package capstone.uoit.ca.lifenoteapp.functions.Medication;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;


/**
 * Created by Peter on 10/04/16.
 */
    public class NewMedicationDBHelper extends SQLiteOpenHelper {
        private static NewMedicationDBHelper ourInstance;
        public static final int DATABASE_VERSION = 1;
        public static final String DATABASE_FILENAME = "medicationstwo.db";
        public static final String TABLE_NAME = "medications";
        Context context;

        public static NewMedicationDBHelper getInstance(Context context) {
            if (ourInstance == null) {
                ourInstance = new NewMedicationDBHelper(context.getApplicationContext());
            }
            return ourInstance;
        }

        private NewMedicationDBHelper(Context context) {
            super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
            this.context = context;
        }

        // don't forget to use the column name '_id' for your primary key
        public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
                "  _id integer primary key autoincrement, " +
                "  name text not null, " +
                "  often text not null, " +
                "  reason text not null, " +
                "  start text not null, " +
                "  dosage text, " +
                "  repeats text " +
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

        public Medication createMedication(Medication medication) {
            // create the object
//        Note note = new Note(modules);

            // obtain a database connection
            SQLiteDatabase database = this.getWritableDatabase();

            ContentValues values = new ContentValues();

            values.put("name", medication.getName());
            values.put("often", medication.getOften());
            values.put("reason", medication.getReason());
            values.put("start", medication.getStart());
            values.put("dosage", medication.getDosage());
            values.put("repeats", medication.getRepeats());


            long id = database.insert(TABLE_NAME, null, values);

            // assign the Id of the new database row as the Id of the object
            medication.setId(id);
            return medication;
        }

        public Medication getMedication(long id) {
            Medication medication = null;

            // obtain a database connection
            SQLiteDatabase database = this.getWritableDatabase();

            // retrieve the note from the database
            String[] columns = new String[] {
                    "name",
                    "often",
                    "reason",
                    "start",
                    "dosage",
                    "repeats"
            };
            Cursor cursor = database.query(TABLE_NAME, columns, "_id = ?", new String[]{"" + id}, "", "", "");
            if (cursor.getCount() >= 1) {
                cursor.moveToFirst();
                medication = new Medication(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5)
                );
                medication.setId(id);
            }
            Log.i("DatabaseAccess", "getNote(" + id + "):  note: " + medication);
            return medication;
        }

        public ArrayList<Medication> getAllMedications() {
            ArrayList<Medication> medications = new ArrayList<Medication>();

            // obtain a database connection
            SQLiteDatabase database = this.getWritableDatabase();

            // retrieve the note from the database
            String[] columns = new String[] { "_id",
                    "name",
                    "often",
                    "reason",
                    "start",
                    "dosage",
                    "repeats"
            };
            try {
                Cursor cursor = database.query(TABLE_NAME, columns, "", new String[]{}, "", "", "");
                if (cursor.getCount() >= 1) {
                    cursor.moveToFirst();
                    do {
                        // collect the note data, and place it into a note object
                        long id = Long.parseLong(cursor.getString(0));
                        Medication medication = new Medication(
                                cursor.getString(1),
                                cursor.getString(2),
                                cursor.getString(3),
                                cursor.getString(4),
                                cursor.getString(5),
                                cursor.getString(6)
                        );
                        medication.setId(id);

                        // add the current note to the list
                        medications.add(medication);

                        // advance to the next row in the results
                        cursor.moveToNext();
                    } while (!cursor.isAfterLast());
                }
            } catch (SQLiteException databaseEmpty) {
                databaseEmpty.printStackTrace();
            }
            Log.i("DatabaseAccess", "getAllNotes():  num: " + medications.size());
            return medications;
        }

    public boolean updateNote(Medication medication) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // update the data in the database
        ContentValues values = new ContentValues();
        values.put("name", medication.getName());
        values.put("often", medication.getOften());
        values.put("reason", medication.getReason());
        values.put("start", medication.getStart());
        values.put("dosage", medication.getDosage());
        values.put("repeats", medication.getRepeats());

        int numRowsAffected = database.update(TABLE_NAME, values, "_id = ?", new String[] { "" + medication.getId() });

        Log.i("DatabaseAccess", "updateNote(" + medication + "):  numRowsAffected: " + numRowsAffected);

        // verify that the note was updated successfully
        return (numRowsAffected == 1);
    }

        public boolean deleteMedication(long id) {
            // obtain a database connection
            SQLiteDatabase database = this.getWritableDatabase();

            // delete the note
            int numRowsAffected = database.delete(TABLE_NAME, "_id = ?", new String[]{"" + id});

            Log.i("DatabaseAccess", "deleteNote(" + id + "):  numRowsAffected: " + numRowsAffected);

            // verify that the note was deleted successfully
            return (numRowsAffected == 1);
        }

        public void deleteAllMedications() {
            // obtain a database connection
            SQLiteDatabase database = this.getWritableDatabase();

            // delete the note
            int numRowsAffected = database.delete(TABLE_NAME, "", new String[] {});

            Log.i("DatabaseAccess", "deleteAllNotes():  numRowsAffected: " + numRowsAffected);
        }
    }


