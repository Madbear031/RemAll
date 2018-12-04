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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TimePicker;

import com.example.marshmallow.remall_2.DB_Helper.DBHelper_task;
import com.example.marshmallow.remall_2.Activity.ForgetActivity;
import com.example.marshmallow.remall_2.R;

import java.util.Calendar;

public class AddTask extends AppCompatActivity implements View.OnClickListener {

    Button btnAdd, date, time;
    EditText etName;
    RadioButton radioButton1,radioButton2,radioButton3;
    RadioGroup radioGroup;
    int myHour = 0;
    int myMinute = 0;
    int myYear = 0;
    int myMonth = 0;
    int myDay = 0;
    int prior = 0;

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
        radioButton1 = findViewById(R.id.radioButton1);
        radioButton2 = findViewById(R.id.radioButton2);
        radioButton3 = findViewById(R.id.radioButton3);
        radioGroup = findViewById(R.id.radiogroup);

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
              /*  RadioButton rb = (RadioButton)v;
                switch (rb.getId()) {
                    case R.id.radioButton1: {
                        prior = 1;
                        break;
                    }
                    case R.id.radioButton2: {
                        prior = 2;
                        break;
                    }
                    case R.id.radioButton3: {
                        prior = 3;
                        break;
                    }
                }*/
                switch (radioGroup.getCheckedRadioButtonId()) {
                    case R.id.radioButton1:
                        radioButton2.setEnabled(false);
                        radioButton3.setEnabled(false);
                        prior = 1;
                        break;
                    case R.id.radioButton2:
                        radioButton1.setEnabled(false);
                        radioButton3.setEnabled(false);
                        prior = 2;
                        break;
                    case R.id.radioButton3:
                        radioButton2.setEnabled(false);
                        radioButton1.setEnabled(false);
                        prior = 3;
                        break;
                }
                cv.put("name", name);
                cv.put("prior", prior);
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
