package com.fanshuchao.example.criminalintent;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import database.CrimeDbSchema.CrimeDbSchema;

/**
 * Created by fanshuchao on 2017-1-16.
 */
//单例类
public class CrimeLab {
    private static CrimeLab sCrimeLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;


    public static CrimeLab get(Context context){
        if (sCrimeLab == null){
            sCrimeLab = new CrimeLab(context);
        }
        return sCrimeLab;
    }
    private CrimeLab(Context context){

        mContext = context.getApplicationContext(); //ApplicationContext一直存在整个应用进程，若采用activity，由于是单例，activity不会销毁，浪费资源
        mDatabase = new CrimeBaseHelper(mContext).getWritableDatabase();

    }

    public List<Crime> getCrimes(){
        List<Crime> crimes = new ArrayList<>();

        CrimeCursorWrapper cursor = queryCrimes(null,null);

        try{
            cursor.moveToFirst();
            while (!cursor.isAfterLast()){
                crimes.add(cursor.getCrime());
                cursor.moveToNext();
            }
        }finally {
            cursor.close();
        }
        return crimes;
    }

    public Crime getCrime(UUID id){

        CrimeCursorWrapper cursor = queryCrimes(
                CrimeDbSchema.CrimeTable.Cols.UUID + "=?", new String[] {id.toString()}
        );
        try{
            if(cursor.getCount() ==0){
                return null;
            }
            cursor.moveToFirst();
            return cursor.getCrime();
        }finally {
            cursor.close();
        }
    }
    public void updateCrime(Crime crime){
        String uuidString = crime.getId().toString();
        ContentValues values = getContentValues(crime);

        mDatabase.update(CrimeDbSchema.CrimeTable.NAME,values,
                CrimeDbSchema.CrimeTable.Cols.UUID + " =?",new String[]{uuidString});
    }
    public void addCrime(Crime c){
        ContentValues values = getContentValues(c);
        mDatabase.insert(CrimeDbSchema.CrimeTable.NAME,null,values);
    }
    private static ContentValues getContentValues(Crime crime){
        ContentValues values = new ContentValues();
        values.put(CrimeDbSchema.CrimeTable.Cols.UUID,crime.getId().toString());
        values.put(CrimeDbSchema.CrimeTable.Cols.TITLE,crime.getTitle());
        values.put(CrimeDbSchema.CrimeTable.Cols.DATE,crime.getDate().getTime());
        values.put(CrimeDbSchema.CrimeTable.Cols.SOLVED,crime.isSolved() ? 1:0);
        values.put(CrimeDbSchema.CrimeTable.Cols.SUSPECT,crime.getSuspect());
        return values;
    }
    private CrimeCursorWrapper queryCrimes(String whereClause,String[] whereArgs){
        Cursor cursor = mDatabase.query(
                CrimeDbSchema.CrimeTable.NAME,
                null,   //Columns -null selects all columns
                whereClause,
                whereArgs,
                null, //groupBy
                null,  //having
                null   //orderBy
        );
        return new CrimeCursorWrapper(cursor);

    }

    public File getPhotoFile(Crime crime){
        File externalFileDir = mContext.getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        if(externalFileDir == null){
            return null;
        }
        return new File(externalFileDir,crime.getPhotoFilename());
    }
}
