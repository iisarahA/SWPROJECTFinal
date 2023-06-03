package com.example.additem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.widget.EditText;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;


public class DataBaseHelper extends SQLiteOpenHelper {


    public static final String STADIUM_TABLE = "STADIUM_TABLE";
    public static final String COLUMN_STADIUM_ID = "ID";
    public static final String COLUMN_STADIUM_NAME = "STADIUM_NAME";
    public static final String COLUMN_SPORT_TYPE = "SPORT_TYPE";
    public static final String COLUMN_STADIUM_LOCATION = "STADIUM_LOCATION";
    public static final String COLUMN_STADIUM_PRICE = "STADIUM_PRICE";

    public static final String COLUMN_RENT_DATE="RENT_DATE";
    public static final String COLUMN_RENT_TIME="RENT_TIME";
    public static final String COLUMN_RENT_HOUR="RENT_HOURS";



    public DataBaseHelper(@Nullable Context context) {
        super(context, "STADIUM2.db", null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //String createTableStatement = "CREATE TABLE " + STADIUM_TABLE + " (" +  COLUMN_STADIUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_STADIUM_NAME + " TEXT , " +COLUMN_SPORT_TYPE + " TEXT, " +COLUMN_STADIUM_LOCATION + " TEXT , " +COLUMN_STADIUM_PRICE + " INTEGER , " + COLUMN_STADIUM_IMAGENN + " TEXT , " + COLUMN_STADIUM_IMAGE + " BLOB )";
        String createTableStatement = "CREATE TABLE " + STADIUM_TABLE + " (" +
                COLUMN_STADIUM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_STADIUM_NAME + " TEXT, " +
                COLUMN_SPORT_TYPE + " TEXT, " +
                COLUMN_STADIUM_LOCATION + " TEXT, " +
                COLUMN_STADIUM_PRICE + " INTEGER )";

        String createRentedTable = "CREATE TABLE " + "Rented_STADIUM" + " (" +
                COLUMN_STADIUM_ID + " INTEGER PRIMARY KEY, " +
                COLUMN_STADIUM_NAME + " TEXT, " +
                COLUMN_SPORT_TYPE + " TEXT, " +
                COLUMN_STADIUM_LOCATION + " TEXT, " +
                COLUMN_STADIUM_PRICE + " INTEGER, " +
                COLUMN_RENT_DATE + " TEXT, " +
                COLUMN_RENT_TIME + " TEXT, " +
                COLUMN_RENT_HOUR + " INTEGER )";


        db.execSQL(createTableStatement);
        db.execSQL(createRentedTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


    public boolean addOne(ItemModel itemMod){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STADIUM_NAME, itemMod.getName());
        cv.put(COLUMN_SPORT_TYPE ,itemMod.getSportType());
        cv.put(COLUMN_STADIUM_LOCATION ,itemMod.getLocation());
        cv.put(COLUMN_STADIUM_PRICE,itemMod.getPrice());


        long insert = db.insert(STADIUM_TABLE , null, cv);
        //db.close();
      return true;

    }




   /* public List<ItemModel> getEveryone(){
        List<ItemModel> returnList = new ArrayList<>();
        // get data from database
        String queryString = "Select * from "+ STADIUM_TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(queryString, null);
        if(cursor.moveToFirst()){
            // loop through cursor results
            do{
                int SID = cursor.getInt(0); //stadium id
                String SName = cursor.getString(1);
                String Stype = cursor.getString(2);
                String Slocation = cursor.getString(3);
                int Sprice = cursor.getInt(4);


                ItemModel newStadium = new ItemModel(SName, SID, Stype,Slocation,Sprice);
                returnList.add(newStadium);
            }while (cursor.moveToNext());
        } else{
            // nothing happens. no stadium is added.
        }
        //close
        cursor.close();
        //db.close();
        return returnList;
    } */

    public List<ItemModel> getEveryone() {
        List<ItemModel> returnList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query for stadiums in STADIUM_TABLE
        String queryString1 = "SELECT * FROM " + STADIUM_TABLE;
        Cursor cursor1 = db.rawQuery(queryString1, null);
        if (cursor1.moveToFirst()) {
            do {
                int stadiumId = cursor1.getInt(0);
                String stadiumName = cursor1.getString(1);
                String sportType = cursor1.getString(2);
                String location = cursor1.getString(3);
                int price = cursor1.getInt(4);

                ItemModel stadium = new ItemModel(stadiumName, stadiumId, sportType, location, price);
                returnList.add(stadium);
            } while (cursor1.moveToNext());
        }
        cursor1.close();

        // Query for rented stadiums in Rented_STADIUM table
        String queryString2 = "SELECT * FROM Rented_STADIUM";
        Cursor cursor2 = db.rawQuery(queryString2, null);
        if (cursor2.moveToFirst()) {
            do {
                int stadiumIdIndex = cursor2.getColumnIndex(COLUMN_STADIUM_ID);
                if (stadiumIdIndex >= 0) {
                    int stadiumId = cursor2.getInt(0);
                    String stadiumName = cursor2.getString(1);
                    String sportType = cursor2.getString(2);
                    String location = cursor2.getString(3);
                    int price = cursor2.getInt(4);

                    ItemModel stadium = new ItemModel(stadiumName, stadiumId, sportType, location, price);
                    returnList.add(stadium);
                }
            } while (cursor2.moveToNext());
        }
        cursor2.close();

        return returnList;
    }



    public boolean deleteOne(ItemModel itemMod){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = " DELETE FROM " + STADIUM_TABLE + " WHERE "+ COLUMN_STADIUM_ID + " = " + itemMod.getId() ;
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }

    }//end deleteOne

//rent should remove the stadium from stadium table and add to rented staudium table
    public boolean Rent(ItemModel itemMod, EditText date ,EditText time,EditText hours ){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String hoursAsString = hours.getText().toString(); // Get the input text as a string
int Hoursvalue;
        try {
             Hoursvalue = Integer.parseInt(hoursAsString); // Convert the string to an integer
            // Use the 'value' variable as an integer
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid integer
            Hoursvalue=-1;
        }
        cv.put(COLUMN_STADIUM_ID, itemMod.getId());
        cv.put(COLUMN_STADIUM_NAME, itemMod.getName());
        cv.put(COLUMN_SPORT_TYPE ,itemMod.getSportType());
        cv.put(COLUMN_STADIUM_LOCATION ,itemMod.getLocation());
        cv.put(COLUMN_STADIUM_PRICE,itemMod.getPrice());
        cv.put(COLUMN_RENT_DATE, String.valueOf(date.getText()));
        cv.put(COLUMN_RENT_TIME, String.valueOf(time.getText()));
        cv.put(COLUMN_RENT_HOUR, Hoursvalue);


        long insert = db.insert("Rented_STADIUM" , null, cv);
        deleteOne(itemMod);
        //db.close();

        if (insert == -1) {
            // Handle insertion failure
            return false;
        }
        else
            return true;

    }

    public boolean isStadiumRented(ItemModel itemMod) {
        SQLiteDatabase db = this.getReadableDatabase();
        String queryString = "SELECT * FROM Rented_STADIUM WHERE " + COLUMN_STADIUM_ID + " = " + itemMod.getId();
        Cursor cursor = db.rawQuery(queryString, null);

        boolean isRented = cursor.getCount() > 0;

        cursor.close();
        return isRented;
    }

    public boolean returnStadium(ItemModel itemMod) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_STADIUM_ID, itemMod.getId());
        cv.put(COLUMN_STADIUM_NAME, itemMod.getName());
        cv.put(COLUMN_SPORT_TYPE, itemMod.getSportType());
        cv.put(COLUMN_STADIUM_LOCATION, itemMod.getLocation());
        cv.put(COLUMN_STADIUM_PRICE, itemMod.getPrice());

        long insert = db.insert(STADIUM_TABLE, null, cv);

        if (insert == -1) {
            // Handle insertion failure
            return false;
        } else {
            // Delete the rented stadium from the rented table
            String whereClause = COLUMN_STADIUM_ID + " = ?";
            String[] whereArgs = {String.valueOf(itemMod.getId())};
            int deleteCount = db.delete("Rented_STADIUM", whereClause, whereArgs);

            if (deleteCount > 0) {
                // Return success if the stadium was successfully deleted from the rented table
                return true;
            } else {
                // Handle deletion failure
                return false;
            }
        }
    }












}