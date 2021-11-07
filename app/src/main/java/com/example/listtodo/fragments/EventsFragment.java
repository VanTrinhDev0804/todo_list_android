package com.example.listtodo.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.listtodo.Adapter.ListVAdater;
import com.example.listtodo.Adapter.Task;
import com.example.listtodo.Adapter.TaskAdater;
import com.example.listtodo.R;
import com.example.listtodo.dataBase.Database;
import com.example.listtodo.views.MainActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Month;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EventsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EventsFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EventsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EventsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EventsFragment newInstance(String param1, String param2) {
        EventsFragment fragment = new EventsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    Database db;
    private View view;
    private MainActivity mainActivity;
    ArrayList<Task> tasksWatting, taskCompleted, tasksDeleted;
    ListVAdater listAdater;

    public void deleteTasksBackup(final int position, int idtask) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Message");
        builder.setMessage("Are you sure you want to delete this task ? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tasksDeleted.remove(position);
                listAdater.notifyDataSetChanged();
                try{
                    db = new Database(EventsFragment.this.getContext());
                    db.deleteBackup(idtask);
                    db.close();
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        });

        builder.setNegativeButton ("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });

        builder.show();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view =  inflater.inflate(R.layout.fragment_events, container, false);

        return view;
    }



    @Override
    public void onResume() {
        super.onResume();
        ListView Viewtask = view.findViewById(R.id.lsvTask);
        RadioGroup radioGroup= view.findViewById(R.id.group_rad);
        RadioButton radWait = view.findViewById(R.id.radtaskWaiting);
        TextView titleEvent= view.findViewById(R.id.titleEvents);
        mainActivity= (MainActivity) getActivity();


        tasksWatting = new ArrayList<>();
        taskCompleted = new ArrayList<>();
        tasksDeleted = new ArrayList<>();

        getTasksFromDataBase();
        getTaskDeleted();
        if(radWait.isChecked()){
            if(tasksWatting.size()==0){
                titleEvent.setText("No Tasks");
            }
            else{
                listAdater = new ListVAdater(this, R.layout.task_item_events, tasksWatting);
                Viewtask.setAdapter(listAdater);
            }
            }

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.radtaskWaiting:
                        titleEvent.setText("Your Tasks");
                        listAdater = new ListVAdater(EventsFragment.this, R.layout.task_item_events, tasksWatting);
                        Viewtask.setAdapter(listAdater);
                        break;
                    case R.id.radCompleted:
                        titleEvent.setText("Task Completed");
                        listAdater = new ListVAdater(EventsFragment.this, R.layout.task_item_events, taskCompleted);
                        Viewtask.setAdapter(listAdater);
                        break;
                    case R.id.radDeleted:
                        titleEvent.setText("Task Deleted");

                        listAdater = new ListVAdater(EventsFragment.this, R.layout.task_item_events, tasksDeleted);
                        Viewtask.setAdapter(listAdater);
                        break;
                }
            }
        });
    }

    private void getTaskDeleted() {
        try {
            db = new Database(this.getContext());
            int maKH =mainActivity.getMaKH();
            Cursor cursor = db.query_with_result("select * from TasksDeleted where maKH ='"+maKH+"'");
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String title = cursor.getString(1);
                String description = cursor.getString(2);
                String date = cursor.getString(3);
                String time = cursor.getString(4);

                String dayWeek = null;
                String dayMonth =null;
                String mMonth = null;
//                convert date
                dayWeek= ConvertDateTime(date , dayWeek, "EEE");
                dayMonth = ConvertDateTime(date, dayMonth, "dd");
                mMonth = ConvertDateTime(date, mMonth , "MMM");
                tasksDeleted.add(new Task(id,title,description,dayWeek, dayMonth,mMonth,time,true));
            }
            db.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void getTasksFromDataBase() {
        try {
            db = new Database(this.getContext());
            int maKH =mainActivity.getMaKH();
            Cursor c = db.query_with_result("select * from Tasks where maKH ='"+maKH+"'");

            while (c.moveToNext()){
                int id = c.getInt(0);
                String title = c.getString(1);
                String description = c.getString(2);
                String date = c.getString(3);
                String time = c.getString(4);
                int check = c.getInt(6);

                String dayOfWeek = null;
                String dayOfMonth =null;
                String mMonth = null;
//                convert date lấy ra định dạng ngay cần thiết
                dayOfWeek= ConvertDateTime(date , dayOfWeek, "EEE");
                dayOfMonth = ConvertDateTime(date, dayOfMonth, "dd");
                mMonth = ConvertDateTime(date, mMonth , "MMM");
                if(check == 0 ){
                    boolean status = false;
                    tasksWatting.add(new Task(id,title, description,dayOfWeek, dayOfMonth,mMonth,time,status));
                }else {
                    boolean status = true;
                    taskCompleted.add(new Task(id,title, description,dayOfWeek, dayOfMonth,mMonth,time,false));
                }
            }

                db.close();

        }catch (Exception e){
            e.printStackTrace();
        }


    }

    private String ConvertDateTime(String date, String getdate ,String path) {

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date dt1=formatter.parse(date);
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat(path);
            getdate = sdf.format(dt1);

        } catch (ParseException e) {
            e.printStackTrace();
        };
        return getdate;
    }


}