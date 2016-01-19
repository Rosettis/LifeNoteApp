package capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import capstone.uoit.ca.lifenoteapp.functions.Notes.DisplayNotes.Modules.NoteModule;

/**
 * Created by Peter on 16/01/16.
 */
public class NoteDBHelper extends SQLiteOpenHelper{
    private static NoteDBHelper ourInstance;
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_FILENAME = "notes.db";
    public static final String TABLE_NAME = "Notes";

    public static NoteDBHelper getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = new NoteDBHelper(context.getApplicationContext());
        }
        return ourInstance;
    }

    private NoteDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    // don't forget to use the column name '_id' for your primary key
    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            "  _id integer primary key autoincrement, " +
            "  note blob not null" +
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

        // insert the data into the database
        ContentValues values = new ContentValues();
        values.put("note", toByteArray(note));
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
        String[] columns = new String[] { "note" };
        Cursor cursor = database.query(TABLE_NAME, columns, "_id = ?", new String[]{"" + id}, "", "", "");
        if (cursor.getCount() >= 1) {
            cursor.moveToFirst();
            byte[] bytes = cursor.getBlob(0);
            note = new Note(toArrayList(bytes));
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
        String[] columns = new String[] { "_id", "note" };
        Cursor cursor = database.query(TABLE_NAME, columns, "", new String[]{}, "", "", "");
        cursor.moveToFirst();
        do {
            // collect the note data, and place it into a note object
            long id = Long.parseLong(cursor.getString(0));
            byte[] bytes = cursor.getBlob(1);
            Note note = new Note(toArrayList(bytes));
            note.setId(id);

            // add the current note to the list
            notes.add(note);

            // advance to the next row in the results
            cursor.moveToNext();
        } while (!cursor.isAfterLast());

        Log.i("DatabaseAccess", "getAllNotes():  num: " + notes.size());

        return notes;
    }

    public boolean updateNote(Note note) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // update the data in the database
        ContentValues values = new ContentValues();
        values.put("note", toByteArray(note));
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

    public byte[] toByteArray(Note note) {
        ArrayList<NoteModule> modules = note.getModules();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;
        byte[] bytes;
        try {
            out = new ObjectOutputStream(bos);
            out.writeObject(modules);
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
        return bytes;
    }

    public ArrayList<NoteModule> toArrayList(byte[] bytes){
        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;
        ArrayList<NoteModule> o;
        try {
            in = new ObjectInputStream(bis);
            o = (ArrayList<NoteModule>) in.readObject();
        } catch (IOException | ClassNotFoundException ioe) {
            o = null;
        } finally {
            try {
                bis.close();
            } catch (IOException ex) {
                // ignore close exception
            }
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                // ignore close exception
            }
        }
        return o;
    }
}
