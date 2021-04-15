package com.gopal.devjunction.pets.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class PetsDbHelper extends SQLiteOpenHelper {

    static final String  DATABASE_NAME = "petss.db";
    static  final String DATABASE_VERSION = "1";

    /**
     * Constructs a new instance of {@link PetsDbHelper}.
     *
     * @param context of the app
     */
    public PetsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, Integer.parseInt( DATABASE_VERSION ) );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_SQL = "CREATE TABLE " + PetContract.PetEntry.TABLE_NAME + "("
                 + PetContract.PetEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + PetContract.PetEntry.COLUMN_PET_NAME + ");"
                 ;

        db.execSQL( CREATE_TABLE_SQL );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
