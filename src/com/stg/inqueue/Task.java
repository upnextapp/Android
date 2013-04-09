package com.stg.inqueue;

import java.util.Date;
import com.stackmob.sdk.model.StackMobModel;
 
public class Task extends StackMobModel {
 
    private String name;
    private Date dueDate;
    private int priority;
    private boolean done;
 
    public Task(String name, Date dueDate) {
        super(Task.class);
        this.name = name;
        this.dueDate = dueDate;
        this.priority = 0;
        this.done = false;
    }
}