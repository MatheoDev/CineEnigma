package com.example.cineenigma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.cineenigma.data.FilmContract;
import com.example.cineenigma.data.FilmDbHelper;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FormActivity extends AppCompatActivity {

    EditText mTitle;
    EditText mDate;
    EditText mHour;
    RatingBar mScenario;
    RatingBar mRealisation;
    RatingBar mMusic;
    MultiAutoCompleteTextView mDescription;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        FilmDbHelper dbHelper = new FilmDbHelper(this);
        mDb = dbHelper.getWritableDatabase();

        mTitle = findViewById(R.id.name);
        mDate = findViewById(R.id.date);
        mHour = findViewById(R.id.hour);
        mScenario = findViewById(R.id.scenario);
        mRealisation = findViewById(R.id.realisation);
        mMusic = findViewById(R.id.musique);
        mDescription = findViewById(R.id.description);
    }

    public void addFilm(View view) {
        // test si les champs ne sont pas vide
        if (mTitle.getText().toString().trim().length() == 0 || mDate.getText().toString().trim().length() == 0 || mHour.getText().toString().trim().length() == 0 || mDescription.getText().toString().trim().length() == 0) {
            Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            return;
        }
        // test si les notes sont bien comprises entre 0 et 5
        if (mScenario.getRating() < 0 || mScenario.getRating() > 5 || mRealisation.getRating() < 0 || mRealisation.getRating() > 5 || mMusic.getRating() < 0 || mMusic.getRating() > 5) {
            Toast.makeText(this, "Veuillez entrer une note comprise entre 0 et 5", Toast.LENGTH_SHORT).show();
            return;
        }
        // test si la date est bien au format YYYY/MM/DD
        if (mDate.getText().toString().trim().length() != 10 || mDate.getText().toString().trim().charAt(4) != '/' || mDate.getText().toString().trim().charAt(7) != '/') {
            Toast.makeText(this, "Veuillez entrer une date au format YYYY/MM/DD", Toast.LENGTH_SHORT).show();
            return;
        }
        // test si l'heure est bien au format hh:mm
        if (mHour.getText().toString().trim().length() != 5 || mHour.getText().toString().trim().charAt(2) != ':') {
            Toast.makeText(this, "Veuillez entrer une heure au format hh:mm", Toast.LENGTH_SHORT).show();
            return;
        }

        addNewFilm();
        resetFields();
    }

    private long addNewFilm() {
        ContentValues values = new ContentValues();
        values.put(FilmContract.FilmEntry.COLUMN_TITLE_NAME, mTitle.getText().toString());
        // new date avec la date et heure
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd hh:mm").parse(mDate.getText().toString() + " " + mHour.getText().toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Timestamp timestamp = new Timestamp(date.getTime());
        values.put(FilmContract.FilmEntry.COLUMN_DATE, timestamp.getTime());
        values.put(FilmContract.FilmEntry.COLUMN_NOTE_SCENARIO, mScenario.getRating());
        values.put(FilmContract.FilmEntry.COLUMN_NOTE_REALISATION, mRealisation.getRating());
        values.put(FilmContract.FilmEntry.COLUMN_NOTE_MUSIQUE, mMusic.getRating());
        values.put(FilmContract.FilmEntry.COLUMN_DESCRIPTION, mDescription.getText().toString());
        return mDb.insert(FilmContract.FilmEntry.TABLE_NAME, null, values);
    }

    private void resetFields() {
        mTitle.setText("");
        mDate.setText("");
        mHour.setText("");
        mScenario.setRating(0);
        mRealisation.setRating(0);
        mMusic.setRating(0);
        mDescription.setText("");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_home) {
            Context context = FormActivity.this;
            Class<MainActivity> destinationActivity = MainActivity.class;
            Intent startChildActivityIntent = new Intent(context, destinationActivity);
            startActivity(startChildActivityIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}