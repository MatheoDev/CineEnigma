package com.example.cineenigma;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.cineenigma.data.FilmContract;
import com.example.cineenigma.data.FilmDbHelper;

public class MainActivity extends AppCompatActivity {

    private FilmListAdapter mAdapter;
    private SQLiteDatabase mDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.film_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FilmDbHelper dbHelper = new FilmDbHelper(this);
        mDb = dbHelper.getWritableDatabase();
        Cursor cursor = getAllFilms();

        mAdapter = new FilmListAdapter(this, cursor);
        recyclerView.setAdapter(mAdapter);

    }

    private Cursor getAllFilms() {
        return mDb.query(
                FilmContract.FilmEntry.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                FilmContract.FilmEntry.COLUMN_TITLE_NAME
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();
        if (itemThatWasClickedId == R.id.action_create) {
            Context context = MainActivity.this;
            Class<FormActivity> destinationActivity = FormActivity.class;
            Intent startChildActivityIntent = new Intent(context, destinationActivity);
            startActivity(startChildActivityIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}