package com.example.marshmallow.remall_2.Add;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.marshmallow.remall_2.Activity.BookActivity;
import com.example.marshmallow.remall_2.DB_Helper.DBHelper_book;
import com.example.marshmallow.remall_2.R;

public class AddBook extends AppCompatActivity implements View.OnClickListener {

   Button add_book_btn;
   EditText author;
   EditText name;

    DBHelper_book dbHelper_book = new DBHelper_book(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_book);

        author = findViewById(R.id.author);
        name = findViewById(R.id.name);
        add_book_btn = findViewById(R.id.add_book_btn);
        add_book_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase db = dbHelper_book.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String namestr = String.valueOf(name.getText());
        String authorstr = String.valueOf(author.getText());

        switch (v.getId()){
            case R.id.add_book_btn:{
                cv.put("author", authorstr);
                cv.put("name", namestr);
                db.insert("bookdb", null, cv);
                Intent intent = new Intent(this, BookActivity.class);
                startActivity(intent);
                break;
            }
        }
        dbHelper_book.close();
    }
}
