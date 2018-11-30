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

import com.example.marshmallow.remall_2.Add.AddShop;
import com.example.marshmallow.remall_2.DB_Helper.DBHelper_shop;
import com.example.marshmallow.remall_2.R;

import java.util.ArrayList;
import java.util.List;

public class ShopActivity extends AppCompatActivity implements View.OnClickListener {

    ImageButton dontForgetButton, bookButton,add_button, del_button;
    TextView purchase;
    AlertDialog.Builder builder;
    LinearLayout lmain;

    DBHelper_shop dbHelper_shop = new DBHelper_shop(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        SQLiteDatabase db = dbHelper_shop.getReadableDatabase();

        dontForgetButton = findViewById(R.id.dontForgetButton);
        bookButton = findViewById(R.id.bookButton);
        add_button = findViewById(R.id.add_button);
        del_button = findViewById(R.id.del_button);
        bookButton.setOnClickListener(this);
        dontForgetButton.setOnClickListener(this);
        add_button.setOnClickListener(this);
        del_button.setOnClickListener(this);


        lmain = findViewById(R.id.lmain);

        Cursor c = db.query("shopdb", null, null, null, null, null, null);

        if (c.moveToFirst()) {


            int purchaseColIndex = c.getColumnIndex("purchase");

            do {
                View inflatedView = getLayoutInflater().inflate(R.layout.shop_layout, null);
                purchase = inflatedView.findViewById(R.id.purchase_bd);
                purchase.setText(c.getString(purchaseColIndex));

                lmain.addView(inflatedView);
            } while (c.moveToNext());
        } else
            c.close();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bookButton:{
                Intent intent = new Intent(this, BookActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.dontForgetButton:{
                Intent intent = new Intent(this, ForgetActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.add_button: {
                Intent intent = new Intent(this, AddShop.class);
                startActivity(intent);
                break;
            }
            case R.id.del_button:{
                showDialog(1);
                break;
            }
        }
    }
    protected Dialog onCreateDialog(final int id){
        DBHelper_shop dbHelper_shop = new DBHelper_shop(this);
        final SQLiteDatabase db = dbHelper_shop.getReadableDatabase();
        final Cursor c = db.query("shopdb", null, null, null, null, null, null);
        final List<String> purchases = new ArrayList<>();
        if (c.moveToFirst()) {
            int purchaseColIndex = c.getColumnIndex("purchase");
            do {
                purchases.add(c.getString(purchaseColIndex));
            } while (c.moveToNext());
        }
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose purchase"); // заголовок для диалога
        builder.setItems(purchases.toArray(new CharSequence[(int) DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM shopdb", null)]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                if (db.delete("shopdb", "purchase = ?", new String[]{purchases.get(item)}) > 0) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
                Toast.makeText(getApplicationContext(),
                        "Delete purchase: " + purchases.get(item),
                        Toast.LENGTH_SHORT).show();
            }
        });
        builder.setCancelable(false);
        return builder.create();
    }

}
