package com.example.listtodo.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;

import com.example.listtodo.R;

public class CreateTask extends AppCompatActivity {

    EditText TaskTitle, TaskDescription, DateTask,TimeTask, EventTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_task);

        TaskTitle = findViewById(R.id.titleTask);
        TaskDescription = findViewById(R.id.depsTask);
        DateTask = findViewById(R.id.dateTask);
        TimeTask = findViewById(R.id.timeTask);
        EventTask = findViewById(R.id.eventTask);

        Intent incomingIntent = getIntent();
        String Date = incomingIntent.getStringExtra("date");

        DateTask.setText(Date);
    }
}
