package com.model;

import javax.persistence.*;

@Entity
@Table(name = "UserTaskInterest")
public class UserTaskInterest {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long userId;

    private String userName;

    private long taskNameId;

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

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public long getTaskNameId() {
        return taskNameId;
    }

    public void setTaskNameId(long taskNameId) {
        this.taskNameId = taskNameId;
    }
}
