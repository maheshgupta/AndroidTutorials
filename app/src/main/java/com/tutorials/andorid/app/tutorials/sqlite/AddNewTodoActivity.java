package com.tutorials.andorid.app.tutorials.sqlite;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.todo.Todo;

public class AddNewTodoActivity extends AppCompatActivity {

    TextView txtViewTodoTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_todo);
        txtViewTodoTitle = (TextView) findViewById(R.id.todo_title);
    }

    public void AddTodo(View view) {
        TodoDatabaseHelper todoDatabaseHelper = new TodoDatabaseHelper(this);
        String title = this.txtViewTodoTitle.getText().toString();
        long value = todoDatabaseHelper.addNewTodo(new Todo(title, null, null, null));
        if (value < 0) {
            Toast.makeText(this, "Error while saving", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Saved", Toast.LENGTH_SHORT).show();
            finish();
        }
    }


}
