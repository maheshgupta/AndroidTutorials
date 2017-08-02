package com.tutorials.andorid.app.model.todo;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class Todo {
    public String title;
    public String subtitle;
    public String description;
    public String dateTime;

    public Todo(@NonNull String title, @Nullable String subtitle, @Nullable String description, @Nullable String dateTime) {
        this.title = title;
        this.subtitle = subtitle;
        this.description = description;
        this.dateTime = dateTime;
    }
}
