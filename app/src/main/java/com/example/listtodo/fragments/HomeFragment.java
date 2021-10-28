package com.example.listtodo.fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.listtodo.Adapter.Task;
import com.example.listtodo.Adapter.TaskAdater;
import com.example.listtodo.R;
import com.example.listtodo.dataBase.Database;
import com.example.listtodo.views.CreateTask;
import com.example.listtodo.views.MainActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
            // TODO: Rename and change types of parameters
            String mParam1 = getArguments().getString(ARG_PARAM1);
            String mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    Database db;
    private View view;
    private MainActivity mainActivity;
    ArrayList<Task> tasksList;
    TaskAdater taskAdater;


    public void Delete(final int position, int index){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Message");
        builder.setMessage("Are you sure you want to delete this task ? ");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                tasksList.remove(position);
                taskAdater.notifyDataSetChanged();
                try{
                    db = new Database(HomeFragment.this.getContext());
                    db.deleteTask(index);
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
    public void Update(final int position, int index){
        boolean isUpdate = true;
        Intent homeUpdate = new Intent(getContext(), CreateTask.class);
        homeUpdate.putExtra("index",index );
        homeUpdate.putExtra("isUpdate",isUpdate);
        startActivity(homeUpdate);
    }
    public void Complete(final int position, int index){
        tasksList.get(position).setStatus(true);
        taskAdater.notifyDataSetChanged();
        try{
            db = new Database(HomeFragment.this.getContext());
            db.upDateStatus(index);
            db.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view=  inflater.inflate(R.layout.fragment_home, container, false);


        TextView addTask = view.findViewById(R.id.addTask);
        ListView listView = view.findViewById(R.id.lsvTask);
        mainActivity= (MainActivity) getActivity();



//    add list task item from database
        try {
                tasksList =new ArrayList<>();
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

                    String dayOfTheWeek = null;
                    String dayOfMonth =null;
                    String mMonth = null;
//                convert date of Week
                    @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                    try {
                        Date dt1=formatter.parse(date);
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("EEE");
                        dayOfTheWeek = sdf.format(dt1);


//                      convert day of month
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat doM = new SimpleDateFormat("dd");
                        dayOfMonth = doM.format(dt1);


//                       convert month
                        @SuppressLint("SimpleDateFormat") SimpleDateFormat mM = new SimpleDateFormat("MMM");
                        mMonth = mM.format(dt1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    };
                    boolean status = true;
                    if(check == 0 ){
                         status = false;
                    };
                    tasksList.add(new Task(id,title, description,dayOfTheWeek, dayOfMonth,mMonth,time,status));
                    db.close();
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        taskAdater = new TaskAdater(HomeFragment.this, R.layout.task_item, tasksList);
        listView.setAdapter(taskAdater);


//        add task
          addTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
           });

        return view;
    }


    private void showBottomSheetDialog() {

        BottomSheetDialog bottomSheetDialog;
        bottomSheetDialog = new BottomSheetDialog(this.getContext());
        bottomSheetDialog.setContentView(R.layout.activity_calendar);

        ImageView back = bottomSheetDialog.findViewById(R.id.backAllTask);
        CalendarView calendarView = bottomSheetDialog.findViewById(R.id.calendarView);

        bottomSheetDialog.show();

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
            }
        });
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                String date = i2 + "/" + (i1 + 1) + "/" + i;
                int maKH =mainActivity.getMaKH();
                Intent itdate = new Intent(getContext(), CreateTask.class);
                itdate.putExtra("date", date);
                itdate.putExtra("maKH", maKH);
                startActivity(itdate);
            }
        });

    }


}