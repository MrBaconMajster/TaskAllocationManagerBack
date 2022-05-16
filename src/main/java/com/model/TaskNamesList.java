package com.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "TaskNamesList")
public class TaskNamesList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String taskName;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
