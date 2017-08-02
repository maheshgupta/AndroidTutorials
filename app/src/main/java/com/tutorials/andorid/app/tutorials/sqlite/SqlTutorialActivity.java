package com.tutorials.andorid.app.tutorials.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.todo.Todo;

public class SqlTutorialActivity extends AppCompatActivity {

    private TodoDatabaseHelper todoDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql_tutorial);
        this.todoDatabaseHelper = new TodoDatabaseHelper(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        long id = this.todoDatabaseHelper.addNewTodo(new Todo("Todo 1", null, null, null));
        Log.i("", "onResume: Id of the inserted record" + id);
        this.todoDatabaseHelper.getTodos();
    }
}
