package com.example.listtodo.Adapter;

import java.text.DateFormat;

public class Task {
    private String title;
    private String description;
    private DateFormat date;

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

    public DateFormat getDate() {
        return date;
    }

    public void setDate(DateFormat date) {
        this.date = date;
    }

    public Task(String title, String description, DateFormat date) {
        this.title = title;
        this.description = description;
        this.date = date;
    }
}
