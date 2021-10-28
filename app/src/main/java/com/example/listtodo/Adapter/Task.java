package com.example.listtodo.Adapter;



public class Task {
    private  int id;
    private String title;
    private String description;
    private String mDayOfWeek, mDate, mMonth;
    private String time;
    private boolean status;

    public Task(int id, String title, String description, String mDayOfWeek, String mDate, String mMonth, String time, boolean status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.mDayOfWeek = mDayOfWeek;
        this.mDate = mDate;
        this.mMonth = mMonth;
        this.time = time;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getmDayOfWeek() {
        return mDayOfWeek;
    }

    public void setmDayOfWeek(String mDayOfWeek) {
        this.mDayOfWeek = mDayOfWeek;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public String getmMonth() {
        return mMonth;
    }

    public void setmMonth(String mMonth) {
        this.mMonth = mMonth;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
