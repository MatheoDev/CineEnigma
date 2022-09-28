package com.example.cineenigma.data;

import android.provider.BaseColumns;

public class FilmContract {

    public static final class FilmEntry implements BaseColumns {
        public static final String TABLE_NAME = "film";
        public static final String COLUMN_TITLE_NAME = "name";
        public static final String COLUMN_DATE = "date";
        public static final String COLUMN_NOTE_SCENARIO = "scenario";
        public static final String COLUMN_NOTE_REALISATION = "realisation";
        public static final String COLUMN_NOTE_MUSIQUE = "musique";
        public static final String COLUMN_DESCRIPTION = "description";
    }

}
