package com.tutorials.andorid.app.tutorials.sqlite;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.tutorials.andorid.app.R;
import com.tutorials.andorid.app.model.todo.Todo;

import java.util.List;

public class ToDoHomeActivity extends AppCompatActivity {

    private TodoDatabaseHelper todoDatabaseHelper;

    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_to_do_home);
        listView = (ListView) findViewById(R.id.todos_list);
        this.todoDatabaseHelper = new TodoDatabaseHelper(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        List<Todo> todos = this.todoDatabaseHelper.getTodos();
        this.renderTodos(todos);

    }

    private void renderTodos(final List<Todo> todos) {
        if (todos != null && todos.size() > 0) {
            String[] titles = new String[todos.size()];
            for (int i = 0; i < todos.size(); i++) {
                titles[i] = todos.get(i).title;
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles);
            this.listView.setAdapter(adapter);
            this.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int index, long l) {
                    showTodoDetail(todos.get(index));
                }
            });
        }
    }

    private void showTodoDetail(Todo todo) {
        Toast.makeText(this, "Showing Todo Detail for " + todo.title, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.todo_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.todo_menu_add:
                Intent intent = new Intent(this, AddNewTodoActivity.class);
                startActivity(intent);
                return true;
            case R.id.todo_menu_close:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
