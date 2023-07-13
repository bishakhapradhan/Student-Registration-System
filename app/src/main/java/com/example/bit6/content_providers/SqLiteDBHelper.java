package com.example.bit6.content_providers;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.bit6.models.StudentData;
import com.example.bit6.utils.MyConstants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class SqLiteDBHelper
        extends SQLiteOpenHelper {

    Context mContext;

    public SqLiteDBHelper(Context context) {
        super(context, MyConstants.DB_NAME, null, 1);
        mContext = context;
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        //create table <table_name> (columns.... );

        //create table
        sqLiteDatabase.execSQL("create table "
                                       + MyConstants.STUDENT_TABLE_NAME
                                       + "(id integer primary key autoincrement, "
                                       + MyConstants.STUDENT_FNAME + " text, "
                                       + MyConstants.STUDENT_LNAME + " text, "
                                       + MyConstants.STUDENT_GENDER + " text, "
                                       + MyConstants.STUDENT_COUNTRY + " text, "
                                       + MyConstants.STUDENT_PHONE + " integer)"
                              );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + MyConstants.STUDENT_TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    //insert data into table
    public boolean insertStudentRecord(List<StudentData> studentData) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for (int i = 0; i < studentData.size(); i++) {
            contentValues.put(MyConstants.STUDENT_FNAME, studentData.get(i).getFirstName());
            contentValues.put(MyConstants.STUDENT_LNAME, studentData.get(i).getLastName());
            contentValues.put(MyConstants.STUDENT_GENDER, studentData.get(i).getGender());
            contentValues.put(MyConstants.STUDENT_COUNTRY, studentData.get(i).getCountry());
            contentValues.put(MyConstants.STUDENT_PHONE, studentData.get(i).getPhone());
        }
        database.insert(MyConstants.STUDENT_TABLE_NAME, null, contentValues);
        Toast.makeText(mContext, "Record inserted successfully !", Toast.LENGTH_SHORT).show();
        return true;
    }

    //read all records from table
    public ArrayList<StudentData> getAllStudentRecords() {
        ArrayList<StudentData> records = new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        Cursor cursor = database.rawQuery("SELECT * FROM "
                                                  + MyConstants.STUDENT_TABLE_NAME
                                                  + " WHERE "
                                                  + MyConstants.STUDENT_FNAME
                                                  + " NOT NULL",
                                          null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            records.add(new StudentData(cursor.getInt(0),
                                        cursor.getString(1),
                                        cursor.getString(2),
                                        cursor.getString(3),
                                        cursor.getString(4),
                                        cursor.getString(5)));
            cursor.moveToNext();
        }
        cursor.close();
        return records;
    }

    //read a single record from table
    public ArrayList<StudentData> getSingleStudentRecord(int studentId) {
        SQLiteDatabase database = this.getReadableDatabase();
        Cursor cursor =
                database.rawQuery("SELECT * FROM "
                                          + MyConstants.STUDENT_TABLE_NAME
                                          + " WHERE "
                                          + MyConstants.STUDENT_ID
                                          + "="
                                          + studentId,
                                  null);

        cursor.moveToFirst();
        ArrayList<StudentData> studentData = new ArrayList<>();
        while (!cursor.isAfterLast()) {
            studentData.add(new StudentData(cursor.getInt(0),
                                            cursor.getString(1),
                                            cursor.getString(2),
                                            cursor.getString(3),
                                            cursor.getString(4),
                                            cursor.getString(5)));
            cursor.moveToNext();
        }
        cursor.close();
        return studentData;
    }

    //update record in table
    public boolean updateStudentRecord(StudentData studentData) {
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(MyConstants.STUDENT_ID, studentData.getId());
        contentValues.put(MyConstants.STUDENT_FNAME, studentData.getFirstName());
        contentValues.put(MyConstants.STUDENT_LNAME, studentData.getLastName());
        contentValues.put(MyConstants.STUDENT_GENDER, studentData.getGender());
        contentValues.put(MyConstants.STUDENT_COUNTRY, studentData.getCountry());
        contentValues.put(MyConstants.STUDENT_PHONE, studentData.getPhone());

        database.update(MyConstants.STUDENT_TABLE_NAME,
                        contentValues,
                        MyConstants.STUDENT_ID + "=?",
                        new String[]{String.valueOf(studentData.getId())});
        database.close();
        Log.d("SqLiteDBHelper", "Record updated successfully !");
        return true;
    }

    //delete record in table
    public boolean deleteStudentData(int studentId){
        SQLiteDatabase database = this.getWritableDatabase();
        database.delete(MyConstants.STUDENT_TABLE_NAME,
                        "id=?",
                        new String[]{String.valueOf(studentId)});
        Toast.makeText(mContext,
                       "Student Record deleted successfully !",
                       Toast.LENGTH_SHORT).show();
        return true;
    }












}
