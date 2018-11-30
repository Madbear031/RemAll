package com.example.marshmallow.remall_2.Add;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.marshmallow.remall_2.DB_Helper.DBHelper_shop;
import com.example.marshmallow.remall_2.R;
import com.example.marshmallow.remall_2.Activity.ShopActivity;

public class AddShop extends AppCompatActivity implements View.OnClickListener {

    Button add_purchase_btn;
    EditText purchase;

    DBHelper_shop dbHelper_shop = new DBHelper_shop(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);

        purchase = findViewById(R.id.task_name_bd);
        add_purchase_btn = findViewById(R.id.add_purchase_btn);
        add_purchase_btn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase db = dbHelper_shop.getWritableDatabase();

        ContentValues cv = new ContentValues();

        String purchasestr = String.valueOf(purchase.getText());

        switch (v.getId()){
            case R.id.add_purchase_btn:{
                cv.put("purchase", purchasestr);
                db.insert("shopdb", null, cv);
                Intent intent = new Intent(this, ShopActivity.class);
                startActivity(intent);
                break;
            }
        }
        dbHelper_shop.close();
    }
}
