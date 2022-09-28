package com.example.cineenigma;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.cineenigma.data.FilmContract;

import java.sql.Timestamp;
import java.util.Date;

public class FilmListAdapter extends RecyclerView.Adapter<FilmListAdapter.FilmViewHolder> {

    private Cursor mCursor;
    private Context mContext;

    public FilmListAdapter(Context context, Cursor cursor) {
        this.mContext = context;
        this.mCursor = cursor;
    }

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.film_list_item, parent, false);
        return new FilmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FilmViewHolder holder, int position) {
        // Move the mCursor to the position of the item to be displayed
        if (!mCursor.moveToPosition(position))
            return; // bail if returned null

        // Update the view holder with the information needed to display
        String title = mCursor.getString(mCursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_TITLE_NAME));
        int noteScenario = mCursor.getInt(mCursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_NOTE_SCENARIO));
        int noteMusique = mCursor.getInt(mCursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_NOTE_MUSIQUE));
        int noteRealisation = mCursor.getInt(mCursor.getColumnIndex(FilmContract.FilmEntry.COLUMN_NOTE_REALISATION));
        long id = mCursor.getLong(mCursor.getColumnIndex(FilmContract.FilmEntry._ID));

        int note = (noteMusique + noteRealisation + noteScenario) / 3;

        // Display the film
        holder.titleFilm.setText(title);
        holder.note.setText(String.valueOf(note));
        holder.itemView.setTag(id);
    }

    @Override
    public int getItemCount() {
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        // Always close the previous mCursor first
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            // Force the RecyclerView to refresh
            this.notifyDataSetChanged();
        }
    }

    class FilmViewHolder extends RecyclerView.ViewHolder {

        TextView titleFilm;
        TextView note;
        TextView date;

        public FilmViewHolder(View itemView) {
            super(itemView);
            titleFilm = itemView.findViewById(R.id.title_film);
            note = itemView.findViewById(R.id.note_avg);
            date = itemView.findViewById(R.id.date);
        }

    }
}
