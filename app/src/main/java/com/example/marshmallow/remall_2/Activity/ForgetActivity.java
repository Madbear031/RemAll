package com.example.marshmallow.remall_2.Activity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.marshmallow.remall_2.Add.AddTask;
import com.example.marshmallow.remall_2.DB_Helper.DBHelper_task;
import com.example.marshmallow.remall_2.R;

import java.util.ArrayList;
import java.util.List;

public class ForgetActivity extends AppCompatActivity implements View.OnClickListener {

    AlertDialog.Builder builder;
    ImageButton dontForgetButton;
    ImageButton bookButton;
    ImageButton shopButton;
    ImageButton addButton;
    ImageButton delButton;
    Button button;
    LinearLayout lmain;
    TextView date_bd;
    TextView time_bd;
    TextView task_name_bd;
    String notif_str = new String();

    private static int NOTIFY_ID = 100;
    DBHelper_task dbHelperTask = new DBHelper_task(this);
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint({"SetTextI18n", "WrongViewCast", "RestrictedApi", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SQLiteDatabase db = dbHelperTask.getReadableDatabase();

        dontForgetButton = findViewById(R.id.dontForgetButton);
        bookButton = findViewById(R.id.bookButton);
        shopButton = findViewById(R.id.shopButton);
        addButton = findViewById(R.id.add_button);
        delButton = findViewById(R.id.del_button);
        bookButton.setOnClickListener(this);
        shopButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        delButton.setOnClickListener(this);

        lmain = findViewById(R.id.lmain);

        Cursor c = db.query("tasksDB", null, null, null, null, null, null);
        if (c.moveToFirst()) {

            int nameColIndex = c.getColumnIndex("name");
            int priorColIndex = c.getColumnIndex("prior");
            int yearColIndex = c.getColumnIndex("year");
            int monthColIndex = c.getColumnIndex("month");
            int dayColIndex = c.getColumnIndex("day");
            int hourColIndex = c.getColumnIndex("hour");
            int minutesColIndex = c.getColumnIndex("minutes");

            do {
                View inflatedView = getLayoutInflater().inflate(R.layout.forget_layout, null);
                task_name_bd = inflatedView.findViewById(R.id.task_name_bd);
                date_bd = inflatedView.findViewById(R.id.date_bd);
                time_bd = inflatedView.findViewById(R.id.time_bd);
                button = inflatedView.findViewById(R.id.prior);
                task_name_bd.setText(c.getString(nameColIndex));
                date_bd.setText(String.valueOf(c.getInt(dayColIndex) + "." + c.getInt(monthColIndex) + "." + c.getInt(yearColIndex)));
                time_bd.setText(String.valueOf(c.getInt(hourColIndex) + ":" + c.getInt(minutesColIndex)));

                notif_str += c.getString(nameColIndex)+" "+c.getInt(dayColIndex)+"."+c.getInt(monthColIndex)
                        +"."+c.getInt(yearColIndex)+" "+c.getInt(hourColIndex)+":"+c.getInt(minutesColIndex)+"\n";


                switch (c.getInt(priorColIndex)){
                    case 1:{
                        button.setBackgroundResource(R.drawable.round_green);
                        break;
                    }
                    case 2:{
                        button.setBackgroundResource(R.drawable.round_yellow);
                        break;
                    }
                    case 3:{
                        button.setBackgroundResource(R.drawable.round_red);
                        break;
                    }
                }

                Intent notificationIntent = new Intent(this, ForgetActivity.class);
                PendingIntent contentIntent = PendingIntent.getActivity(this,
                        0, notificationIntent,
                        PendingIntent.FLAG_CANCEL_CURRENT);

                Resources res = this.getResources();


                NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

                builder.setContentIntent(contentIntent)
                        .setSmallIcon(R.drawable.ic_launcher_background)
                        .setContentTitle("Список Задач :")
                        .setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.ic_launcher_foreground))
                        .setPriority(NotificationCompat.PRIORITY_HIGH)
                        .setOngoing(true)
                        .setAutoCancel(false)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText(notif_str))
                        .setWhen(System.currentTimeMillis())
                        .setAutoCancel(false);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                notificationManager.notify(NOTIFY_ID, builder.build());

                lmain.addView(inflatedView);

            } while (c.moveToNext());
        }
        c.close();
    }

    @SuppressLint("ResourceType")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.shopButton: {
                Intent intent = new Intent(this, ShopActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.bookButton: {
                Intent intent = new Intent(this, BookActivity.class);
                startActivity(intent);
                break;
            }
            case R.id.add_button: {
                Intent intent = new Intent(this, AddTask.class);
                startActivity(intent);
                break;
            }
            case R.id.del_button: {
                showDialog(1);
                break;
            }
        }
    }

    protected Dialog onCreateDialog(final int id){
        DBHelper_task dbHelperTask = new DBHelper_task(this);
        final SQLiteDatabase db = dbHelperTask.getReadableDatabase();
        final Cursor c = db.query("tasksDB", null, null, null, null, null, null);
        final List<String> tasks = new ArrayList<>();
        if (c.moveToFirst()) {
            int nameColIndex = c.getColumnIndex("name");
            do {
                tasks.add(c.getString(nameColIndex));
            } while (c.moveToNext());
        }
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Choose task"); // заголовок для диалога
        builder.setItems(tasks.toArray(new CharSequence[(int)DatabaseUtils.longForQuery(db, "SELECT COUNT(*) FROM tasksDB", null)]), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                // TODO Auto-generated method stub
                if (db.delete("tasksDB", "name = ?", new String[]{tasks.get(item)}) > 0) {
                    Intent intent = getIntent();
                    finish();
                    startActivity(intent);
                }
                Toast.makeText(getApplicationContext(),
                        "Delete task: " + tasks.get(item),
                        Toast.LENGTH_SHORT).show();
        }
        });
        builder.setCancelable(false);
        return builder.create();
    }

}