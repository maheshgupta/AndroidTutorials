package com.tutorials.andorid.app.tutorials.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.tutorials.andorid.app.model.todo.Todo;

import java.util.ArrayList;
import java.util.List;


public class TodoDatabaseHelper extends SQLiteOpenHelper {


    public TodoDatabaseHelper(Context context) {
        super(context, ToDoContract.DB_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ToDoContract.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldversion, int newVersion) {
        if (newVersion < 3) {

        }
    }

    public long addNewTodo(@NonNull Todo todo) {
        SQLiteDatabase writableDatabase = getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(ToDoContract.ToDoEntry.COLUMN_NAME_TITLE, todo.title);
        contentValues.put(ToDoContract.ToDoEntry.COLUMN_NAME_SUBTITLE, todo.subtitle);
        contentValues.put(ToDoContract.ToDoEntry.COLUMN_NAME_DESCRIPTION, todo.description);
        contentValues.put(ToDoContract.ToDoEntry.COLUMN_NAME_DATE, todo.dateTime);

        return writableDatabase.insert(ToDoContract.ToDoEntry.TABLE_NAME, null, contentValues);
    }

    public List<Todo> getTodos() {
        ArrayList<Todo> todos = new ArrayList<>();
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.query(ToDoContract.ToDoEntry.TABLE_NAME, null, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            while (cursor.moveToNext()) {
                String title = cursor.getString(cursor.getColumnIndex(ToDoContract.ToDoEntry.COLUMN_NAME_TITLE));
                todos.add(new Todo(title, null, null, null));
            }
        }
        cursor.close();
        return todos;
    }

}
