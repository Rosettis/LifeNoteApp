package capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Peter on 09/01/16.
 */
public class NoteLayoutDBHelper extends SQLiteOpenHelper {

    private static NoteLayoutDBHelper dbInstance;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILENAME = "notelayout.db";
    private static final String TABLE_NAME = "NoteLayouts";

    public static synchronized NoteLayoutDBHelper getInstance(Context context) {
        if (dbInstance == null) dbInstance = new NoteLayoutDBHelper(context.getApplicationContext());
        return dbInstance;
    }

    private NoteLayoutDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            "  _id integer primary key autoincrement, " +
            "  layoutName text not null," +
            "  hasDoctorFrag integer not null," +
            "  hasIllnessFrag integer not null," +
            "  hasCodifyFrag integer not null" +
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

    //add here
    public NoteLayout createNoteLayout(String layoutName, boolean hasDoctorFrag, boolean hasIllnessFrag , boolean hasCodifyFrag) {
        // create the object
        NoteLayout noteLayout = new NoteLayout(layoutName, hasDoctorFrag, hasIllnessFrag, hasCodifyFrag);

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // insert the data into the database
        ContentValues values = new ContentValues();
        values.put("layoutName", noteLayout.getLayoutName());
        values.put("hasDoctorFrag", noteLayout.hasDoctorFragAsInt());
        values.put("hasIllnessFrag", noteLayout.hasIllnessFragAsInt());
        values.put("hasCodifyFrag", noteLayout.hasCodifyFragAsInt());
        long id = database.insert(TABLE_NAME, null, values);

        // assign the Id of the new database row as the Id of the object
        noteLayout.setId(id);

        return noteLayout;
    }

    public NoteLayout createNoteLayout(NoteLayout  noteLayout) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // insert the data into the database
        ContentValues values = new ContentValues();
        values.put("layoutName", noteLayout.getLayoutName());
        values.put("hasDoctorFrag", noteLayout.hasDoctorFragAsInt());
        values.put("hasIllnessFrag", noteLayout.hasIllnessFragAsInt());
        values.put("hasCodifyFrag", noteLayout.hasCodifyFragAsInt());
        long id = database.insert(TABLE_NAME, null, values);

        // assign the Id of the new database row as the Id of the object
        noteLayout.setId(id);

        return noteLayout;
    }

    public boolean intToBool(int i) {
        return i == 1;
    }

    public NoteLayout getNoteLayout(long id) {
        NoteLayout noteLayout = null;

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the contact from the database
        String[] columns = new String[] { "layoutName", "hasDoctorFrag", "hasIllnessFrag", "hasCodifyFrag"};
        Cursor cursor = database.query(TABLE_NAME, columns, "_id = ?", new String[] { "" + id }, "", "", "");
        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            String layoutName = cursor.getString(0);
            boolean hasDoctorFrag = cursor.getInt(1)>0;
            boolean hasIllnessFrag = cursor.getInt(2)>0;
            boolean hasCodifyFrag = cursor.getInt(3)>0;
            noteLayout = new NoteLayout(layoutName, hasDoctorFrag, hasIllnessFrag, hasCodifyFrag);
            noteLayout.setId(id);
        }
        return noteLayout;
    }

    public ArrayList<NoteLayout> getAllLayouts() {
        ArrayList<NoteLayout> layouts = new ArrayList<NoteLayout>();

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the contact from the database
        String[] columns = new String[] { "_id", "layoutName", "hasDoctorFrag", "hasIllnessFrag", "hasCodifyFrag"};
        Cursor cursor = database.query(TABLE_NAME, columns, "", new String[]{}, "", "", "");
        cursor.moveToFirst();
        do {
            // collect the contact data, and place it into a contact object
            long id = Long.parseLong(cursor.getString(0));
            String layoutName = cursor.getString(1);
            boolean hasDoctorFrag = cursor.getInt(2)>0;
            boolean hasIllnessFrag = cursor.getInt(3)>0;
            boolean hasCodifyFrag = cursor.getInt(4)>0;
            NoteLayout noteLayout = new NoteLayout(layoutName, hasDoctorFrag, hasIllnessFrag, hasCodifyFrag);
            noteLayout.setId(id);

            // add the current contact to the list
            layouts.add(noteLayout);

            // advance to the next row in the results
            cursor.moveToNext();
        } while (!cursor.isAfterLast());

        Log.i("DatabaseAccess", "getAllContacts():  num: " + layouts.size());

        return layouts;
    }

    public boolean updateNoteLayout(NoteLayout noteLayout) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // update the data in the database
        ContentValues values = new ContentValues();

        values.put("layoutName", noteLayout.getLayoutName());
        values.put("hasDoctorFrag", noteLayout.hasDoctorFragAsInt());
        values.put("hasIllnessFrag", noteLayout.hasIllnessFragAsInt());
        values.put("hasCodifyFrag", noteLayout.hasCodifyFragAsInt());

        int numRowsAffected = database.update(TABLE_NAME, values, "_id = ?", new String[] { "" + noteLayout.getId() });

        // verify that the contact was updated successfully
        return (numRowsAffected == 1);
    }

    public boolean deleteNoteLayout(long id) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // delete the contact
        int numRowsAffected = database.delete(TABLE_NAME, "_id = ?", new String[]{"" + id});

        Log.i("DatabaseAccess", "deleteContact(" + id + "):  numRowsAffected: " + numRowsAffected);

        // verify that the contact was deleted successfully
        return (numRowsAffected == 1);
    }

    public void deleteAllNoteLayouts() {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // delete the contact
        int numRowsAffected = database.delete(TABLE_NAME, "", new String[] {});

        Log.i("DatabaseAccess", "deleteAllContacts():  numRowsAffected: " + numRowsAffected);
    }
}
