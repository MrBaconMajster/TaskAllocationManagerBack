package com.model;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "BoardEntry")
public class BoardEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private long createdBy;

    private long dateDue;

    private String title;

    private String description;

    public BoardEntry() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(long createdBy) {
        this.createdBy = createdBy;
    }

    public long getDateDue() {
        return dateDue;
    }

    public void setDateDue(long dateDue) {
        this.dateDue = dateDue;
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
}
