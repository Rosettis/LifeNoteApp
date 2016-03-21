package capstone.uoit.ca.lifenoteapp.functions.Graphs;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * Created by Peter on 09/01/16.
 */
public class CodifiedWordsDBHelper extends SQLiteOpenHelper {

    private static CodifiedWordsDBHelper dbInstance;

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_FILENAME = "codified.db";
    private static final String TABLE_NAME = "codified";

    public static synchronized CodifiedWordsDBHelper getInstance(Context context) {
        if (dbInstance == null) dbInstance = new CodifiedWordsDBHelper(context.getApplicationContext());
        return dbInstance;
    }

    private CodifiedWordsDBHelper(Context context) {
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    public static final String CREATE_STATEMENT = "CREATE TABLE " + TABLE_NAME + "(" +
            "  keyWord text primary key," +
            "  value integer not null" +
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

    public void createAllEntries(TreeMap<String, Integer> treeMap) {
        Set set = treeMap.entrySet();
        for (Object entry : set) {
            Map.Entry currEntry = (Map.Entry) entry;
            createEntry((String) currEntry.getKey(), (Integer) currEntry.getValue());
        }
    }

    //add here
    public void createEntry(String keyWord, int value) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();
        Log.i("ADDING", "createEntry: " + keyWord + " value:" + value);

        // insert the data into the database
        ContentValues values = new ContentValues();
        values.put("keyWord", keyWord);
        values.put("value", (Integer) value);
        database.insert(TABLE_NAME, null, values);

        // assign the Id of the new database row as the Id of the object
    }

//    public Map.Entry getEntry(long id) {
//        Map.Entry noteLayout = null;
//
//        // obtain a database connection
//        SQLiteDatabase database = this.getWritableDatabase();
//
//        // retrieve the contact from the database
//        String[] columns = new String[] {
//                "keyWord",
//                "value"};
//        Cursor cursor = database.query(TABLE_NAME, columns, "_id = ?", new String[] { "" + id }, "", "", "");
//        if (cursor.getCount() >= 1) {
//            cursor.moveToFirst();
//            noteLayout = new Map.Entry(cursor.getString(0),
//                    cursor.getInt(1)>0,
//                    cursor.getInt(2)>0,
//                    cursor.getInt(3)>0,
//                    cursor.getInt(4)>0,
//                    cursor.getInt(5)>0,
//                    cursor.getInt(6)>0,
//                    cursor.getInt(7)>0,
//                    cursor.getInt(8)>0,
//                    cursor.getInt(9)>0,
//                    cursor.getInt(10)>0,
//                    cursor.getInt(11)>0,
//                    cursor.getInt(12)>0,
//                    cursor.getInt(13)>0,
//                    cursor.getInt(14)>0);
//            noteLayout.setId(id);
//        }
//        return noteLayout;
//    }

    public TreeMap<String, Integer> generateHashMap() {
//        ArrayList<Map.Entry> entries = new ArrayList<Map.Entry>();
        TreeMap<String, Integer> treeMap = new TreeMap<>();

        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // retrieve the contact from the database
        String[] columns = new String[] {
                "keyWord",
                "value"};

        try {
            Cursor cursor = database.query(TABLE_NAME, columns, "", new String[]{}, "", "", "");
            cursor.moveToFirst();
            do {
                // collect the contact data, and place it into a contact object
                treeMap.put(cursor.getString(0), cursor.getInt(1));

                // advance to the next row in the results
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        } catch (CursorIndexOutOfBoundsException databaseEmpty) {
            Log.i("CodifiedWordsDBHelper", "generateHashMap: Database empty, returning empty tree map");
        }
        return treeMap;
    }

    public boolean updateEntry(String keyWord, int value) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // update the data in the database
        ContentValues values = new ContentValues();
        values.put("keyWord", keyWord);
        values.put("value", value);

        int numRowsAffected = database.update(TABLE_NAME, values, "keyWord = ?", new String[] { "" + keyWord });

        // verify that the contact was updated successfully
        return (numRowsAffected == 1);
    }

    public boolean deleteEntry(long id) {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // delete the contact
        int numRowsAffected = database.delete(TABLE_NAME, "_id = ?", new String[]{"" + id});

        Log.i("DatabaseAccess", "deleteContact(" + id + "):  numRowsAffected: " + numRowsAffected);

        // verify that the contact was deleted successfully
        return (numRowsAffected == 1);
    }

    public void deleteAllEntrys() {
        // obtain a database connection
        SQLiteDatabase database = this.getWritableDatabase();

        // delete the contact
        int numRowsAffected = database.delete(TABLE_NAME, "", new String[] {});

        Log.i("DatabaseAccess", "deleteAllContacts():  numRowsAffected: " + numRowsAffected);
    }
}
