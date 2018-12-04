package com.example.marshmallow.remall_2.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marshmallow.remall_2.Add.AddBook;
import com.example.marshmallow.remall_2.DB_Helper.DBHelper_book;
import com.example.marshmallow.remall_2.R;

import java.util.ArrayList;
import java.util.List;

public class BookActivity extends AppCompatActivity implements View.OnClickListener {


    ImageButton dontForgetButton, shopButton, add_button, del_button;
    TextView name_author;
    AlertDialog.Builder builder;
    LinearLayout lmain;

    DBHelper_book dbHelper_book = new DBHelper_book(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);

        SQLiteDatabase db = dbHelper_book.getReadableDatabase();

        dontForgetButton = findViewById(R.id.dontForgetButton);
        shopButton = findViewById(R.id.shopButton);
        add_button = findViewById(R.id.add_button);
        del_button = findViewById(R.id.del_button);
        shopButton.setOnClickListener(this);
        dontForgetButton.setOnClickListener(this);
        add_button.setOnClickListener(this);
        del_button.setOnClickListener(this);


        lmain = findViewById(R.id.lmain);

        Cursor c = db.query("bookdb", null, null, null, null, null, null);

        // ставим позицию курсора на первую строку выборки
        // если в выборке нет строк, вернется false
        if (c.moveToFirst()) {


            // определяем номера столбцов по имени в выборке
            int authorColIndex = c.getColumnIndex("author");
            int nameColIndex = c.getColumnIndex("name");

            do {
                View inflatedView = getLayoutInflater().inflate(R.layout.book_layout, null);
                name_author = inflatedView.findViewById(R.id.book_name_bd);
                name_author.setText(c.getString(authorColIndex) + "." + c.getString(nameColIndex));

                lmain.addView(inflatedView);

                // переход на следующую строку
                // а если следующей нет (текущая - последняя), то false - выходим из цикла
            } while (c.moveToNext());
        } else
            //taskView.setText("0 rows");
            c.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopButton: {
                Intent intent = new Intent(this, ShopActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.dontForgetButton: {
                Intent intent = new Intent(this, ForgetActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.add_button: {
                Intent intent = new Intent(this, AddBook.class);
                startActivity(intent);
                break;
            }
            case R.id.del_button: {
                showDialog(1);
                break;
            }
        }
    }
//

    protected Dialog onCreateDialog(final int id) {
        DBHelper_book dbHelper_book = new DBHelper_book(this);
        final SQLiteDatabase db = dbHelper_book.getReadableDatabase();
        final Cursor c = db.query("bookdb", null, null, null, null, null, null);
        final List<String> books = new ArrayList<>();
        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("name");
            do {
                books.add(c.getString(nameColIndex));
            } while (c.moveToNext());
        }
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose book"); // заголовок для диалога
        builder.setItems(books.toArray(new CharSequence[(int) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM bookdb", null)]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                //db.delete("Egor", "id = " + item, null);
                if (db.delete("bookdb", "name = ?", new String[]{books.get(item)}) > 0) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
                Toast.makeText(getApplicationContext(),
                        "Delete purchase: " + books.get(item),
                        Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        return builder.create();
    }
}
