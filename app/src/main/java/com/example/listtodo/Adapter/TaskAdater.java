package com.example.listtodo.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listtodo.R;
import com.example.listtodo.dataBase.Database;
import com.example.listtodo.fragments.HomeFragment;
import com.example.listtodo.views.CreateTask;

import java.util.ArrayList;

public class TaskAdater extends BaseAdapter {
    private final HomeFragment mContext;
    private final int layout ;
    private final ArrayList<Task> tasksList;


    public TaskAdater(HomeFragment mContext, int layout, ArrayList<Task> tasksList) {
        this.mContext = mContext;
        this.layout = layout;
        this.tasksList = tasksList;
    }

    @Override
    public int getCount() {
        return tasksList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    TextView update, delete , complete,title, descrition,dayofWeek,dayofMonth,Month,time;
    private int id;

    @SuppressLint("ViewHolder")
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view =layoutInflater.inflate(layout , null);


        update = view.findViewById(R.id.update);
        delete = view.findViewById(R.id.delete);
        complete = view.findViewById(R.id.complete);
        title =view.findViewById(R.id.titleTask);
        descrition = view.findViewById(R.id.description);
        dayofWeek = view.findViewById(R.id.dayOfWeek);
        dayofMonth = view.findViewById(R.id.date);
        Month = view.findViewById(R.id.month);
        time = view.findViewById(R.id.time);

        Task task =tasksList.get(i);
        boolean status = task.isStatus();
        if(status){
            title.setText(task.getTitle()+" (Completed)");
            descrition.setText(task.getDescription());
            dayofWeek.setText(task.getmDayOfWeek());
            dayofMonth.setText(task.getmDate());
            Month.setText(task.getmMonth());
            time.setText(task.getTime());
            complete.setText("Completed");
            complete.setBackgroundColor(0x546E7A);
            update.setBackgroundColor(0x546E7A);

        }
        else {
            title.setText(task.getTitle());
            descrition.setText(task.getDescription());
            dayofWeek.setText(task.getmDayOfWeek());
            dayofMonth.setText(task.getmDate());
            Month.setText(task.getmMonth());
            time.setText(task.getTime());
            update.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    id = task.getId();
                    mContext.Update(i, id);

                }
            });
            complete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    id = task.getId();
                    mContext.Complete(i, id);
                }
            });
        }


        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = task.getId();
                mContext.Delete(i, id);


            }
        });



        return view;
    }


}
