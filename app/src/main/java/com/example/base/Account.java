package com.example.base;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Account extends AppCompatActivity {

    TextView textView;
    DatabaseHelper databaseHelper;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.account);

        textView = findViewById(R.id.textView);
        databaseHelper = new DatabaseHelper(this);
        db = databaseHelper.getReadableDatabase();

        Cursor cursor = db.query(DatabaseHelper.TABLE, null, null, null, null, null, null);
        if (cursor.moveToLast()) {
            @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex(DatabaseHelper.COLUMN_NAME));
            textView.setText(String.format("  " + name));
            textView.setTextColor(Color.RED);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);
        }

        cursor.close();
        db.close();
    }
}