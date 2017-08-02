package com.tutorials.andorid.app.tutorials.sharedprefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.tutorials.andorid.app.R;

public class SharedPrefsDemo extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shared_prefs_demo);
    }

    private SharedPreferences getSharedPreferences() {
        return getPreferences(Context.MODE_PRIVATE);
    }


    @Override
    protected void onResume() {
        super.onResume();
        String value = getSharedPreferences().getString("Name", "DefaultValue");
        Toast.makeText(this, "Value is " + value, Toast.LENGTH_SHORT).show();
    }

    public void saveToPrefs(View view) {
        EditText editText = (EditText) findViewById(R.id.edit_text_prefs_value);

        String value = editText.getText().toString();
        String key = "Name";

        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(key, value);
        editor.commit();

        Toast.makeText(this, "Saved in preferences", Toast.LENGTH_SHORT).show();

        finish();
    }


}
