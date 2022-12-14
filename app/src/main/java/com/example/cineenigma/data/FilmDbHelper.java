package com.example.cineenigma.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FilmDbHelper extends SQLiteOpenHelper {

    // The database name
    private static final String DATABASE_NAME = "film.db";

    // If you change the database schema, you must increment the database version
    private static final int DATABASE_VERSION = 1;

    // Constructor
    public FilmDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        // Create a table to hold waitlist data
        final String SQL_CREATE_FILM_TABLE = "CREATE TABLE " + FilmContract.FilmEntry.TABLE_NAME + " (" +
                FilmContract.FilmEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                FilmContract.FilmEntry.COLUMN_TITLE_NAME + " TEXT NOT NULL, " +
                FilmContract.FilmEntry.COLUMN_NOTE_REALISATION + " INTEGER NOT NULL, " +
                FilmContract.FilmEntry.COLUMN_NOTE_SCENARIO + " INTEGER NOT NULL, " +
                FilmContract.FilmEntry.COLUMN_NOTE_MUSIQUE + " INTEGER NOT NULL, " +
                FilmContract.FilmEntry.COLUMN_DESCRIPTION + " TEXT NOT NULL, " +
                FilmContract.FilmEntry.COLUMN_DATE + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP" +
                "); ";

        sqLiteDatabase.execSQL(SQL_CREATE_FILM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        // For now simply drop the table and create a new one. This means if you change the
        // DATABASE_VERSION the table will be dropped.
        // In a production app, this method might be modified to ALTER the table
        // instead of dropping it, so that existing data is not deleted.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + FilmContract.FilmEntry.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

}
