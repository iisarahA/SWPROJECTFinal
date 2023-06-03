package com.example.additem;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class DataBaseHandler extends SQLiteOpenHelper {

Context context;
private static String DATABASE_NAME="mydb.db";
private static int DATABASE_VERSION = 1 ;

    private ByteArrayOutputStream objectByteArrayOutputStream ;
    private byte[] imageInBytes;
private static String createTableQuery="CREATE TABLE IMAGEINFO (imageName TEXT" +
        ",image BLOB)";
    public DataBaseHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try{
           db.execSQL(createTableQuery);
            Toast.makeText(context , "image table created succsfuly", Toast.LENGTH_SHORT).show();
        }
        catch(Exception e){
            Toast.makeText(context , e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

  public void storeImage(ModelClass objectModelClass) {

        try {
            SQLiteDatabase objSqLiteDatabase = this.getWritableDatabase();
            Bitmap imageToStoreBitmap = objectModelClass.getImage();
            objectByteArrayOutputStream = new ByteArrayOutputStream();
            imageToStoreBitmap.compress(Bitmap.CompressFormat.JPEG, 100, objectByteArrayOutputStream);
            imageInBytes = objectByteArrayOutputStream.toByteArray();
            ContentValues objectContentValues = new ContentValues();
            objectContentValues.put("imageName", objectModelClass.getImageName());
            objectContentValues.put("image", imageInBytes);

            long b =objSqLiteDatabase.insert("IMAGEINFO", null, objectContentValues);
            if(b != -1){
                Toast.makeText(context , "DATA ADDED", Toast.LENGTH_SHORT).show();
                objSqLiteDatabase.close();
            }
            else{
                Toast.makeText(context , "DATA NOT  ADDED", Toast.LENGTH_SHORT).show();
            }
            objSqLiteDatabase.close();
        }
        catch(Exception e){
            Toast.makeText(context , e.getMessage(), Toast.LENGTH_SHORT).show();
        }



    }

    public ArrayList<ModelClass> getAllImagesData(){
       try{
          SQLiteDatabase objectSqliteDatabase=this.getReadableDatabase();
          ArrayList<ModelClass> objectModelClassList=new ArrayList<>();
          Cursor objectCursor =objectSqliteDatabase.rawQuery("SELECT * FROM IMAGEINFO" , null);
          if(objectCursor.getCount() != 0 ){
              while(objectCursor.moveToNext())
              {
                  String nameOfImage=objectCursor.getString(0);
                  byte[] imageBytes=objectCursor.getBlob(1);
                  Bitmap objectBitmap= BitmapFactory.decodeByteArray(imageBytes,0,imageBytes.length);
                  objectModelClassList.add(new ModelClass(nameOfImage , objectBitmap));
              }
              return objectModelClassList;
          }
          else
           {
               Toast.makeText (context, "No Rows exist in database", Toast.LENGTH_SHORT).show();
               return null ;
           }

       }
       catch(Exception e){
           Toast.makeText(context , e.getMessage(), Toast.LENGTH_SHORT).show();
           return null ;
       }
    }

   /* public boolean deleteImage(ModelClass model){
        SQLiteDatabase db = this.getWritableDatabase();
        String queryString = " DELETE FROM IMAGEINFO " + " WHERE imageName " + " = " + model.getImageName() ;
        Cursor cursor = db.rawQuery(queryString, null);

        if(cursor.moveToFirst()){
            return true;
        }
        else{
            return false;
        }

    }//end deleteImage */












}
