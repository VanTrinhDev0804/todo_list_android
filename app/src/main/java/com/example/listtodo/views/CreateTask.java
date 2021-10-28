package com.example.listtodo.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.listtodo.R;
import com.example.listtodo.dataBase.Database;
import com.example.listtodo.fragments.HomeFragment;

import java.util.Calendar;

public class CreateTask extends AppCompatActivity {

    Database db;
    ConstraintLayout btnSave ;
    EditText TaskTitle, TaskDescription, DateTask,TimeTask;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private MainActivity mainActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);


        TaskTitle = findViewById(R.id.titleTask);
        TaskDescription = findViewById(R.id.depsTask);
        DateTask = findViewById(R.id.dateTask);
        TimeTask = findViewById(R.id.timeTask);

        btnSave = findViewById(R.id.btnSave);




        Intent incomingIntent = getIntent();
        boolean isUpdate = incomingIntent.getBooleanExtra("isUpdate",false);
        int id = incomingIntent.getIntExtra("index", 0);
        getValuesToUpdate(id,isUpdate);
        handleEventCreateTask(isUpdate,id);


// input day
        DateTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Date
                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(CreateTask.this, new DatePickerDialog.OnDateSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        DateTask.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                    },mYear, mMonth, mDay);
                    datePickerDialog.show();


            }
        });
//        input time
        TimeTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get Current Time
                final Calendar c = Calendar.getInstance();
                mHour = c.get(Calendar.HOUR_OF_DAY);
                mMinute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(CreateTask.this, new TimePickerDialog.OnTimeSetListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        TimeTask.setText(hourOfDay + ":"+minute);
                    }
                },mHour, mMinute,false);
                timePickerDialog.show();
            }
        });
    }

    private void getValuesToUpdate(int id, boolean isUpdate) {
        if(isUpdate){
            String title = null;
            String description = null;
            String date = null;
            String time = null;

            try{
                db = new Database(CreateTask.this);
                Cursor c = db.query_with_result("select * from Tasks where id ='"+id+"'");
                while (c.moveToNext()){
                     title = c.getString(1);
                     description = c.getString(2);
                     date = c.getString(3);
                     time = c.getString(4);
                }
                db.close();

            }catch (Exception e ){
                e.printStackTrace();
            }
            TaskTitle.setText(title);
            TaskDescription.setText(description);
            DateTask.setText(date);
            TimeTask.setText(time);

        }
    }

    private void handleEventCreateTask(boolean isUpdate, int id) {
        if(isUpdate){
            TextView TitleTag = findViewById(R.id.TagAdd);
            TextView TitleBtnSave = findViewById(R.id.txtBtnSave);
            TitleTag.setText("Update Task");
            TitleBtnSave.setText("Update Task");

            /// update task to database
            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        if (validateFields()){
                            db = new Database(CreateTask.this);
                            String title = TaskTitle.getText().toString();
                            String description = TaskDescription.getText().toString();
                            String date = DateTask.getText().toString();
                            String time = TimeTask.getText().toString();

                            db.upDateTask(title , description, date , time , 0, id);// false la cv chua hoan thanh
                            db.close();
                            closeTaskAndMoveHome();
                        }
                        else{
                            validateFields();
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }
        else {
//            get day from calendar
            Intent incomingIntent = getIntent();
            String Date = incomingIntent.getStringExtra("date");
            int MaKH = incomingIntent.getIntExtra("maKH",0);
            DateTask.setText(Date);

            btnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try{
                        if (validateFields()){
                            db = new Database(CreateTask.this);
                            String title = TaskTitle.getText().toString();
                            String description = TaskDescription.getText().toString();
                            String date = DateTask.getText().toString();
                            String time = TimeTask.getText().toString();

                            db.addTask(MaKH,title , description, date , time , 0);// false la cv chua hoan thanh
                            db.close();
                            closeTaskAndMoveHome();
                        }
                        else{
                            validateFields();
                        }

                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }

                }
            });
        }
    }

    public boolean validateFields() {
        if(TaskTitle.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter a valid title", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TaskDescription.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter a valid description", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(DateTask.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter a valid date", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(TimeTask.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Please enter a valid time", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            return true;
        }

    }

    private void closeTaskAndMoveHome() {
        TaskTitle.setText("");
        TaskDescription.setText("");
        DateTask.setText("");
        TimeTask.setText("");
        Intent it = new Intent(CreateTask.this, MainActivity.class);
        startActivity(it);
    }




}
