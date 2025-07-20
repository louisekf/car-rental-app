package com.autorental.model;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String message;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    private int userId;

    public Notification() {}

    public Notification(String message, int userId) {
        this.message = message;
        this.userId = userId;
        this.date = new Date();
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setUserId(int userId) { this.userId = userId; }

    public int getId() {
        return id;
    }
    public String getMessage() {
        return message;
    }
    public Date getDate() {
        return date;
    }
    public int getUserId() { return userId; }
}

