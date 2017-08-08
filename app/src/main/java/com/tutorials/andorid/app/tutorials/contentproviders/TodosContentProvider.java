package com.tutorials.andorid.app.tutorials.contentproviders;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import com.tutorials.andorid.app.model.todo.Todo;
import com.tutorials.andorid.app.tutorials.sqlite.SqlTutorialActivity;
import com.tutorials.andorid.app.tutorials.sqlite.TodoDatabaseHelper;

public class TodosContentProvider extends ContentProvider {

    private static final String CONTENT_URI = "com.android.cp.todos";

    public TodosContentProvider() {
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        // Implement this to handle requests to delete one or more rows.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public String getType(Uri uri) {
        // at the given URI.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public Uri insert(Uri uri, ContentValues values) {
        TodoDatabaseHelper todoDatabaseHelper = new TodoDatabaseHelper(getContext());
        Todo todo = new Todo(
                values.getAsString("title"), values.getAsString("subtitle"),
                values.getAsString("description"), values.getAsString("target_date")
        );
        long id = todoDatabaseHelper.addNewTodo(todo);
        Uri newUri = ContentUris.withAppendedId(Uri.parse(CONTENT_URI), id);
        return newUri;
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        TodoDatabaseHelper todoDatabaseHelper = new TodoDatabaseHelper(getContext());
        return todoDatabaseHelper.queryForTodos();
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
