package com.tutorials.andorid.app.tutorials.sqlite;


import android.provider.BaseColumns;

public class ToDoContract {

    private ToDoContract() {
    }
    public static final String DB_NAME = "TodoDB";

    public static class ToDoEntry implements BaseColumns {
        public static final String TABLE_NAME = "todos";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_NAME_SUBTITLE = "subtitle";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_DATE = "target_date";
    }

    public static final String CREATE_TABLE =
            "CREATE TABLE " + ToDoEntry.TABLE_NAME + " (" +
                    ToDoEntry._ID + " INTEGER PRIMARY KEY," +
                    ToDoEntry.COLUMN_NAME_TITLE + " TEXT," +
                    ToDoEntry.COLUMN_NAME_SUBTITLE + " TEXT," +
                    ToDoEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                    ToDoEntry.COLUMN_NAME_DATE + " TEXT" +
                    ")";



}
