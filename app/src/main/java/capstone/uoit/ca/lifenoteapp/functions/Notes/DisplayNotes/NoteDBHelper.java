package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.Note;
import capstone.uoit.ca.lifenoteapp.functions.Notes.CreateNotes.NoteLayoutDBHelper;

/**
 * Created by Peter on 16/01/16.
 */
public class NoteDBHelper extends SQLiteOpenHelper {
    private static NoteDBHelper ourInstance;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_FILENAME = "notestwo.db";
    public static final String TABLE_NAME = "Notes";
    Context context;

    public static NoteDBHelper getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new NoteDBHelper(context.getApplicationContext());
        }
        return ourInstance;
    }

    private NoteDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
        this.context = context;
    }

    // don't forget to use the column name '_id' for your primary key
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            "  _id integer primary key autoincrement, " +
            "  layoutId long not null, " +
            "  name text not null, " +
            "  date text not null, " +
            "  time text not null, " +
            "  docName text, " +
            "  docDetails text, " +
            "  illName text, " +
            "  illSymptoms text, " +
            "  illSeverity integer, " +
            "  additionalDetails text " +
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

    public Note createNote(Note note) {
        // create the object
//        Note note = new Note(modules);

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("layoutId", note.getLayout().getId());
        values.put("name", note.getName());
        values.put("date", note.getDate());
        values.put("time", note.getTime());
        values.put("docName", note.getDocName());
        values.put("docDetails", note.getDocDetails());
        values.put("illName", note.getIllName());
        values.put("illSymptoms", note.getIllSymptoms());
        values.put("illSeverity", note.getIllSeverity());
        values.put("additionalDetails", note.getAdditionalDetails());

        long id = database.insert(TABLE_NAME, null, values);

        // assign the Id of the new database row as the Id of the object
        note.setId(id);
        return note;
    }

    public Note getNote(long id) {
        Note note = null;

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the note from the database
        String[] columns = new String[] {
                "layoutId",
                "name",
                "date",
                "time",
                "docName",
                "docDetails",
                "illName",
                "illSymptoms",
                "illSeverity",
                "additionalDetails"};
        Cursor cursor = database.query(TABLE_NAME, columns, "_id = ?", new String[]{"" + id}, "", "", "");
        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            NoteLayoutDBHelper dbHelper = NoteLayoutDBHelper.getInstance(context);
            note = new Note(
                    dbHelper.getNoteLayout2(cursor.getLong(0)),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getString(6),
                    cursor.getString(7),
                    cursor.getInt(8),
                    cursor.getString(9)
            );
            note.setId(id);
        }

        Log.i("DatabaseAccess", "getNote(" + id + "):  note: " + note);

        return note;
    }

    public ArrayList<Note> getAllNotes() {
        ArrayList<Note> notes = new ArrayList<Note>();

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the note from the database
        String[] columns = new String[] { "_id",
                "layoutId",
                "name",
                "date",
                "time",
                "docName",
                "docDetails",
                "illName",
                "illSymptoms",
                "illSeverity",
                "additionalDetails"};
        Cursor cursor = database.query(TABLE_NAME, columns, "", new String[]{}, "", "", "");
        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            do {
                // collect the note data, and place it into a note object
                long id = Long.parseLong(cursor.getString(0));
                NoteLayoutDBHelper dbHelper = NoteLayoutDBHelper.getInstance(context);
                Note note = new Note(
                        dbHelper.getNoteLayout2(cursor.getLong(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7),
                        cursor.getString(8),
                        cursor.getInt(9),
                        cursor.getString(10)
                );
                note.setId(id);

                // add the current note to the list
                notes.add(note);

                // advance to the next row in the results
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }

        Log.i("DatabaseAccess", "getAllNotes():  num: " + notes.size());

        return notes;
    }

    public boolean updateNote(Note note) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // update the data in the database
        ContentValues values = new ContentValues();
        values.put("layoutId", note.getLayout().getId());
        values.put("name", note.getName());
        values.put("date", note.getDate());
        values.put("time", note.getTime());
        values.put("docName", note.getDocName());
        values.put("docDetails", note.getDocDetails());
        values.put("illName", note.getIllName());
        values.put("illSymptoms", note.getIllSymptoms());
        values.put("illSeverity", note.getIllSeverity());
        values.put("additionalDetails", note.getAdditionalDetails());

        int numRowsAffected = database.update(TABLE_NAME, values, "_id = ?", new String[] { "" + note.getId() });

        Log.i("DatabaseAccess", "updateNote(" + note + "):  numRowsAffected: " + numRowsAffected);

        // verify that the note was updated successfully
        return (numRowsAffected == 1);
    }

    public boolean deleteNote(long id) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // delete the note
        int numRowsAffected = database.delete(TABLE_NAME, "_id = ?", new String[]{"" + id});

        Log.i("DatabaseAccess", "deleteNote(" + id + "):  numRowsAffected: " + numRowsAffected);

        // verify that the note was deleted successfully
        return (numRowsAffected == 1);
    }

    public void deleteAllNotes() {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // delete the note
        int numRowsAffected = database.delete(TABLE_NAME, "", new String[] {});

        Log.i("DatabaseAccess", "deleteAllNotes():  numRowsAffected: " + numRowsAffected);
    }
}
