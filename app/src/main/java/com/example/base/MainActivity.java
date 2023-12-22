package com.example.base;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    EditText nameBox;
    Button saveButton;
    DatabaseHelper sqlHelper;
    SQLiteDatabase db;
    long meoId = 0;

    @SuppressLint("Range")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nameBox = findViewById(R.id.name);
        saveButton = findViewById(R.id.saveButton);
        sqlHelper = new DatabaseHelper(this);
        db = sqlHelper.getWritableDatabase();

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            meoId = extras.getLong("id");
        }

        if (meoId > 0) {
            // Fetch existing name from database for editing
            Cursor cursor = db.query(DatabaseHelper.TABLE, null, DatabaseHelper.COLUMN_ID + "=?",
                    new String[]{String.valueOf(meoId)}, null, null, null);
            if (cursor.moveToFirst()) {
                nameBox.setText(cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME)));
            }
            cursor.close();
        }
    }

    public void save(View view) {
        ContentValues cv = new ContentValues();
        cv.put(DatabaseHelper.COLUMN_NAME, nameBox.getText().toString());

        if (meoId > 0) {
            // Update existing entry
            db.update(DatabaseHelper.TABLE, cv, DatabaseHelper.COLUMN_ID + "=?",
                    new String[]{String.valueOf(meoId)});
        } else {
            // Insert new entry
            db.insert(DatabaseHelper.TABLE, null, cv);
        }

        Intent i = new Intent(this, Account.class);
        startActivity(i);
    }
}