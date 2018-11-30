package com.example.marshmallow.remall_2.Add;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.example.marshmallow.remall_2.DB_Helper.DBHelper_task;
import com.example.marshmallow.remall_2.Activity.ForgetActivity;
import com.example.marshmallow.remall_2.R;

import java.util.Calendar;

public class AddTask extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, date, time;
    EditText etName;
    int myHour = 0;
    int myMinute = 0;
    int myYear = 0;
    int myMonth = 0;
    int myDay = 0;

    Calendar dateAndTime=Calendar.getInstance();

    DBHelper_task dbHelperTask = new DBHelper_task(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);
        date = findViewById(R.id.date);
        date.setOnClickListener(this);
        time = findViewById(R.id.time);
        time.setOnClickListener(this);

        etName = findViewById(R.id.etName);

    }

    @Override
    public void onClick(View v) {
        SQLiteDatabase db = dbHelperTask.getWritableDatabase();

        ContentValues cv = new ContentValues();


        String name = etName.getText().toString();


        switch (v.getId()) {
            case R.id.date: {
                setDate(v);
                break;
            }
            case R.id.time: {
                setTime(v);
                break;
            }
            case R.id.btnAdd: {
                cv.put("name", name);
                cv.put("year", myYear);
                cv.put("month", myMonth);
                cv.put("day", myDay);
                cv.put("hour", myHour);
                cv.put("minutes", myMinute);
                db.insert("tasksDB", null, cv);
                Intent intent = new Intent(this, ForgetActivity.class);
                startActivity(intent);
                break;
            }
        }
        dbHelperTask.close();
    }

    public void setDate(View v) {
        new DatePickerDialog(this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    public void setTime(View v) {
        new TimePickerDialog(this, t,
                dateAndTime.get(Calendar.HOUR_OF_DAY),
                dateAndTime.get(Calendar.MINUTE), true)
                .show();

    }

    DatePickerDialog.OnDateSetListener d = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myYear = year;
            myMonth = month;
            myDay = dayOfMonth;
        }
    };

    TimePickerDialog.OnTimeSetListener t = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            myHour = hourOfDay;
            myMinute = minute;
        }
    };

}
