package capstone.uoit.ca.lifenoteapp.functions.Appointments;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by 100472298.
 */
public class AppointmentsDBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_FILENAME = "appointments.db";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Appointment";
    public static final String column_aptName = "aptName";
    public static final String column_aptDate = "aptDate";
    public static final String column_aptTime = "aptTime";
    public static final String column_aptClinic = "aptClinic";
    public static final String column_aptDoctor = "aptDoctor";


    public static final String CREATE_STMT =
            "CREATE TABLE Appointment (" +
                    " id integer primary key, aptName text, aptDate text, aptTime text, aptClinic text,aptDoctor text )";

    public static final String DROP_STMT = "DROP TABLE Appointment";

    public AppointmentsDBHelper(Context context){
        super(context, DATABASE_FILENAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_STMT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_STMT);
        db.execSQL(CREATE_STMT);

    }

    public boolean createAppointment(String aptName, String aptDate, String aptTime, String aptClinic, String aptDoctor){

        SQLiteDatabase database =this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("aptName", aptName);
        values.put("aptDate", aptDate);
        values.put("aptTime", aptTime);
        values.put("aptClinic", aptClinic);
        values.put("aptDoctor", aptDoctor);

        database.insert(TABLE_NAME, null, values);
        return true;
    }

    public Cursor getAppointment(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery( "select * from Appointment where id="+id+"", null);
        return cursor;
    }


    public ArrayList<String> getAllAppointment(){

        ArrayList<String> appointments = new ArrayList<String>();
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor = database.rawQuery("select * from Appointment", null);
        cursor.moveToFirst();

        while(cursor.isAfterLast() == false){
            appointments.add(cursor.getString(cursor.getColumnIndex(column_aptName)));
            cursor.moveToNext();
        }

        return appointments;

    }

    public boolean updateAppointment (Integer id, String aptName, String aptDate, String aptTime, String aptClinic, String aptDoctor){

        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("aptName", aptName);
        values.put("aptDate", aptDate);
        values.put("aptTime", aptTime);
        values.put("aptClinic", aptClinic);
        values.put("aptDoctor", aptDoctor);

        database.update(TABLE_NAME, values, "id = ? ", new String[] { Integer.toString(id) });

        return true;

    }

    public Integer deleteAppointment(Integer id ){

        SQLiteDatabase database = this.getWritableDatabase();
        return database.delete(TABLE_NAME, "id = ? ", new String[]{Integer.toString(id)} );

    }

    public void deleteAllAppointment(){

        SQLiteDatabase database = this.getWritableDatabase();

        int numRowsAffected = database.delete(TABLE_NAME, "", new String[] {});

        Log.i("Appts", "deleteAllAppointment(): numRowsAffected: " + numRowsAffected);


    }




}
