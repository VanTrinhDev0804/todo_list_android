package com.example.listtodo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.listtodo.R;
import com.example.listtodo.fragments.EventsFragment;

import java.util.ArrayList;

public class ListVAdater extends BaseAdapter {
    private final EventsFragment mContext;
    private final int layout ;
    private final ArrayList<Task> tasksListView;

    public ListVAdater(EventsFragment mContext, int layout, ArrayList<Task> tasksListView) {
        this.mContext = mContext;
        this.layout = layout;
        this.tasksListView = tasksListView;
    }

    @Override
    public int getCount() {
        return tasksListView.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    TextView title, descrition,dayofWeek,dayofMonth,Month,time, delete;
    private int idtask;
    @Override
    public View getView(int i, View view, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) mContext.getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view =layoutInflater.inflate(layout , null);


        title =view.findViewById(R.id.titleTask);
        descrition = view.findViewById(R.id.description);
        dayofWeek = view.findViewById(R.id.dayOfWeek);
        dayofMonth = view.findViewById(R.id.date);
        Month = view.findViewById(R.id.month);
        time = view.findViewById(R.id.time);
        delete = view.findViewById(R.id.btndelete);

        Task task =tasksListView.get(i);
        boolean status = task.isStatus();
        if(status){
            title.setText(task.getTitle());
            descrition.setText(task.getDescription());
            dayofWeek.setText(task.getmDayOfWeek());
            dayofMonth.setText(task.getmDate());
            Month.setText(task.getmMonth());
            time.setText(task.getTime());
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    idtask = task.getId();
                    mContext.deleteTasksBackup(i, idtask);
                }
            });
        }
        else {
            title.setText(task.getTitle());
            descrition.setText(task.getDescription());
            dayofWeek.setText(task.getmDayOfWeek());
            dayofMonth.setText(task.getmDate());
            Month.setText(task.getmMonth());
            time.setText(task.getTime());
            delete.setBackgroundColor(0xfffff);


        }
        return view;
    }
}
